/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cs.toronto.edu.secxbrl;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

/**
 *
 * @author ekzhu
 */
public class Main {
    
    private static String outputFile = null;
    private static String formType = null;
    private static String yearMonth = null;
    private static String cik = null;
    private static final String monthlyArchive = "http://www.sec.gov/Archives/edgar/monthly/";
    
    public static void main(String args[]) {
        Options options = setupOptions();
        CommandLineParser parser = new BasicParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption('h')) {
                printHelpAndExit(options);
            }
            if (line.hasOption("o")) {
                outputFile = line.getOptionValue('o');
            } else {
                printHelpAndExit(options);
            }
            if (line.hasOption('m')) {
                yearMonth = line.getOptionValue('m');
            }
            if (line.hasOption('t')) {
                formType = line.getOptionValue('t');
            }
            if (line.hasOption('c')) {
                cik = line.getOptionValue('c');
            }
            
            // Run
            MonthlyFeedDirectoryReader monthlyFeedDirectoryReader 
                    = new MonthlyFeedDirectoryReader(monthlyArchive);
            FilingLoader filingLoader = new FilingLoader(monthlyFeedDirectoryReader);
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outputFile), "utf-8"));
            filingLoader.getFilings(yearMonth, cik, formType, writer);            
            
        } catch (Exception ex) {
            System.err.println("Unexpected exception: " + ex.getMessage());
            System.exit(1);
        }
    }
    
    private static Options setupOptions() {
        Options options = new Options();
        options.addOption("m", "yearmonth", true, "Recommended for faster performance. The filing month and year, "
                + "with format yyyy-mm. If not specified, will try to retrieve all historical filings' metadata.");
        options.addOption("c", "cik", true, "The 10-digit CIK number of the filer company. "
                + "If your CIK number is less then 10 digits, prepend 0s to pad it.");
        options.addOption("t", "formtype", true, "The type of form (e.g. 10-K, 8-K).");
        options.addOption("o", "output", true, "Mandatory. The output file is a tab separated text file.");
        options.addOption("h", "help", false, "Display this message.");
        return options;
    }
    
    private static void printHelpAndExit(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("secxbrl.jar", options, true);
        System.exit(1);
    }
}
