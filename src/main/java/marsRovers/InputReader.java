package marsRovers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Hakan on 22.1.2015.
 */
public class InputReader {
    private static final Logger logger = LogManager.getLogger(InputReader.class);
    private static final Object lock = new Object();
    private static InputReader instace;
    private int plateauX, plateauY;

    private ArrayList<Rover> rovers;

    private InputReader() {
        logger.trace("Singleton InputReader Created");
        rovers = new ArrayList<Rover>();
        int roverCount = 0;
        logger.trace("Trying to open input.txt");
        InputStreamReader isReader = new InputStreamReader(
                MainApp.class.getResourceAsStream("..//input.txt")
        );
        BufferedReader br = new BufferedReader(isReader);


        String thisLine = null;
        try {
            int lineCount = 0;
            while ((thisLine = br.readLine()) != null) {

                logger.entry("Line " + lineCount);
                if (lineCount == 0) {

                    int x = Integer.parseInt(thisLine.split(" ")[0]);
                    int y = Integer.parseInt(thisLine.split(" ")[1]);
                    logger.trace("Line 1 - X = " + x + " .. Y= " + y);
                    this.plateauX = x+1;
                    this.plateauY = y+1;
                } else {
                    // each 2 entries equal to a new rover
                    if (lineCount % 2 == 1) {

                        int x = Integer.parseInt(thisLine.split(" ")[0]);
                        int y = Integer.parseInt(thisLine.split(" ")[1]);
                        char direction = thisLine.split(" ")[2].charAt(0);
                        Rover newRover = new Rover(x, y, direction);
                        rovers.add(newRover);
                        logger.trace("New Rover added.");
                    } else {
                        for (int i = 0; i < thisLine.length(); i++) {
                            rovers.get(roverCount).addCommand(thisLine.charAt(i));
                        }
                        roverCount++;
                    }

                }

                logger.exit(thisLine);
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.trace("Total Rovers : " + roverCount);
    }

    public static InputReader getInstace() {
        if (instace == null) {
            synchronized (lock) {
                if (instace == null) {
                    instace = new InputReader();
                }
            }
        }
        return instace;
    }

    public ArrayList<Rover> getRovers() {
        return rovers;
    }

    public int getPlateauX() {
        return plateauX;
    }

    public int getPlateauY() {
        return plateauY;
    }

}
