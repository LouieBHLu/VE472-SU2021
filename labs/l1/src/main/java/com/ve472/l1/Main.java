package com.ve472.l1;

import org.apache.commons.cli.*;
import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        ArgParse path = new ArgParse(args);
        Cinema cinema = new Cinema(path.getHallPath());
        cinema.checkQuery(path.getQueryPath());
        cinema.printTickets();
    }
}

class ArgParse{
    private String HallPath;
    private String QueryPath;

    public ArgParse(String[] args){
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine input;

        Option help = new Option("h", "help", false, "print this message");
        help.setRequired(false);
        Option hall = new Option(null, "hall", true, "path of the hall config directory");
        hall.setRequired(true);
        Option query = new Option(null, "query", true, "query of customer orders");
        query.setRequired(true);

        options.addOption(help);
        options.addOption(hall);
        options.addOption(query);

        try{
            input = parser.parse(options, args);
            if(input.hasOption(help.getOpt())){
                System.out.println("has help!");
                formatter.printHelp("cinema", options);
                System.exit(0);
            }
            HallPath = input.getOptionValue("hall");
            QueryPath = input.getOptionValue("query");
        } catch (ParseException error){
            formatter.printHelp("cinema", options);
            System.exit(0);
        }

    }

    public String getHallPath(){
        return HallPath;
    }
    public String getQueryPath(){
        return QueryPath;
    }
}