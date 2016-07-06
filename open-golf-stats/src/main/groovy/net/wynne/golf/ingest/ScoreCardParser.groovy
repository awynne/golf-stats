package net.wynne.golf.ingest

import net.wynne.golf.types.*
import net.wynne.golf.model.CardBuilder;
import net.wynne.golf.model.Course
import net.wynne.golf.model.Player
import net.wynne.golf.model.Score
import net.wynne.golf.model.ScoreCard
import net.wynne.golf.model.Tee

class ScoreCardParser {
	
	String filePath

	Map<String,Course> courses
	Map<String,Player> players

	private List<String[]> rows
	
	ScoreCard parse() {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new RuntimeException("File not found: " + filePath)
		}
		String name = file.name.substring(0, file.name.lastIndexOf('.'))
		String[] nameParts = name.split("_")
		
		CardBuilder builder = new CardBuilder()
		
		builder.round = Round.fromShort( nameParts[FnameParts.NUM_HOLES] )
		builder.date = nameParts[FnameParts.DATE]

		if (courses != null) {
			String courseId = nameParts[FnameParts.COURSE_ID]
			builder.course = courses[courseId]
			builder.teeColor = nameParts[FnameParts.TEE_COLOR]
		}
		
		if (players != null) {
			String user = nameParts[FnameParts.USER_NAME]
			builder.player = players[user]
		}

		FileInputStream inputFile = new FileInputStream(file)
		String[] lines = inputFile.text.split('\n')
		rows = lines.collect {it.split(",")}
		
		// skip header
		for (int i=1; i < rows.size(); i++ ) {
			String[] row = rows[i]

			Score score = new Score()
			score.hole          = parseInt( row[Column.HOLE] )
			score.driveDistance = parseInt( row[Column.DRIVE_DISTANCE] )
			score.numPutts      = parseInt( row[Column.NUM_PUTTS] )
			score.strokes       = parseInt( row[Column.STROKES] )

			builder.scores << score

//			score.firstClub     = Club.fromShort( row[Column.FIRST_CLUB] )
//			score.driveAccuracy = Accuracy.fromShort( row[Column.DRIVE_ACCURACY] )
//			score.greenIn       = GreenIn.fromShort( row[Column.GREEN_IN_REG] )
//			score.numAwfulLong  = parseInt( row[Column.AWFUL_LONG] )
//			score.putt1distance = parseInt( row[Column.PUTT1_DISTANCE] )
//			score.numAwfulShort = parseInt( row[Column.AWFUL_SHORT] )
		}
		
		builder.build()
	}
	
	private int parseInt(String str) {
		if (str == null || str.trim().equals("") || !str.trim().isInteger()) {
			return -1
		}
		
		return str.toInteger()
	}
}
