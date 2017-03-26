/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.data.obj;

import javafx.scene.Node;
import javax.json.JsonObject;
import mg.data.info.RenderInfo;
import mg.file.FileManager;
import mg.gui.PointRender;

public class MapPoint extends MapObj {

    public static final double DEFAULT_POINT_SIZE = 7;

    public MapPoint(double posX, double posY) {
        this.ri = new RenderInfo(posX, posY, DEFAULT_POINT_SIZE, DEFAULT_POINT_SIZE);
    }

    @Override
    public JsonObject toJsonObject() {
        return FileManager.makeDesignObjJsonObject(this);
    }

    @Override
    public Node getRenderObject() {
        shape = new PointRender(this);
        return shape;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("Point(");
        s.append(ri.toString());
        s.append(")");

        return s.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (!(that instanceof MapPoint)) {
            return false;
        }

        MapPoint target = (MapPoint) that;

        if (!ri.equals(target.ri)) {
            return false;
        }

        return true;
    }
}
