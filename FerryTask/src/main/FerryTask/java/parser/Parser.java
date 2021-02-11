package parser;

import entity.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private final static String DELIMITER = " ";
    private static final Logger logger = LogManager.getLogger();

    private static Parser instance = new Parser();


    private Parser() {
    }

    public static Parser getInstance() {
        return instance;
    }

    public List<String> parse(String line){
        List<String> carProperties = Arrays.asList(line.split(DELIMITER));
        List<String> parsedLine = new ArrayList<>(carProperties);
        logger.info("parsed line: {}", parsedLine);
        return parsedLine;
    }
}
