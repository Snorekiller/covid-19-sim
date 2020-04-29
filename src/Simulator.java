import java.io.IOException;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;
import java.util.logging.Logger;

/**
 * A simple predator-prey simulator, based on a rectangular field containing
 * rabbits and foxes.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 */
public class Simulator {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a rabbit will be created in any given position.
    private static final double SickHumans = 0.0002;
    // The probability that a rabbit will be created in any given position.
    private static final double HealthyHumans = 0.08;

    // Lists of animals in the field.
    public List<HealthyHuman> healthyHumen;
    public List<SickHuman> sickHumen;
    public List<CuredHuman> curedHumen;
    public List<DeadHumans> deadHumens;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    private int numberOfDeaths=0;

    // private WriteLogEntriesToLogFile logger;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() throws IOException {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     *
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) throws IOException {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be >= zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        //   logger = new WriteLogEntriesToLogFile();
        healthyHumen = new ArrayList<>();
        sickHumen = new ArrayList<>();
        curedHumen = new ArrayList<>();
        deadHumens = new ArrayList<>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(HealthyHuman.class, Color.DARK_GRAY);
        view.setColor(CuredHuman.class, Color.GREEN);
        view.setColor(SickHuman.class, Color.RED);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long
     * period (4000 steps).
     */
    public void runLongSimulation() {
        simulate(4000);
    }

    /**
     * Run the simulation for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     *
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();
            delay(60);   // uncomment this to run more slowly

        }
    }

    /**
     * Run the simulation from its current state for a single step. Iterate
     * over the whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep() {
        step++;
        //     logger.simulatorStep(Integer.toString(step));

        // Let all rabbits act.
        for (Iterator<HealthyHuman> it = healthyHumen.iterator(); it.hasNext(); ) {
            HealthyHuman healthyHuman = it.next();
            healthyHuman.move();
            if((healthyHuman.isSick())&&(!healthyHuman.isCured())){
                it.remove();
                Field newField = healthyHuman.getField();
                Location newLocation = healthyHuman.getLocation();
                int newAge = healthyHuman.getAge();
                healthyHuman.clearPosition();
                SickHuman sickHuman = new SickHuman(false, newField, newLocation);
                sickHuman.setAge(newAge);
                sickHumen.add(sickHuman);
            }
        }
        for (Iterator<SickHuman> it = sickHumen.iterator(); it.hasNext(); ) {
            SickHuman sickHuman = it.next();
            sickHuman.move();
            if (!sickHuman.isAlive()) {
                //            logger.foxLog(fox.getInfo());
                it.remove();
                DeadHumans deadHumans = new DeadHumans(sickHuman.getAge());
                deadHumens.add(deadHumans);
                view.setDead();
            }
            if(sickHuman.isCured()){
                it.remove();
                Field newField = sickHuman.getField();
                Location newLocation = sickHuman.getLocation();
                int newAge = sickHuman.getAge();
                sickHuman.clearPosition();
                CuredHuman curedHuman = new CuredHuman(newAge, newField, newLocation);
                curedHumen.add(curedHuman);
            }
        }
        for (Iterator<CuredHuman> it = curedHumen.iterator(); it.hasNext(); ) {
            CuredHuman curedHuman = it.next();
            curedHuman.move();
        }



        //   logger.simulatorLog(Integer.toString(foxes.size()),Integer.toString(rabbits.size()));

        view.showStatus(step, field);
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        healthyHumen.clear();
        sickHumen.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                if (rand.nextDouble() <= HealthyHumans) {
                    Location location = new Location(row, col);
                    HealthyHuman healthyHuman = new HealthyHuman(true, field, location);
                    healthyHumen.add(healthyHuman);
                } else if ((sickHumen.size() < 1) && (rand.nextDouble() <= SickHumans)) {
                    Location location = new Location(row, col);
                    SickHuman sickHuman = new SickHuman(true, field, location);
                    sickHumen.add(sickHuman);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Pause for a given time.
     *
     * @param millisec The time to pause for, in milliseconds
     */
    private void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException ie) {
            // wake up
        }
    }
}
