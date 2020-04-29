public class Human {
    private boolean alive;

    private boolean sick = false;

    protected boolean cured = false;

    private Location location;

    private Field field;

    protected int age;

    public Human( Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }

    /**
     * Place the fox at the new location in the given field.
     * @param newLocation The fox's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the fox's location.
     * @return The fox's location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Indicate that the fox is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    protected void clearPosition(){
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    protected void setSick(){
        sick = true;
    }

    /**
     * Check whether the fox is alive or not.
     * @return True if the fox is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    protected Field getField (){
        return field;
    }

    public boolean isSick() {
        return sick;
    }

    public boolean isCured() {
        return cured;
    }

}
