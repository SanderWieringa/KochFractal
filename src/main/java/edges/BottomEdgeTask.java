package edges;

import calculate.KochFractal;
import calculate.KochManager;

public class BottomEdgeTask implements Runnable{

    private KochFractal koch;
    private KochManager manager;

    public BottomEdgeTask(KochFractal koch, KochManager manager){
        this.koch = koch;
        this.manager = manager;
    }

    @Override
    public void run() {
        try{
            koch.generateBottomEdge();
        } catch (Exception e) {
            System.out.println(e);
        }
        manager.setCount();
    }
}
