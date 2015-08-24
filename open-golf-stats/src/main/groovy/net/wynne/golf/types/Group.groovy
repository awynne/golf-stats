package net.wynne.golf.types

import java.util.Map;

enum Group {
	GOLFER_PRO("pro-golfer", "pro",       0..70),
	GOLFER_75("75-golfer",   "75glfr",   71..75),
	GOLFER_80("80-golfer",   "80glfr",   76..80),
	GOLFER_85("85-golfer",   "85glfr",   81..85),
	GOLFER_90("90-golfer",   "90glfr",   86..90),
	GOLFER_95("95-golfer",   "95glfr",   91..95),
	GOLFER_100("100-golfer", "100glfr",  96..100),
	GOLFER_105("105-golfer", "105glfr", 101..105),
	GOLFER_110("110-golfer", "110glfr", 106..9999);
	
	private String longName
	private String shortName
	private Range<Integer> range
	
	static final Map<String,Group> map
	
	static {
		map = [:] as TreeMap
		values().each() { group ->
			group.range.each { score ->
				map.putAt(score, group)
			} 
		}
	}
	
	Group(String longName, String shortName, Range<Integer> range) {
		this.longName = longName
		this.shortName = shortName
		this.range = range
	}
	
	static Group fromScore(int score) {
		map[score]
	}
	
	static Group fromLong(String name) {
		Group.values().find { Group group ->
			group.longName.equals(name)
		}
	}
	
	String getLong() {
		return longName
	}
	
	String getShort() {
		return shortName
	}
	
	static main(args) {
		(1..200).each { it ->
			println("$it: " + Group.fromScore(it).longName)
		}
	}

}
