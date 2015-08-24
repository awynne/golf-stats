package test.manual

import static net.wynne.golf.types.Group.*;

import java.rmi.activation.ActivationGroupDesc.CommandEnvironment;

import com.sun.java.swing.plaf.gtk.GTKConstants.StateType;

import net.wynne.golf.model.Comp;
import net.wynne.golf.types.Stat;
import groovy.json.JsonOutput;
import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class SerializeComps {
	
	static final String file = "src/test/resources/comps/comps.json"
	
	Map<String,Comp> map = [:]
	
	static main(args) {
		SerializeComps ser = new SerializeComps()
		ser.run()
	}
	
	void run() {
		loadGir()
		loadAwful()
		
		def json = JsonOutput.toJson(map)
		new File(file).write(JsonOutput.prettyPrint(json))
	}
	
	/*
	 */
	void loadAwful() {
		String ref  = "Table 9.17. Awful shot scorecard: Broadie, Mark (2014-03-06). " +
                      "Every Shot Counts (p. 207). Penguin Publishing Group. "
							 
        String desc = "Off-green strokes lost per round to awful shots. Count one "   + 
	                  "for each awful shot and count two for each double-awful shot " + 
		 			  "and add the numbers to get your awful total. The long-game "   + 
		 			  "and short-game stats break the awful total down by where the " + 
					  "awful shots occur, outside 100 yards from the hole or inside " + 
					  "100 yards (excluding putts on the green)."

		Map totalData = [:]
		totalData[GOLFER_PRO.longName] = 0.7
		totalData[GOLFER_75.longName]  = 1.4
		totalData[GOLFER_80.longName]  = 2.5
		totalData[GOLFER_85.longName]  = 3.9
		totalData[GOLFER_90.longName]  = 5.6
		totalData[GOLFER_95.longName]  = 7.5
		totalData[GOLFER_100.longName] = 9.7
		totalData[GOLFER_105.longName] = 12.1
		totalData[GOLFER_110.longName] = 14.8

		Comp awfulTotal = new Comp(name:Stat.AWFUL_TOTAL.shortName, data:totalData, description:desc, reference:ref)
		map[awfulTotal.name] = awfulTotal

		Map longData = [:]
		longData[GOLFER_PRO.longName] = 0.5
		longData[GOLFER_75.longName]  = 1.1
		longData[GOLFER_80.longName]  = 1.7
		longData[GOLFER_85.longName]  = 2.6
		longData[GOLFER_90.longName]  = 3.8
		longData[GOLFER_95.longName]  = 5.1
		longData[GOLFER_100.longName] = 6.7
		longData[GOLFER_105.longName] = 8.5
		longData[GOLFER_110.longName] = 10.6
		
		Comp awfulLong = new Comp(name:Stat.AWFUL_LONG.shortName, data:longData, description:desc, reference:ref)
		map[awfulLong.name] = awfulLong

		Map shortData = [:]
		shortData[GOLFER_PRO.longName] = 0.2
		shortData[GOLFER_75.longName]  = 0.3
		shortData[GOLFER_80.longName]  = 0.8
		shortData[GOLFER_85.longName]  = 1.3
		shortData[GOLFER_90.longName]  = 1.8
		shortData[GOLFER_95.longName]  = 2.4
		shortData[GOLFER_100.longName] = 3.0
		shortData[GOLFER_105.longName] = 3.6
		shortData[GOLFER_110.longName] = 4.2

		Comp awfulShort = new Comp(name:Stat.AWFUL_SHORT.shortName, data:shortData, description:desc, reference:ref)
		map[awfulShort.name] = awfulShort
	}
	
	void loadGir() {
		String desc = "GIR is the number of greens hit in regulation per round.  " + 
		              "GIRP is the number of greens and fringe hit in regulation " + 
					  "plus one stroke per round. "

		String ref  = "Table 9.16. GIR and GIRP scorecard: Broadie, Mark (2014-03-06)." +
	                  "Every Shot Counts (p. 203). Penguin Publishing Group. "
							
		Map girData = [:]
		girData[GOLFER_PRO.longName] = 11.6
		girData[GOLFER_75.longName]  = 8.9
		girData[GOLFER_80.longName]  = 7.0
		girData[GOLFER_85.longName]  = 5.3
		girData[GOLFER_90.longName]  = 3.9
		girData[GOLFER_95.longName]  = 2.8
		girData[GOLFER_100.longName] = 1.9
		girData[GOLFER_105.longName] = 1.2
		girData[GOLFER_110.longName] = 0.8
		
		Comp gir = new Comp(name:Stat.GIR.shortName, data:girData, description:desc, reference:ref)
		map[gir.name] = gir
		
		Map girpData = [:]
		girpData[GOLFER_PRO.longName] = 17.3
		girpData[GOLFER_75.longName]  = 17.0
		girpData[GOLFER_80.longName]  = 15.4
		girpData[GOLFER_85.longName]  = 13.8
		girpData[GOLFER_90.longName]  = 12.3
		girpData[GOLFER_95.longName]  = 10.8
		girpData[GOLFER_100.longName] = 9.5
		girpData[GOLFER_105.longName] = 8.1
		girpData[GOLFER_110.longName] = 6.8
		
		Comp girp = new Comp(name:Stat.GIRP.shortName, data:girpData, description:desc, reference:ref)
		map[girp.name] = girp
	}
	

}
