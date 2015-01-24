package marsRovers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static marsRovers.Rover.Direction.East;


public class Rover {
    private static final Logger logger = LogManager.getLogger(Rover.class);
    private Direction head;
    private int currentX;
    private int currentY;
    private ArrayList<Character> roverCommands;

    private Rover() {

    }

    public Rover(int currentX, int currentY, char direction) {
        this.currentX = currentX;
        this.currentY = currentY;
        roverCommands = new ArrayList<Character>();
        setHead(direction);
        String pos = "( " + Integer.toString(currentX) + " x " + Integer.toString(currentY) +" "+ head.name() + " )";
        logger.trace("Rover created at " + pos);
    }

    public Rover(int currentX, int currentY, Direction direction) {

        this.currentX = currentX;
        this.currentY = currentY;
        roverCommands = new ArrayList<Character>();
        setHead(direction);
        String pos = "( " + Integer.toString(currentX) + " x " + Integer.toString(currentY) +" "+ head.name() + " )";
        logger.trace("Rover created at " + pos);
    }

    public void addCommand(Character cmd) {
        this.roverCommands.add(cmd);
    }

    public String getPosition() {
        return "( " + Integer.toString(currentX) + " , " + Integer.toString(currentY) + " " + head.name() + " )";

    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public Direction getHead() {
        return head;
    }

    public void setHead(Direction direction) {
        this.head = direction;
        logger.trace("Head showing to " + head.name());
    }

    public void process() {
        for (int i = 0; i < roverCommands.size(); i++) {
            char cmd = roverCommands.get(i);
            logger.trace("Rover's Position was : " + getPosition());
            logger.trace("Rover Command    : " + cmd);
            if (cmd == 'M' || cmd == 'm') {
                this.move();
            } else {
                int newHeadValue = head.getValue();
                if (cmd == 'L' || cmd == 'l') {
                    newHeadValue -= 1;
                    if (newHeadValue < 0) {
                        newHeadValue = 3;
                    }
                } else if (cmd == 'R' || cmd == 'r') {
                    newHeadValue += 1;
                    if (newHeadValue > 3) {
                        newHeadValue = 0;
                    }
                }
                setHead(newHeadValue);
            }
            logger.trace("Rover's new Position : " + getPosition());
            logger.trace("");
        }
    }

    private void move() {
        Plateau myPlague = Plateau.getInstance();
        logger.trace("*** Current was X : "+currentX + " , Current was Y :"+ currentY);
        switch (getHead()) {


            case North:
                if (myPlague.isEmpty(currentX, currentY + 1)) {
                    currentY += 1;
                }
                break;
            case East:
                if (myPlague.isEmpty(currentX + 1, currentY)) {
                    currentX += 1;
                }
                break;
            case South:
                if (myPlague.isEmpty(currentX, currentY - 1)) {
                    currentY -= 1;
                }
                break;
            case West:
                if (myPlague.isEmpty(currentX - 1, currentY)) {
                    currentX -= 1;
                }
                break;
            default:
                break;
        }
        myPlague.updatePlateau();
        logger.trace("*** Current now X : "+currentX + " , Current now Y :"+ currentY);

    }

    public void setHead(char direction) {
        if (direction == 'N' || direction == 'n') {
            this.head = Direction.North;
        } else if (direction == 'E' || direction == 'e') {
            this.head = East;
        } else if (direction == 'S' || direction == 's') {
            this.head = Direction.South;
        } else if (direction == 'W' || direction == 'w') {
            this.head = Direction.West;
        }
        logger.trace("Head showing to " + head.name());

    }

    public void setHead(int direction) {
        if (direction == 0) {
            this.head = Direction.North;
        } else if (direction == 1) {
            this.head = Direction.East;
        } else if (direction == 2) {
            this.head = Direction.South;
        } else if (direction == 3) {
            this.head = Direction.West;
        }
        logger.trace("Head showing to " + head.name());

    }

    public static enum Direction {
        North(0), East(1), South(2), West(3);
        private final int value;

        private Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}
