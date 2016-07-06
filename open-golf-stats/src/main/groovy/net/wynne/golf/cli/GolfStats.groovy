package net.wynne.golf.cli

import net.wynne.golf.analysis.Analysis;
import net.wynne.golf.data.AnalysisInput
import net.wynne.golf.ingest.DataLoader


class GolfStats {
	
	static main(args) {
		
		CliBuilder cli = new CliBuilder(usage:"analysis [options]")

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
		//Reporter reporter = new Reporter();

		/*
		if (options.extended) {
			analysis.PRINT_EXTENDED = options.extended.toBoolean()
		}
		*/
		
		/*
		if (options.reports) {
			analysis.reports = options.reports.split(",")
			//reporter.reports = options.reports.split(",")
		}
		*/
		
		AnalysisInput input = new DataLoader().load()
		analysis.run(input, options.user)
		//reporter.report()
	}
}
