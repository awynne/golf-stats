package net.wynne.golf.ingest

import java.util.List;

import net.wynne.golf.model.ScoreCard;

class StatsBuilder {
	
	boolean extended = false
	
	List<ScoreCard> scoreCards
	
	Statistics build() {
		assert scoreCards
		new Statistics(scoreCards, extended)
	}

}
