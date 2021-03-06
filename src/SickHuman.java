import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SickHuman extends Human {
    // Characteristics shared by all foxes (class variables).


    // The maximum age of a human
    private static final int MAX_AGE = 100;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Individual characteristics (instance fields).
    public int timeSick = 0;
    public int timeToCured = 100;
    // The human's age.
    public int age;

   // private double chanceOfDeath=0.004 + (age/10000);
    private double chanceOfDeath = getChanceOfDeath();

    /**
     * Create a human. A human can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the human will have random age and hunger level.
     * @param field     The field currently occupied.
     * @param location  The location within the field.
     */
    public SickHuman(boolean randomAge, Field field, Location location,boolean quarantined) {
        super(field, location,quarantined);
        age = 0;
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }

    public void incrementTimeToCured(){
        timeSick++;
        if(timeSick>timeToCured){
            cured = true;
        }
    }

    public void checkIfDead(){
     //   double prob = manDeathTable[age];
        //if( Math.random() < chanceOfDeath )
        if( Math.random() < getChanceOfDeath() )
            setDead();
    }

    public void move() {
        if (isAlive()) {
            incrementTimeToCured();

            // Move towards a source of food if found.
            Location newLocation = findNextSpot();
            if (newLocation == null) {
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            Object border = getField().getObjectAt(newLocation);
            // See if it was possible to move.
            if (newLocation != null && !(border instanceof Borders)) {
                setLocation(newLocation);
            } else {
            }
            checkIfDead();
        }
    }


    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findNextSpot() {
        Location newLocation;
        List<Location> adjacent = getField().adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        int randomNext = rand.nextInt(adjacent.size());
        for (int i=0;i>randomNext;i++) {
            it.next();
        }
        Location where = it.next();
        Object human = getField().getObjectAt(where);
        if(human instanceof HealthyHuman){
            HealthyHuman healthyHuman = (HealthyHuman) human;

            healthyHuman.setInQuarantine(healthyHuman.isInQuarantine());
            healthyHuman.setSick();
        }
        newLocation = where;
        return newLocation;
    }

//    public String getInfo(){
//        return ("Age:" + Integer.toString(age)+"\n" + "Hunger:" + Integer.toString(foodLevel));
//    }

    public int getAge(){
        return age;
    }

    public void setAge(int newAge){
        age = newAge;
    }

    public double getChanceOfDeath(){
        double age = getAge();
        double chanceOfDeath =0;
        if(age <10){
            chanceOfDeath = 0;
        }
        if(age > 10 && age <20){
            chanceOfDeath = 0.00005;
        }
        if(age > 20 && age < 40){
            chanceOfDeath = 0.0001;
        }
        if(age > 40 && age < 70){
            chanceOfDeath = 0.0005;
        }
        if(age > 70){
            chanceOfDeath = 0.001;
        }
        return chanceOfDeath;
    }
}
