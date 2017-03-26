package mg.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import mg.data.obj.MapObj;
import mg.data.obj.MapPoint;
import mg.data.obj.MapPolygon;
import saf.AppTemplate;
import saf.components.AppDataComponent;

/**
 * This class serves as the data management component for this application.
 *
 * @author Richard McKenna
 * @author Yun Joon Soh
 * @version 1.0
 */
public class DataManager implements AppDataComponent {

    // THIS IS A SHARED REFERENCE TO THE APPLICATION
    protected static AppTemplate app;

    protected static ArrayList<MapObj> dObjList  = new ArrayList<>();

    private final static Stack<DataModifyLog> undoLog  = new Stack<>();
    private final static Stack<DataModifyLog> redoLog  = new Stack<>();

    static ObjectProperty<MapObj> selectedDObj;

    static ObjectProperty<Node> selectedCanvas = new SimpleObjectProperty<>();

    public final static BooleanProperty IS_DBOX_SELECTED = new SimpleBooleanProperty();

    private final static List<MapPolygon> polygons = new ArrayList<>();
    private static MapPolygon buf_polygon = new MapPolygon();
    
    public static void addPoint(MapPoint p){
        buf_polygon.addPoint(p);
    }
    
    public static void pushBuffer(){
        polygons.add(buf_polygon);
        buf_polygon = new MapPolygon();
    }
    
    public static List<MapPolygon> getPolygons(){
        return polygons;
    }

    public static boolean isUndoLogEmpty() {
        return undoLog.isEmpty();
    }

    public static boolean isRedoLogEmpty() {
        return redoLog.isEmpty();
    }

    public static void pushToUndoLog(DataModifyLog dataModifyLog) {
        undoLog.push(dataModifyLog);
        System.out.println("Pushed to undoList");
    }

    public static void pushToRedoLog(DataModifyLog dataModifyLog) {
        redoLog.push(dataModifyLog);
    }

    public static DataModifyLog popFromUndoLog() {
        DataModifyLog ret = undoLog.pop();
        redoLog.push(ret);
        return ret;
    }

    public static DataModifyLog popFromRedoLog() {
        DataModifyLog ret = redoLog.pop();
        undoLog.push(ret);
        return ret;
    }

    public static void resetLog() {
        undoLog.clear();
        redoLog.clear();
    }

    /**
     * THis constructor creates the data manager and sets up the
     *
     *
     * @param initApp The application within which this data manager is serving.
     */
    public DataManager(AppTemplate initApp) throws Exception {
        // KEEP THE APP FOR LATER
        app = initApp;
        
        selectedDObj = new SimpleObjectProperty<>();

        selectedCanvas.addListener((obs, oldNode, newNode) -> {
            if (oldNode != null) {
                oldNode.setStyle("");
            }
            if (newNode != null) {
                newNode.setStyle("-fx-effect: innershadow(gaussian, #039ed3, 10, 1.0, 0, 0);");
            }
        }
        );

    }

    @Override
    public void reset() {
        dObjList.clear();

        undoLog.clear();
        redoLog.clear();

        selectedCanvas.set(null);
        selectedDObj.set(null);
        
        polygons.clear();
        buf_polygon.clear();
    }

    public static AppTemplate getApp() {
        return app;
    }

    public static ArrayList<MapObj> getDObjList() {
        return dObjList;
    }

    public static ObjectProperty<MapObj> getSelectedDObj() {
        return selectedDObj;
    }

    public static ObjectProperty<Node> getSelectedCanvas() {
        return selectedCanvas;
    }

    public static void setSelected(MapObj dObj) {
        if (dObj instanceof MapObj) {
            selectedDObj.set(dObj);
        } else if (dObj == null) {
            selectedDObj.set(null);
        }
    }


    public static JsonArray getDObjListInJson() {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (MapObj dObj : dObjList) {
            arrayBuilder.add(dObj.toJsonObject());
        }

        return arrayBuilder.build();
    }

    public static void setdObjList(ArrayList<MapObj> dObjList) {
        DataManager.dObjList = dObjList;
    }

}
