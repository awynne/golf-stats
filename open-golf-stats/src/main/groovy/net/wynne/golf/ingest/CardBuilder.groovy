package net.wynne.golf.ingest

import java.util.List;

import net.wynne.golf.model.Course;
import net.wynne.golf.model.Player
import net.wynne.golf.model.Score;
import net.wynne.golf.model.ScoreCard
import net.wynne.golf.types.Round;

class CardBuilder {
	
	String date
	Player player

	Course course
	String teeColor

	Round round

	List<Score> scores = new ArrayList<Score>()
	
	ScoreCard build() {
		ScoreCard card = new ScoreCard(date:date, player:player, 
			course:course, tee:teeColor, round:round, 
			name:"${course.id}_${date}", scores:scores)
	}

}
