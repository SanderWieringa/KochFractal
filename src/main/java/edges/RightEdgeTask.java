package edges;

import calculate.Edge;
import calculate.KochFractal;
import calculate.KochManager;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;

public class RightEdgeTask extends Task implements Runnable{

    private final List<Edge> edges = new ArrayList<>();
    private final KochFractal koch;
    private final KochManager kochManager;

    public RightEdgeTask(KochFractal koch, KochManager kochManager){
        this.koch = koch;
        this.kochManager = kochManager;
    }

    @Override
    public void run() {
        try{
            koch.generateRightEdge(edges);
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
