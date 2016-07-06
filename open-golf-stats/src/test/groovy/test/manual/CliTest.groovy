package test.manual

import net.wynne.golf.cli.GolfStats;

class CliTest {
	
	static main(args) {
		String[] arg= ['-user', 'adam', '-extended', 'false', '-reports', '9norm,18norm']
		GolfStats.main(arg)
	}

}
