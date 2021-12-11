/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edges.BottomEdgeTask;
import edges.LeftEdgeTask;
import edges.RightEdgeTask;
import fun3kochfractalfx.FUN3KochFractalFX;
import javafx.application.Platform;
import timeutil.TimeStamp;

/**
 *
 * @author Nico Kuijpers
 * Modified for FUN3 by Gertjan Schouten
 */
public class KochManager {

    private final List<Edge> edges;
    private final FUN3KochFractalFX application;
    private final TimeStamp tsCalc;
    private final TimeStamp tsDraw;
    private int count = 0;
    private BottomEdgeTask bottomEdgeTask;
    private LeftEdgeTask leftEdgeTask;
    private RightEdgeTask rightEdgeTask;
    
    public KochManager(FUN3KochFractalFX application) {
        this.edges = new ArrayList<>();
        this.application = application;
        this.tsCalc = new TimeStamp();
        this.tsDraw = new TimeStamp();
    }

    public synchronized void setCount() throws Exception {
        this.count++;
        if (count == 3) {
            edges.addAll((ArrayList<Edge>)bottomEdgeTask.call());
            edges.addAll((ArrayList<Edge>)leftEdgeTask.call());
            edges.addAll((ArrayList<Edge>)rightEdgeTask.call());

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    tsCalc.setEnd("End calculating");
                    application.setTextNrEdges("" + bottomEdgeTask.getKoch().getNrOfEdges());
                    application.setTextCalc(tsCalc.toString());
                }
            });
            application.requestDrawEdges();
            this.count = 0;
        }
    }
    
    public void changeLevel(int nxt) throws Exception {
        if (bottomEdgeTask != null) {
            bottomEdgeTask.cancel();
        }
        if (leftEdgeTask != null) {
            leftEdgeTask.cancel();
        }
        if (rightEdgeTask != null) {
            rightEdgeTask.cancel();
        }

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        edges.clear();
        application.clearKochPanel();
        tsCalc.init();
        tsCalc.setBegin("Begin calculating");

        executorService.execute(bottomEdgeTask = new BottomEdgeTask(new KochFractal(nxt), this));
        executorService.execute(leftEdgeTask = new LeftEdgeTask(new KochFractal(nxt), this));
        executorService.execute(rightEdgeTask = new RightEdgeTask(new KochFractal(nxt), this));
        executorService.shutdown();
    }
    
    public void drawEdges() {
        tsDraw.init();
        tsDraw.setBegin("Begin drawing");
        application.clearKochPanel();
        for (Edge e : edges) {
            application.drawEdge(e);
        }
        tsDraw.setEnd("End drawing");
        application.setTextDraw(tsDraw.toString());
    }
}
