package test.manual

import groovy.json.JsonOutput;
import net.wynne.golf.model.*

class SerializePlayers {
	
	static final String playerDir = "src/test/resources/players"
	
	static main(args) {
		Map<String, Player> players = new HashMap<String, Player>()

		Player adam = new Player(firstName:"Adam", lastName:"Wynne", userName:"adam", handicap:23.2, targetHandicap:15)
		players[adam.userName] = adam

		def json = JsonOutput.toJson(players)

		new File(playerDir).mkdirs()
		String file = playerDir + "/players.json"
		new File(file).write(JsonOutput.prettyPrint(json))
	}
	
}
