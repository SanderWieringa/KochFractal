package edges;

import calculate.KochFractal;
import calculate.KochManager;

public class RightEdgeTask implements Runnable{

    private KochFractal koch;
    private KochManager manager;

    public RightEdgeTask(KochFractal koch, KochManager manager){
        this.koch = koch;
        this.manager = manager;
    }

    @Override
    public void run() {
        try{
            koch.generateRightEdge();
        } catch (Exception e) {
            System.out.println(e);
        }
        manager.setCount();
    }
}
