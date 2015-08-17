package test.manual

import static net.wynne.golf.types.Group.*;

import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;

import net.wynne.golf.model.Comp;
import groovy.json.JsonOutput;

class SerializeComps {
	
	static final String file = "src/test/resources/comps/comps.json"
	
	List<Comp> comps = []
	
	static main(args) {
		SerializeComps ser = new SerializeComps()
		ser.run()
	}
	
	void run() {
		comps << loadGir()
		comps << loadAwful()
		
		def json = JsonOutput.toJson(comps)
		new File(file).write(JsonOutput.prettyPrint(json))
	}
	
	/*
	 */
	Comp loadAwful() {
		Comp awfulComp = new Comp()
		awfulComp.name = "AwfulShots"
		awfulComp.reference = "Table 9.17. Awful shot scorecard: Broadie, Mark (2014-03-06). " +
                              "Every Shot Counts (p. 207). Penguin Publishing Group. "
							 
        awfulComp.description = "Off-green strokes lost per round to awful shots. Count one "   + 
	                            "for each awful shot and count two for each double-awful shot " + 
		 					    "and add the numbers to get your awful total. The long-game "   + 
		 					    "and short-game stats break the awful total down by where the " + 
							    "awful shots occur, outside 100 yards from the hole or inside " + 
							    "100 yards (excluding putts on the green)."

		Map total = [:]
		total[GOLFER_PRO.longName] = 0.7
		total[GOLFER_75.longName]  = 1.4
		total[GOLFER_80.longName]  = 2.5
		total[GOLFER_85.longName]  = 3.9
		total[GOLFER_90.longName]  = 5.6
		total[GOLFER_95.longName]  = 7.5
		total[GOLFER_100.longName] = 9.7
		total[GOLFER_105.longName] = 12.1
		total[GOLFER_110.longName] = 14.8

		Map awfulLong = [:]
		awfulLong[GOLFER_PRO.longName] = 0.5
		awfulLong[GOLFER_75.longName]  = 1.1
		awfulLong[GOLFER_80.longName]  = 1.7
		awfulLong[GOLFER_85.longName]  = 2.6
		awfulLong[GOLFER_90.longName]  = 3.8
		awfulLong[GOLFER_95.longName]  = 5.1
		awfulLong[GOLFER_100.longName] = 6.7
		awfulLong[GOLFER_105.longName] = 8.5
		awfulLong[GOLFER_110.longName] = 10.6

		Map awfulShort = [:]
		awfulShort[GOLFER_PRO.longName] = 0.2
		awfulShort[GOLFER_75.longName]  = 0.3
		awfulShort[GOLFER_80.longName]  = 0.8
		awfulShort[GOLFER_85.longName]  = 1.3
		awfulShort[GOLFER_90.longName]  = 1.8
		awfulShort[GOLFER_95.longName]  = 2.4
		awfulShort[GOLFER_100.longName] = 3.0
		awfulShort[GOLFER_105.longName] = 3.6
		awfulShort[GOLFER_110.longName] = 4.2

		awfulComp.data["awfulTotal"] = total
		awfulComp.data["awfulLong"] = awfulLong
		awfulComp.data["awfulShort"] = awfulShort
		
		awfulComp
	}
	
	Comp loadGir() {
		Comp girComp = new Comp()
		girComp.name = "GIR/GIRP"
		girComp.description = "GIR is the number of greens hit in regulation per round.  " + 
		                      "GIRP is the number of greens and fringe hit in regulation " + 
							  "plus one stroke per round. "

		girComp.reference = "Table 9.16. GIR and GIRP scorecard: Broadie, Mark (2014-03-06)." +
	                        "Every Shot Counts (p. 203). Penguin Publishing Group. "
							
		def gir = [:]
		gir[GOLFER_PRO.longName] = 11.6
		gir[GOLFER_75.longName]  = 8.9
		gir[GOLFER_80.longName]  = 7.0
		gir[GOLFER_85.longName]  = 5.3
		gir[GOLFER_90.longName]  = 3.9
		gir[GOLFER_95.longName]  = 2.8
		gir[GOLFER_100.longName] = 1.9
		gir[GOLFER_105.longName] = 1.2
		gir[GOLFER_110.longName] = 0.8
		
		def girp = [:]
		girp[GOLFER_PRO.longName] = 17.3
		girp[GOLFER_75.longName]  = 17.0
		girp[GOLFER_80.longName]  = 15.4
		girp[GOLFER_85.longName]  = 13.8
		girp[GOLFER_90.longName]  = 12.3
		girp[GOLFER_95.longName]  = 10.8
		girp[GOLFER_100.longName] = 9.5
		girp[GOLFER_105.longName] = 8.1
		girp[GOLFER_110.longName] = 6.8

		girComp.data["gir"] = gir
		girComp.data["girp"] = girp
		
		girComp
	}
	

}
