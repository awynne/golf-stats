package net.wynne.golf.types

import java.util.Map;

enum Round {
	NINE_HOLE("9-hole", "9"),
	EIGHTEEN_HOLE("18-hole", "18"),
	COMBINED("combined", "C"),
	NOT_RECORDED("not-recorded", "NR");
	
	private String longName
	private String shortName
	
	static final Map<String,Accuracy> map
	
	static {
		map = [:] as TreeMap
		values().each() { accuracy ->
			map.putAt(accuracy.shortName, accuracy)
		}
	}
	
	Round(String longName, String shortName) {
		this.longName = longName
		this.shortName = shortName
	}
	
	static Round fromShort(String shortNm) {
		if (shortNm == null | shortNm.trim().equals("")) {
			shortNm = "NR"
		}

		map[shortNm]
	}
	
	String getLong() {
		return longName
	}
	
	String getShort() {
		return shortName
	}
}
