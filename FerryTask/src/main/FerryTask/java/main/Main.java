package main;

import entity.Car;
import parser.Parser;
import reader.CarInfoReader;

import java.util.List;

public class Main {
    private final static String FILE_PATH = "data/carInfo.txt";

    public static void main(String[] args) {
        CarInfoReader infoReader = new CarInfoReader();
        Parser carParser = Parser.getInstance();
        List<String> readerLines = infoReader.readFileData(FILE_PATH);
        for (String line: readerLines) {
            List<String> parsedLine = carParser.parse(line);
            Car car = new Car(Integer.parseInt(parsedLine.get(0)), Integer.parseInt(parsedLine.get(1)));
            car.start();
        }
    }
}
