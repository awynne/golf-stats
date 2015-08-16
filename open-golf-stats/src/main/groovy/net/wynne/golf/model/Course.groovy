package net.wynne.golf.model

import groovy.transform.ToString;

import java.util.List

@ToString(includeNames = true, includeFields = true)
class Course {
	String name
	String id
	Map<String,Tee> tees = new HashMap<String,Tee>()
}
