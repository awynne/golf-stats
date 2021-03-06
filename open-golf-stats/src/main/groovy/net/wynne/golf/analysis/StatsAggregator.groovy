package net.wynne.golf.analysis

import java.math.RoundingMode;
import java.util.List;

import net.wynne.golf.data.ExtendedStats;
import net.wynne.golf.model.Comp
import net.wynne.golf.model.Score
import net.wynne.golf.model.ScoreCard;
import net.wynne.golf.model.Tee
import net.wynne.golf.types.GreenIn;
import net.wynne.golf.types.Group;
import net.wynne.golf.types.Round;

class StatsAggregator {
	
	static final int LABEL_COL_WIDTH = 18
	static final int VALUE_COL_WIDTH = 6
	static final int COL_PAD = 2

	static final double SLOPE_AVE = 113
	static final double BONUS_PERCENTAGE = 0.96
	
	static final String HDR_STAT_NAME = "Stat"
	static final String HDR_AVE = "Ave"
	static final String HDR_TARGET = "Target"
	static final String HDR_MAX = "Max"
	static final String HDR_MIN = "Min"
	static final String HDR_N = "N"
	
	boolean reportExtended = false
	
	private String[] coreCols = [HDR_AVE]//, HDR_TARGET]
	private String[] extendedCols = [HDR_MAX, HDR_MIN, HDR_N]
	private String[] targetCols = [Group.GOLFER_95.longName, Group.GOLFER_90.longName]

	private int numValCols 
	private int numBorders 
	private int totalWidth 
	private String rowSep
	private String headerSep
	
	private List<ScoreCard> scoreCards
	private Map<String,Comp> comps

	private ExtendedStats driveDistStats = new ExtendedStats()
	private ExtendedStats girpStats = new ExtendedStats()
	private ExtendedStats girStats = new ExtendedStats()
	private ExtendedStats numPuttStats = new ExtendedStats()
	private ExtendedStats strokeStats = new ExtendedStats()
	private ExtendedStats diffStats = new ExtendedStats()
	private ExtendedStats handicapStats = new ExtendedStats()
	
	private Map<Stat,ExtendedStats> extStats = [:]
	
	StatsAggregator(List<ScoreCard> scoreCards, Map<String,Comp> comps, boolean extended) {
		this.scoreCards = scoreCards
		this.comps = comps
		this.reportExtended = extended
		
		extStats[Stat.DRIVE_DIST] = driveDistStats
		extStats[Stat.GIRP] = girpStats
		extStats[Stat.GIR] = girStats
		extStats[Stat.PUTTS] = numPuttStats
		extStats[Stat.OVER_PAR] = strokeStats
		extStats[Stat.DIFFERENTIAL] = diffStats
		extStats[Stat.HANDICAP] = handicapStats
		
		initTable()
	}
	
	def compute() {
		extStats.keySet().each { Stat stat ->
			Comp comp = comps[stat.shortName]
			if (comp) {
				ExtendedStats extStats = extStats[stat]

				targetCols.each { String colName ->
					extStats.targets[colName] = comp.data[colName]
				}
			}
		} 

		scoreCards.each { ScoreCard card ->
			// Tally numbers for this score card and store descriptive stats
			Map tally = perRoundTally(card)
			
			// gir/girp
			girpStats.addValue(tally.girp)
			girStats.addValue(tally.gir)

			numPuttStats.addValue(tally.putts)
			
			// Get the course info for this card
			Map course = calcCourseInfo(card)

			// Calculate strokes over par
			int strokesOverPar = (tally.strokes - course.par)
			strokeStats.addValue(strokesOverPar)

			// Calculate stroke differential for this card
			// source: http://golf.about.com/cs/handicapping/a/howcalculated.htm
			if (card.round == Round.EIGHTEEN_HOLE || card.round == Round.COMBINED) {
				double differential = (tally.strokes - course.rating) * SLOPE_AVE / course.slope
				diffStats.addValue(differential)
			}
		}
		// Calculate handicap after all cards are processed
		calcHandicap()
	}
	
	Map perRoundTally(ScoreCard card) {
		// per round stats
		int girp = 0, gir = 0, putts = 0, strokes = 0

		card.scores.each { Score score ->
			// Drive distance
			if (score.driveDistance) {
				driveDistStats.addValue(score.driveDistance)
			}
			
			// GIR/P
			/*
			if (score.greenIn.equals(GreenIn.REGULATION)) {
				gir++
				girp++
			}
			else if (score.greenIn.equals(GreenIn.REGULATION_PLUSONE)) {
				girp++
			}
			else {
				// GreenIn.MISS
			}
			*/
			
			// num putts
			if (putts >= 0) { putts += score.numPutts }
			
			//strokes total
			strokes += score.strokes
		}

		[girp:girp, gir:gir, putts:putts, strokes:strokes]
	}

	Map calcCourseInfo(ScoreCard card) {
		int par = -1
		double rating = -1
		double slope = -1

		if (card.course) {
			Tee tee = card.course.tees[card.tee]

			if (card.round == Round.NINE_HOLE) {
				par = tee.getParOut();
			}
			else {
				par = tee.getParOut() + tee.getParIn()
				rating = tee.rating
				slope = tee.slope
			}
		}
		else {
			if (card.round == Round.COMBINED) {
				par = card.parIn + card.parOut
				rating = card.rating
				slope = card.slope
			}
			else {
				throw new RuntimeException("don't know how to handle round: " + card.round)
			}
		}
		
		[par:par, rating:rating, slope:slope]
	}
	
	// source: http://golf.about.com/cs/handicapping/a/howcalculated.htm
	def calcHandicap() {
		
		List diffs = diffStats.getValues().toList().sort()

		if (!diffs) return
		
		int numRounds = diffs.size()
		int numDiffs 

		if      (numRounds <= 6)  { numDiffs = 1 }
		else if (numRounds <= 8)  { numDiffs = 2 }
		else if (numRounds <= 10) { numDiffs = 3 }
		else if (numRounds <= 12) { numDiffs = 4 }
		else if (numRounds <= 14) { numDiffs = 5 }
		else if (numRounds <= 16) { numDiffs = 6 }
		else if (numRounds <= 17) { numDiffs = 7 }
		else if (numRounds <= 18) { numDiffs = 8 }
		else if (numRounds <= 19) { numDiffs = 9 }
		else                      { numDiffs = 10 }
		
		diffs.subList(0, numDiffs).each {
			handicapStats.addValue(it)
		}
	}
	
	void initTable() {
		if (reportExtended) {
			numValCols = coreCols.size() + targetCols.size() + extendedCols.size()
		}
		else {
			numValCols = coreCols.size() + targetCols.size()
		}

		numBorders = numValCols + 2
		totalWidth = numValCols * ( VALUE_COL_WIDTH + COL_PAD) + (LABEL_COL_WIDTH + COL_PAD) + numBorders
		rowSep = "-".multiply(totalWidth)
		headerSep = "=".multiply(totalWidth)
	}
	
	def report(String title) {
		printTitle(title)
		printHeader()
		printRow(Stat.OVER_PAR, strokeStats)
		printRow(Stat.DIFFERENTIAL, diffStats) 
		printRow(Stat.HANDICAP, handicapStats, true) 
		printRow(Stat.DRIVE_DIST, driveDistStats)
		printRow(Stat.GIR, girStats)
		printRow(Stat.GIRP, girpStats)
		printRow(Stat.PUTTS, numPuttStats)
	}
	
	private printTitle(String title) {
		if (title) {
			println(headerSep)
			println("| ${title.center(totalWidth-4)} |")
		}
	}
	
	private printHeader() {
		println(headerSep)
		
		String currGroup = Group.fromLong( targetCols[0] ).shortName
		String futureGroup = Group.fromLong( targetCols[1] ).shortName
		
		print("| ${HDR_STAT_NAME.padRight(LABEL_COL_WIDTH)} | ${HDR_AVE.center(VALUE_COL_WIDTH)} | ${currGroup.center(VALUE_COL_WIDTH)} | ${futureGroup.center(VALUE_COL_WIDTH)} ")
		if (reportExtended) {
			print("| ${HDR_MIN.center(VALUE_COL_WIDTH)} | ${HDR_MAX.center(VALUE_COL_WIDTH)} ")
			print("| ${HDR_N.center(VALUE_COL_WIDTH)} ")
		}
		print("|\n")
		println(headerSep)
	}
	
	private printRow(Stat stat, ExtendedStats stats, boolean handicap = false) {
		if (stats.getN() == 0) { return }

		String aveStr 

		if (handicap) { aveStr = round(stats.getMean() * BONUS_PERCENTAGE, 1) as String }
		else          { aveStr = round(stats.getMean(), 1) as String }
		
		String minStr = Math.round(stats.getMin()) as String
		String maxStr = Math.round(stats.getMax()) as String
		String numStr = stats.getN() as String
		
		String currTarget = stats.targets[targetCols[0]] ?: ""
		String futureTarget = stats.targets[targetCols[1]] ?: ""
		
		String label = stat.longName
		
		print("| ${label.padRight(LABEL_COL_WIDTH)} | ${aveStr.padLeft(VALUE_COL_WIDTH)} | ${currTarget.padLeft(VALUE_COL_WIDTH)} | ${futureTarget.padLeft(VALUE_COL_WIDTH)} ")
		if (reportExtended) {
			print("| ${minStr.padLeft(VALUE_COL_WIDTH)} | ${maxStr.padLeft(VALUE_COL_WIDTH)} ")
			print("| ${numStr.padLeft(VALUE_COL_WIDTH)} ")
		}
		print("|\n")
		println(rowSep)
	}
	
	double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
	
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
