import java.util.Random;

public class HealthyHuman extends Human {
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
    public HealthyHuman(boolean randomAge, Field field, Location location) {
        super(field, location);
        age = 0;
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
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

    public int getAge(){
        return age;
    }

    public void setAge(int newAge){
        age = newAge;
    }

    public void hasBeenCured(){
        cured = true;
    }
}