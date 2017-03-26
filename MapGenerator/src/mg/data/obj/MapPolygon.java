/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.data.obj;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Line;
import mg.data.info.RenderInfo;

/**
 *
 * @author Mukum
 */
public class MapPolygon {

    private ArrayList<MapPoint> vertices;

    public MapPolygon() {
        this.vertices = new ArrayList<>();
    }

    public void addPoint(MapPoint p) {
        vertices.add(p);
    }

    public void rmPoint(MapPoint p) {
        vertices.remove(p);
    }

    public List<MapPoint> getPoints() {
        return vertices;
    }
    
    public Line[] getLines(){
        int N = vertices.size();
        Line[] ret = new Line[N];
        
        for(int i = 0; i < N; i++){
            ret[i] = new Line();
            
            RenderInfo ri = vertices.get(i).getRenderInfo();
            
            ret[i].startXProperty().bindBidirectional(ri.posXProperty());
            ret[i].startYProperty().bindBidirectional(ri.posYProperty());
            
            RenderInfo ri2 = vertices.get((i+1)%N).getRenderInfo();
            ret[i].endXProperty().bindBidirectional(ri2.posXProperty());
            ret[i].endYProperty().bindBidirectional(ri2.posYProperty());
        }
        
        return ret;
    }

    public void reset() {
        this.vertices = new ArrayList<>();
    }
}
