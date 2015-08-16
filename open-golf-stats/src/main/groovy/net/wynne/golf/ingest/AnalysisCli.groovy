package net.wynne.golf.ingest

class AnalysisCli {
	
	static main(args) {
		
		CliBuilder cli = new CliBuilder(usage:"analysis [opitons]")

		cli.help("print this message")
		cli.extended(args:1, argName:"boolean", "true to print extended statistics")
		cli.user(args:1, argName:"userName", "user to print analysis for")
		cli.reports(args:1, argName:"reportList", "comma-separated list of reports to output")

		def options = cli.parse(args)
		
		assert options
		assert options.user
		
		if (options.help) {
			cli.usage()
			System.exit(0)
		}
		
		Analysis analysis = new Analysis()

		if (options.extended) {
			analysis.extendedStats = options.extended.toBoolean()
		}
		
		if (options.reports) {
			analysis.reports = options.reports.split(",")
		}
		
		analysis.load()
		analysis.run(options.user)
	}
	
	static run() {
		Analysis loader = new Analysis(basePath:Analysis.BASE_PATH_DEFAULT, extendedStats:false)
	}

}
