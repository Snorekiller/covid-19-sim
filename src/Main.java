import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String args[]) throws InterruptedException, IOException {
        // set parameters of simulation
        boolean borders = false;
        boolean ageDeadlyness = false;
        boolean quaranten = false;

        Simulator simulator = new Simulator(borders,ageDeadlyness, quaranten);
        simulator.runLongSimulation();

        simulator.reset();

    }
}
