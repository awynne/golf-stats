package net.wynne.golf.model;

import groovy.transform.ToString;

import java.util.List;

import net.wynne.golf.types.Round

@ToString(includeNames = true, includeFields = true)
public class ScoreCard {
	
	String date
	Player player
	String name

	Course course
	String tee

	double rating
	int slope
	int parIn
	int parOut

	Round round

	List<Score> scores = new ArrayList<Score>()
	
	public List<Score> getFront() {
		scores.findAll { Score score -> score.hole <= 9 }
	}
	
	public List<Score> getBack() {
		scores.findAll { Score score -> score.hole > 9 }
	}
}
