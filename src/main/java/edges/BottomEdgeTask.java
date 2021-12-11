package edges;

import calculate.Edge;
import calculate.KochFractal;
import calculate.KochManager;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class BottomEdgeTask extends Task implements Runnable{

    private final List<Edge> edges = new ArrayList<>();
    private final KochFractal koch;
    private final KochManager kochManager;

    public BottomEdgeTask(KochFractal koch, KochManager kochManager){
        this.koch = koch;
        this.kochManager = kochManager;
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
}
