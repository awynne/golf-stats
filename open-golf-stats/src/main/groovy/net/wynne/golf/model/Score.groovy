package net.wynne.golf.model;

import groovy.transform.ToString;

import java.util.Map
import net.wynne.golf.types.*

@ToString(includeNames = true, includeFields = true)
public class Score {
	
	int hole
	
	static Boolean hitFairway 
	int driveDistance
	int numPutts
	int strokes;
	
	static void main(args) {
		println("hey: " + hitFairway)
	}
}
