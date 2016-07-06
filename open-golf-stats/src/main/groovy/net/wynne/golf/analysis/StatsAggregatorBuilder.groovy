package net.wynne.golf.analysis

import java.util.List;

import net.wynne.golf.model.Comp
import net.wynne.golf.model.ScoreCard;

class StatsAggregatorBuilder {
	
	boolean extended = false
	
	List<ScoreCard> scoreCards
	Map<String,Comp> comps
	
	StatsAggregator build() {
		assert scoreCards
		assert comps
		new StatsAggregator(scoreCards, comps, extended)
	}

}
