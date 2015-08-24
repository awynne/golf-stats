package net.wynne.golf.model

import groovy.transform.ToString;

@ToString(includeNames = true, includeFields = true)
class Comp {
	
	Map data = [:]

	String name
	String reference
	String description

}
