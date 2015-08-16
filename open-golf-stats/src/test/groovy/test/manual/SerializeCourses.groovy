package test.manual

import groovy.json.JsonOutput;
import net.wynne.golf.model.*

class SerializeCourses {
	
	static final String courseDir = "src/test/resources/courses"
	
	static void main(String[] args) {
		serialize( createMtLebo()  )
		serialize( createSoPark9() )
		serialize( createBrandywine() )
	}
	
	static void serialize(Course course) {
		new File(courseDir).mkdirs()

		String file = courseDir + "/" + course.id + ".json"
		def json = JsonOutput.toJson(course)
		new File(file).write(JsonOutput.prettyPrint(json))
	}
	
	static Course createBrandywine() {
		Course bw = new Course(name:"Brandywine Country Club", id:"Brandywine")
		
		Tee white = new Tee(color:"white", rating:68.7, slope:116, parOut:36, parIn:36)
		bw.tees.put(white.color, white)
		
		white.holes << new Hole(number:1,  par:4, distance:376, handicap:9)
		white.holes << new Hole(number:2,  par:5, distance:494, handicap:3)
		white.holes << new Hole(number:3,  par:3, distance:155, handicap:17)
		white.holes << new Hole(number:4,  par:4, distance:323, handicap:5)
		white.holes << new Hole(number:5,  par:4, distance:336, handicap:11)
		white.holes << new Hole(number:6,  par:4, distance:380, handicap:7)
		white.holes << new Hole(number:7,  par:4, distance:318, handicap:13)
		white.holes << new Hole(number:8,  par:5, distance:534, handicap:1)
		white.holes << new Hole(number:9,  par:3, distance:140, handicap:15)

		white.holes << new Hole(number:10, par:4, distance:347, handicap:14)
		white.holes << new Hole(number:11, par:4, distance:301, handicap:8)
		white.holes << new Hole(number:12, par:4, distance:277, handicap:10)
		white.holes << new Hole(number:13, par:4, distance:350, handicap:4)
		white.holes << new Hole(number:14, par:3, distance:130, handicap:16)
		white.holes << new Hole(number:15, par:3, distance:148, handicap:18)
		white.holes << new Hole(number:16, par:5, distance:545, handicap:2)
		white.holes << new Hole(number:17, par:4, distance:416, handicap:6)
		white.holes << new Hole(number:18, par:5, distance:518, handicap:12)
		
		return bw
	}
	
	static Course createSoPark9() {
		
		Course soPark = new Course(name:"South Park 9-Hole Golf Course", id:"SoPark9")
		
		Tee white = new Tee(color:"white", rating:34, slope:116, parOut:36)
		soPark.tees.put(white.color, white)
		
		white.holes << new Hole(number:1, distance:309, par:4, handicap:4)
		white.holes << new Hole(number:2, distance:295, par:4, handicap:6)
		white.holes << new Hole(number:3, distance:166, par:3, handicap:9)
		white.holes << new Hole(number:4, distance:323, par:4, handicap:7)
		white.holes << new Hole(number:5, distance:460, par:5, handicap:2)
		white.holes << new Hole(number:6, distance:264, par:4, handicap:5)
		white.holes << new Hole(number:7, distance:455, par:5, handicap:3)
		white.holes << new Hole(number:8, distance:187, par:3, handicap:8)
		white.holes << new Hole(number:9, distance:400, par:4, handicap:1)
		
		Tee red = new Tee(color:"red", rating:35, slope:112, parOut:36)
		soPark.tees.put(red.color, red)

		red.holes << new Hole(number:1, distance:309, par:4, handicap:4)
		red.holes << new Hole(number:2, distance:295, par:4, handicap:6)
		red.holes << new Hole(number:3, distance:166, par:3, handicap:9)
		red.holes << new Hole(number:4, distance:323, par:4, handicap:7)
		red.holes << new Hole(number:5, distance:460, par:5, handicap:2)
		red.holes << new Hole(number:6, distance:264, par:4, handicap:5)
		red.holes << new Hole(number:7, distance:455, par:5, handicap:3)
		red.holes << new Hole(number:8, distance:187, par:3, handicap:8)
		red.holes << new Hole(number:9, distance:400, par:4, handicap:1)
		
		return soPark
	}
	
	static Course createMtLebo() {
		
		// Mt. Lebo

		Course mtLeboCourse = new Course(name:"Mt. Lebanon Golf Course", id:"MtLebanon")
		
		Tee leboBlue = new Tee(color:"blue", rating:34.3, slope:118, parOut:34)
		mtLeboCourse.tees.put(leboBlue.color, leboBlue)
		
		leboBlue.holes << new Hole(number:1, distance:164, par:3, handicap:8) 
		leboBlue.holes << new Hole(number:2, distance:394, par:4, handicap:4) 
		leboBlue.holes << new Hole(number:3, distance:374, par:4, handicap:5) 
		leboBlue.holes << new Hole(number:4, distance:477, par:4, handicap:2) 
		leboBlue.holes << new Hole(number:5, distance:195, par:3, handicap:6) 
		leboBlue.holes << new Hole(number:6, distance:314, par:4, handicap:7) 
		leboBlue.holes << new Hole(number:7, distance:391, par:4, handicap:3) 
		leboBlue.holes << new Hole(number:8, distance:397, par:4, handicap:1) 
		leboBlue.holes << new Hole(number:9, distance:261, par:4, handicap:9) 
		
		Tee leboWhite = new Tee(color:"white", rating:33.7, slope:113, parOut:34)
		mtLeboCourse.tees.put(leboWhite.color, leboWhite)

		leboWhite.holes << new Hole(number:1, distance:155, par:3, handicap:8) 
		leboWhite.holes << new Hole(number:2, distance:386, par:4, handicap:4) 
		leboWhite.holes << new Hole(number:3, distance:339, par:4, handicap:5) 
		leboWhite.holes << new Hole(number:4, distance:423, par:4, handicap:2) 
		leboWhite.holes << new Hole(number:5, distance:177, par:3, handicap:6) 
		leboWhite.holes << new Hole(number:6, distance:306, par:4, handicap:7) 
		leboWhite.holes << new Hole(number:7, distance:386, par:4, handicap:3) 
		leboWhite.holes << new Hole(number:8, distance:386, par:4, handicap:1) 
		leboWhite.holes << new Hole(number:9, distance:245, par:4, handicap:9) 
		
		Tee leboGold = new Tee(color:"gold", rating:32.6, slope:111, parOut:35)
		mtLeboCourse.tees.put(leboGold.color, leboGold)

		leboGold.holes << new Hole(number:1, distance:145, par:3, handicap:8) 
		leboGold.holes << new Hole(number:2, distance:370, par:4, handicap:4) 
		leboGold.holes << new Hole(number:3, distance:321, par:4, handicap:5) 
		leboGold.holes << new Hole(number:4, distance:414, par:5, handicap:2) 
		leboGold.holes << new Hole(number:5, distance:109, par:3, handicap:6) 
		leboGold.holes << new Hole(number:6, distance:298, par:4, handicap:7) 
		leboGold.holes << new Hole(number:7, distance:379, par:4, handicap:3) 
		leboGold.holes << new Hole(number:8, distance:377, par:4, handicap:1) 
		leboGold.holes << new Hole(number:9, distance:185, par:4, handicap:9) 

		Tee leboRed = new Tee(color:"red", rating:35.2, slope:118, parOut:36)
		mtLeboCourse.tees.put(leboRed.color, leboRed)

		leboRed.holes << new Hole(number:1, distance:145, par:3, handicap:8) 
		leboRed.holes << new Hole(number:2, distance:370, par:4, handicap:4) 
		leboRed.holes << new Hole(number:3, distance:321, par:4, handicap:5) 
		leboRed.holes << new Hole(number:4, distance:408, par:5, handicap:2) 
		leboRed.holes << new Hole(number:5, distance:103, par:3, handicap:6) 
		leboRed.holes << new Hole(number:6, distance:294, par:4, handicap:7) 
		leboRed.holes << new Hole(number:7, distance:379, par:5, handicap:3) 
		leboRed.holes << new Hole(number:8, distance:377, par:5, handicap:1) 
		leboRed.holes << new Hole(number:9, distance:176, par:4, handicap:9) 
		
		return mtLeboCourse;
	}
}
