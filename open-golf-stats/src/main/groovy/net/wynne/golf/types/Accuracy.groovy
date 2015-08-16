package net.wynne.golf.types

enum Accuracy {
	HIT ("hit", "H"),
	MISS("miss", "X"),
	MISS_LEFT("left", "L"),
	MISS_RIGHT("right", "R"),
	MISS_SHORT("short", "S"),
	MISS_NA("NA", "NA"),
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
	
	Accuracy(String longName, shortName) {
		this.longName = longName
		this.shortName = shortName
	}
	
	static Accuracy fromShort(String shortNm) {
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
