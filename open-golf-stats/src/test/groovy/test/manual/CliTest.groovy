package test.manual

import net.wynne.golf.ingest.AnalysisCli;

class CliTest {
	
	static main(args) {
		String[] arg= ['-user', 'adam', '-extended', 'false', '-reports', '9norm,18norm']
		AnalysisCli.main(arg)
	}

}
