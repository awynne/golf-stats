package net.wynne.golf.types;

enum Club {
	DRIVER("driver", "D"), 
	WOOD_3("3-wood", "3W"), 
	WOOD_5("5-wood", "5W"), 
	HYBRID_3("3-hybrid", "3H"), 
	HYBRID_4("4-hybrid", "4H"),
	HYBRID_5("5-hybrid", "5H"),
	IRON_5("5-iron", "5I"), 
	IRON_6("6-iron", "6I"), 
	IRON_7("7-iron", "7I"), 
	IRON_8("8-iron", "8I"), 
	IRON_9("9-iron", "9I"),
	IRON_PW("pitching-wedge", "PW"), 
	IRON_GW("gap-wedge", "GW"), 
	IRON_SW("sand-wedge", "SW"), 
	IRON_LW("lob-wedge", "LW"), 
	PUTTER("putter", "P"),
	NOT_RECORDED("not-recorded", "NR");
	
	private String longName;
	private String shortName;
	
	static final Map map
	
	static {
		map = [:] as TreeMap
		values().each{ it ->
			map.put(it.shortName, it)
		}
	}
	
	private Club(String longName, String shortName) {
		this.longName = longName;
		this.shortName = shortName;
	}
	
	static Club fromShort(String shortNm) {
		if (shortNm == null | shortNm.trim().equals("")) {
			shortNm = "NR"
		}

		map[shortNm]
	}
	
	String getShort() {
		return shortName
	}
	
	String getLong() {
		return longName
	}
}
