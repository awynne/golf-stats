package net.wynne.golf.ingest

import java.util.List
import java.util.Map

import net.wynne.golf.data.AnalysisInput
import net.wynne.golf.model.Comp
import net.wynne.golf.model.Course
import net.wynne.golf.model.Player
import net.wynne.golf.model.ScoreCard
import groovy.json.JsonSlurper
import groovy.io.FileType
		
class DataLoader {
	
//	static final BASE_PATH_DEFAULT = "src/test/resources"
	static final BASE_PATH_DEFAULT = "../data"
	static final CARD_PATH = "cards"
	static final COURSE_PATH = "courses"
	static final PLAYER_PATH = "players"
	static final COMPS_PATH = "comps"

	String basePath;
	
	AnalysisInput load() {
		if (!basePath) basePath = BASE_PATH_DEFAULT

		def cardPath   = basePath + "/" + CARD_PATH
		def coursePath = basePath + "/" + COURSE_PATH
		def playerFile = basePath + "/" + PLAYER_PATH + "/players.json"
		def compFile = basePath + "/" + COMPS_PATH + "/comps.json"

		def players = loadPlayers(playerFile)
		def courses = loadCourses(coursePath)
		def cards = parseCards(cardPath, courses, players)
		def comps = loadComps(compFile)
		
		new AnalysisInput(players:players, courses:courses, 
			scoreCards:cards, comps:comps)
	}
	
	private Map<String,Comp> loadComps(String compsFile) {
		
		FileInputStream inputFile = new FileInputStream(compsFile)
		JsonSlurper jsonSlurper = new JsonSlurper()

		jsonSlurper.parseText(inputFile.text)
	}

	private Map<String,Course> loadCourses(String coursePath) {
		Map<String,Course> courses = new HashMap<String,Course>()

		File dir = new File(coursePath);
		File[] listOfFiles = dir.listFiles();
		
		listOfFiles.each { file ->
			FileInputStream inputFile = new FileInputStream(file)
			JsonSlurper jsonSlurper = new JsonSlurper()
			Course course = jsonSlurper.parseText(inputFile.text)
			courses.put(course.id, course)
		}
		
		courses
	}
	
	private Map<String,Player> loadPlayers(String playerFile) {
		FileInputStream inputFile = new FileInputStream(playerFile)
		JsonSlurper jsonSlurper = new JsonSlurper()
		Map<String, Player> players = jsonSlurper.parseText(inputFile.text)
		println("loading players: " + players)
		
		return players
	}

	private List<ScoreCard> parseCards(String cardPath, Map courses, Map players) {
		List<ScoreCard> cards = new ArrayList<ScoreCard>()
		
		def cardFiles = []
		def dir = new File(cardPath)
		dir.eachFileRecurse (FileType.FILES) { file ->
		  cardFiles << file
		}
		
		/*
		String[] files = [
			"$cardPath/2015-07-16_SoPark9_adam_white_9.csv",
			"$cardPath/2015-07-23_MtLebanon_adam_white_9.csv",
			"$cardPath/2015-08-09_Brandywine_adam_white_18.csv"
		]
		*/
		
		cardFiles.each { file ->
			ScoreCardParser parser = new ScoreCardParser(filePath:file, courses:courses, players:players);
			cards << parser.parse()
		}

		cards
	}
}
