package marsRovers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Created by Hakan on 21.1.2015.
 */
public class MainApp {
    private static final Logger logger = LogManager.getLogger(MainApp.class);

    public static void main(final String... args) {
        logger.trace("Program Başladı");

        InputReader.getInstace();
        Plateau myPlateau = Plateau.getInstance();

        myPlateau.showPlateau();
        myPlateau.showRoverPositions();

        for (Rover rover : InputReader.getInstace().getRovers()) {
            myPlateau.addRover(rover);
        }

        myPlateau.showPlateau();
        myPlateau.showRoverPositions();

        for (Rover rover : Plateau.getInstance().getRovers()) {
            rover.process();
        }

        myPlateau.showPlateau();
        myPlateau.showRoverPositions();

        logger.trace("Program Bitti");
    }
}
