package marsRovers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Plateau {
    private static final Logger logger = LogManager.getLogger(Plateau.class);
    private static final Object lock = new Object();
    private static Plateau instance = null;
    private final int maxX;
    private final int maxY;
    private int[][] plateau;
    private ArrayList<Rover> rovers;


    private Plateau() {
        this.maxX = 0;
        this.maxY = 0;
        logger.error("WRONG Plateau Creation");
    }

    private Plateau(int x, int y) {
        this.maxX = x;
        this.maxY = y;
        plateau = new int[x][y];
        resetPlateau();
        rovers = new ArrayList<Rover>();
        logger.trace("Singleton Plateau " + x + "x" + y + " Created");
    }

    public static Plateau getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    int x = InputReader.getInstace().getPlateauX();
                    int y = InputReader.getInstace().getPlateauY();
                    instance = new Plateau(x, y);
                }
            }
        }
        return instance;
    }

    public ArrayList<Rover> getRovers() {
        return rovers;
    }

    public void addRover(Rover rover) {
        this.rovers.add(rover);
        logger.trace("Adding New Rover : ( " + rover.getCurrentX() + "x" + rover.getCurrentY() + " " + rover.getHead() + " )");
        this.plateau[rover.getCurrentX()][rover.getCurrentY()] = rovers.size();
    }

    private void resetPlateau() {
        for (int y = 0; y < this.maxY; y++) {
            for (int x = 0; x < this.maxX; x++) {
                plateau[x][y] = 0;
            }
        }
    }

    public void updatePlateau() {

        // first we delete whole matrix and just please
        // current places of rovers in matrix
        resetPlateau();
        int roverCount = 1;
        for (Rover rover : getRovers()) {
            plateau[rover.getCurrentX()][rover.getCurrentY()] = roverCount;
            roverCount++;
        }
    }

    public Boolean isEmpty(int x, int y) {
        // a method for checking if the given X and Y point empty or not
        if (x >= this.maxX || y >= this.maxY || x < 0 || y < 0) {
            return false;
        } else {
            if (plateau[x][y] == 0) {
                return true;
            } else {
                return false;
            }
        }
    }

    public void showPlateau() {
        // a kind of small presentation of plateau with * and rover's number
        updatePlateau();
        System.out.println();
        for ( int y =0 ; y < maxY ; y++)
        {
            for (int x = 0 ; x < maxX;x++)
            {
                if(plateau[x][maxY-1-y]!=0){
                    System.out.print(plateau[x][maxY-1-y]);
                }else{
                    System.out.print("*");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void showRoverPositions() {
        System.out.println();
        int roverCount=1;
        for (Rover rover: getRovers())
        {
            System.out.println("Rover "+ roverCount + " is at "+ rover.getPosition());
            roverCount++;
        }
        System.out.println();
    }

}
