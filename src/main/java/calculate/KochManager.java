/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
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
    
    private KochFractal koch;
    private ArrayList<Edge> edges;
    private FUN3KochFractalFX application;
    private TimeStamp tsCalc;
    private TimeStamp tsDraw;
    private int count = 0;

    
    public KochManager(FUN3KochFractalFX application) {
        this.edges = new ArrayList<>();
        this.koch = new KochFractal(this);
        this.application = application;
        this.tsCalc = new TimeStamp();
        this.tsDraw = new TimeStamp();
    }

    public synchronized void setCount() {
        this.count++;
        if (count == 3) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    tsCalc.setEnd("End calculating");
                    application.setTextNrEdges("" + koch.getNrOfEdges());
                    application.setTextCalc(tsCalc.toString());
                }
            });
            application.requestDrawEdges();
            this.count = 0;
        }
    }
    
    public void changeLevel(int nxt) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        edges.clear();
        koch.setLevel(nxt);
        tsCalc.init();
        tsCalc.setBegin("Begin calculating");

        executorService.execute(new BottomEdgeTask(koch, this));
        executorService.execute(new LeftEdgeTask(koch, this));
        executorService.execute(new RightEdgeTask(koch, this));
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
    
    public void addEdge(Edge e) {
        edges.add(e);
    }
}
