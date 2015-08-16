package net.wynne.golf.model;

import groovy.transform.ToString;

import java.util.List;

@ToString
class Tee {
	String color

	double rating
	double slope
	
	List<Hole> holes = new ArrayList<Hole>()
	
	int parOut
	int parIn
	
	
}
