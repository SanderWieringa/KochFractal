package edges;

import calculate.KochFractal;
import calculate.KochManager;

public class LeftEdgeTask implements Runnable{

    private KochFractal koch;
    private KochManager manager;

    public LeftEdgeTask(KochFractal koch, KochManager manager){
        this.koch = koch;
        this.manager = manager;
    }

    @Override
    public void run() {
        try{
            koch.generateLeftEdge();
        } catch (Exception e) {
            System.out.println(e);
        }
        manager.setCount();
    }
}
