package net.wynne.golf.ingest

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import groovy.json.JsonSlurper
import net.wynne.golf.model.*
import net.wynne.golf.types.Round;


class Analysis {
	
	static final BASE_PATH_DEFAULT = "src/test/resources"
	static final CARD_PATH = "cards"
	static final COURSE_PATH = "courses"
	static final PLAYER_PATH = "players"
	
	static final REPORT_18 = "18"
	static final REPORT_9 = "9"
	static final REPORT_18_NORMALIZED = "18norm"
	static final REPORT_9_NORMALIZED = "9norm"
	static final String[] REPORTS_DEFAULT = [REPORT_18, REPORT_9, REPORT_18_NORMALIZED, REPORT_9_NORMALIZED]
	
	String basePath  = BASE_PATH_DEFAULT
	boolean extendedStats = false
	String[] reports

	private Map<String,Player> players
	private Map<String,Course> courses
	private List<ScoreCard> scoreCards

	static main(args) {
		Analysis loader = new Analysis()
		loader.load()
		loader.run("adam")
	}
	
	void run(String userName) {
		Player player = players[userName]
		println("Printing stats for player: $player")
		
		if (!reports) { reports = REPORTS_DEFAULT }
		
		List<ScoreCard> cards = scoreCards.findAll { it.player.userName.equals(userName) }

		if (reports.contains(REPORT_18)) { report18(cards) }
		if (reports.contains(REPORT_9))  { report9(cards) }
		
		List <ScoreCard>cards09 = cards.findAll { ScoreCard card -> card.round == Round.NINE_HOLE}
		List<ScoreCard> cards09Norm = new ArrayList<ScoreCard>()
		cards09Norm.addAll(cards09)
		
		if (reports.contains(REPORT_9_NORMALIZED))  { report9Normalized(cards, cards09Norm) }
		if (reports.contains(REPORT_18_NORMALIZED)) { report18Normalized(cards, cards09) }

	}
	
	void report18(List<ScoreCard> cards) {
		List <ScoreCard>cards18 = cards.findAll { ScoreCard card -> card.round == Round.EIGHTEEN_HOLE}
		report("18-hole Stats (" + cards18.size() + ")", cards18)
	}
	
	void report9(List<ScoreCard> cards) {
		List <ScoreCard>cards09 = cards.findAll { ScoreCard card -> card.round == Round.NINE_HOLE}
		report("9-hole Stats (" + cards09.size() + ")", cards09)
	}
	
	void report18Normalized(List<ScoreCard> cards, List<ScoreCard> cards09) {
		List <ScoreCard>cards18 = cards.findAll { ScoreCard card -> card.round == Round.EIGHTEEN_HOLE}
		List<ScoreCard> cards18Norm = new ArrayList<ScoreCard>()
		cards18Norm.addAll(cards18)
		
		int num = cards09.size()/2
		
		for(int i=0; i < num; i++) {
			CardCombinedBuilder builder = new CardCombinedBuilder();
			builder.front = cards09.pop()
			builder.back  = cards09.pop()
			builder.date  = builder.back.date
			builder.player = builder.back.player
			builder.round = Round.COMBINED

			cards18Norm << builder.build()
		}
		
		report("18-hole Normalized (" + cards18Norm.size() + ")", cards18Norm)
	}
	
	void report9Normalized(List<ScoreCard> cards, List<ScoreCard> cards09Norm ) {
		List <ScoreCard>cards18 = cards.findAll { ScoreCard card -> card.round == Round.EIGHTEEN_HOLE}
		cards18.each { ScoreCard card ->
			CardBuilder fBuilder = new CardBuilder()
			fBuilder.course = card.course
			fBuilder.date = card.date
			fBuilder.player = card.player
			fBuilder.round = Round.NINE_HOLE
			fBuilder.scores = card.getFront()
			fBuilder.teeColor = card.tee
			cards09Norm << fBuilder.build()
			
			CardBuilder bBuilder = new CardBuilder()
			bBuilder.course = card.course
			bBuilder.date = card.date
			bBuilder.player = card.player
			bBuilder.round = Round.NINE_HOLE
			bBuilder.scores = card.getBack()
			bBuilder.teeColor = card.tee
			cards09Norm << bBuilder.build()
		}

		report("9-hole Normalized (" + cards09Norm.size() + ")", cards09Norm)
	}

	
	void report(String title, List<ScoreCard> cards) {
		StatsBuilder builder = new StatsBuilder(scoreCards:cards, extended:extendedStats)
		Statistics stats = builder.build()
		stats.compute()
		stats.report(title)
	}
	
	List<ScoreCard> load() {
		assert basePath

		def cardPath   = basePath + "/" + CARD_PATH
		def coursePath = basePath + "/" + COURSE_PATH
		def playerFile = basePath + "/" + PLAYER_PATH + "/players.json"

		players = loadPlayers(playerFile)
		courses = loadCourses(coursePath)
		scoreCards = parseCards(cardPath, courses, players)
	}

	private Map<String,Course> loadCourses(String coursePath) {
		Map<String,Course> courses = new HashMap<String,Course>()

		File dir = new File(coursePath);
		File[] listOfFiles = dir.listFiles();
		
		listOfFiles.each { file ->
			FileInputStream inputFile = new FileInputStream(file)
			JsonSlurper jsonSlurper = new JsonSlurper()
			Course course = jsonSlurper.parseText(inputFile.text)
			courses.put(course.id, course)
		}
		
		courses
	}
	
	private Map<String,Player> loadPlayers(String playerFile) {
		FileInputStream inputFile = new FileInputStream(playerFile)
		JsonSlurper jsonSlurper = new JsonSlurper()
		Map<String, Player> players = jsonSlurper.parseText(inputFile.text)
		
		return players
	}

	private List<ScoreCard> parseCards(String cardPath, Map courses, Map players) {
		List<ScoreCard> cards = new ArrayList<ScoreCard>()
		
		String[] files = [ 
			"$cardPath/2015-07-16_SoPark9_adam_white_9.csv",
			"$cardPath/2015-07-23_MtLebanon_adam_white_9.csv",
			"$cardPath/2015-08-09_Brandywine_adam_white_18.csv"
		]
		
		files.each { file ->
			ScoreCardParser parser = new ScoreCardParser(filePath:file, courses:courses, players:players);
			cards << parser.parse()
		}

		cards
	}

}
