public class Borders {
    private Location location;
    private Field field;
    public Borders(Field field, Location location){
        setLocation(location);
        this.field = field;
    }
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
    }
    public Location getLocation()
    {
        return location;
    }

    public void clearPosition(){
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }
}

