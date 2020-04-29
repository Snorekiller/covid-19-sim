import java.util.Random;

public class CuredHuman extends Human{

    // The maximum age of a human
    private static final int MAX_AGE = 100;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).

    // The human's age.
    public int age;


    /**
     * Create a human. A human can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the human will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public CuredHuman(int Age,Field field, Location location) {
        super(field, location);
        age = Age;
    }

    public void move() {
        if (isAlive()) {
            // Move towards a source of food if found.
            Location newLocation = null;
            if (newLocation == null) {
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
            }
        }
    }
}
