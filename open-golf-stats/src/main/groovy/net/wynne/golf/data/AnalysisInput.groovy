package net.wynne.golf.data

import java.util.List;
import java.util.Map;

import net.wynne.golf.model.Comp;
import net.wynne.golf.model.Course;
import net.wynne.golf.model.Player;
import net.wynne.golf.model.ScoreCard;

class AnalysisInput {
	
	Map<String,Player> players
	Map<String,Course> courses
	List<ScoreCard> scoreCards
	Map<String,Comp> comps

}
