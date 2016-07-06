package net.wynne.golf.model

import groovy.transform.ToString;

@ToString(includeNames = true, includeFields = true)
class Player {
	
	String firstName
	String lastName
	String userName
	
	double handicap
	double targetHandicap
}
