package net.wynne.golf.types

import java.util.Map;

enum Stat {
	
	OVER_PAR("Strokes over par", "overPar"),
	DIFFERENTIAL("Differential", "diff"),
	HANDICAP("Handicap index", "handicap"),
	DRIVE_DIST("Drive dist", "dist"),
	
	GROUP_GIR("GIR and GIRP", "girAndGirp"),
	GIR("GIR per rd", "gir"),
	GIRP("GIRP per rd", "girp"),
	
	GROUP_AWFUL("Awful Shots", "awfulShots"),
	AWFUL_LONG("Awful long per rd", "awfulLong"),
	AWFUL_SHORT("Awful short per rd", "awfulShort"),
	AWFUL_TOTAL("Awful total per rd", "awfulTotal"),
	PUTTS("Num putts per rd", "numPutts"),
	NONE("StatType not set", "notSet");
	
	private String longName
	private String shortName
	
	static final Map<String,Stat> map
	
	static {
		map = [:] as TreeMap
		values().each() { statType ->
			map.putAt(statType.shortName, statType)
		}
	}
	
	Stat(String longName, String shortName) {
		this.longName = longName
		this.shortName = shortName
	}
	
	static Stat fromShort(String shortName) {
		if (shortName == null | shortName.trim().equals("")) {
			shortName = Stat.NONE.shortName
		}
		
		map[shortName]
	}
}
