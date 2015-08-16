package net.wynne.golf.model;

import groovy.transform.ToString;

@ToString(includeNames = true, includeFields = true, excludes = "tee")
class Hole {
	int number;
	int par;
	int handicap;
	int distance;
}

