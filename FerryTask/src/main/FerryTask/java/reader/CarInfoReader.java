package reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

public class CarInfoReader {
    private static Logger logger = LogManager.getLogger();

    public List<String> readFileData(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(fileName));
            logger.info("file was successfully read!");

        } catch (IOException e) {
            logger.info("Error while reading file: ", e);
        }
        return lines;
    }
}
