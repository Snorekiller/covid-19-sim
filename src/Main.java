import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String args[]) throws InterruptedException, IOException {
        Simulator simulator = new Simulator();
        simulator.runLongSimulation();

        TimeUnit.SECONDS.sleep(8);//sets delay timer
        simulator.reset();

    }
}
