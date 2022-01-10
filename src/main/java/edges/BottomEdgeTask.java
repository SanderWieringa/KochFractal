package edges;

import calculate.Edge;
import calculate.KochFractal;
import calculate.KochManager;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import java.util.ArrayList;
import java.util.List;

public class BottomEdgeTask extends Task implements IObserver, Runnable{

    private final List<Edge> edges = new ArrayList<>();
    private final KochFractal koch;
    private final KochManager kochManager;
    private double progress;
    ProgressBar bottomProgressBar;

    public BottomEdgeTask(KochFractal koch, KochManager kochManager, ProgressBar bottomProgressBar){
        this.koch = koch;
        this.kochManager = kochManager;
        this.bottomProgressBar = bottomProgressBar;
        koch.subscribe(this);
    }

    @Override
    public void run() {
        try{
            koch.generateBottomEdge(edges);
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
        progress = progress + 0.1;
        bottomProgressBar.setProgress(progress);
    }
}
