package net.wynne.golf.types

enum GreenIn {
	REGULATION("GIR", "R"),
	REGULATION_PLUSONE("GIRP", "P"),
	MISS("miss", "X");
	
	private String longName
	private String shortName
	
	static final Map map
 
	static {
		map = [:] as TreeMap
		values().each{ it ->
			map.put(it.shortName, it)
		}
	}
	
	private GreenIn(String longName, String shortName) {
		this.longName = longName
		this.shortName = shortName
	}

	static GreenIn fromShort(String shortNm) {
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
