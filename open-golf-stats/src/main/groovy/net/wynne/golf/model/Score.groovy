package net.wynne.golf.model;

import groovy.transform.ToString;

import java.util.Map
import net.wynne.golf.types.*

@ToString(includeNames = true, includeFields = true)
public class Score {
	
	int hole
	
	// Tee stats
	
	Club firstClub
	Accuracy driveAccuracy
	int driveDistance
	
	// Approach stats

	GreenIn greenIn
	int numAwfulLong
	
	// Putting stats
	
	int putt1distance
	int numPutts
	int numAwfulShort
	
	// final score
	
	int strokes;
}
