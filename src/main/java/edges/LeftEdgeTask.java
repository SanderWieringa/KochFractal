package edges;

import calculate.Edge;
import calculate.KochFractal;
import calculate.KochManager;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import java.util.ArrayList;
import java.util.List;

public class LeftEdgeTask extends Task implements IObserver, Runnable{

    private final List<Edge> edges = new ArrayList<>();
    private final KochFractal koch;
    private final KochManager kochManager;
    private double progress;
    ProgressBar leftProgressBar;
    double count = 0;

    public LeftEdgeTask(KochFractal koch, KochManager kochManager, ProgressBar leftProgressBar){
        this.koch = koch;
        this.kochManager = kochManager;
        this.leftProgressBar = leftProgressBar;
        koch.subscribe(this);
    }

    @Override
    public void run() {
        try {
            koch.generateLeftEdge(edges);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            kochManager.setCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public KochFractal getKoch() {
        return koch;
    }

    @Override
    public Object call() throws Exception {
        return edges;
    }

    @Override
    public void update() {
        count++;
        progress = (count / (koch.getNrOfEdges() / 3.0));
        leftProgressBar.setProgress(progress);
    }
}
