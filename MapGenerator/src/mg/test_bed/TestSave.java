/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.test_bed;

import java.util.ArrayList;
import mg.JClassDesigner;
import mg.data.DataManager;
import mg.data.obj.MapObj;
import mg.file.FileManager;
import saf.components.AppComponentsBuilder;
import saf.components.AppDataComponent;
import saf.components.AppFileComponent;
import static saf.settings.AppStartupConstants.PATH_WORK;

/**
 *
 * @author ysoh
 */
public class TestSave {

    final static boolean IS_STATIC = true;
    final static boolean IS_FINAL = true;

    static AppDataComponent dataComponent;
    static AppFileComponent fileComponent;

    public static void main(String[] args) throws Exception {
        System.out.println("TestSave Driver started!");
        JClassDesigner jcd = new JClassDesigner();
        AppComponentsBuilder builder;
        builder = jcd.makeAppBuilderHook();
        fileComponent = builder.buildFileComponent();
        dataComponent = builder.buildDataComponent();

        ArrayList<MapObj> objList = new ArrayList<>();
        DataManager.setdObjList(objList);

        DataManager dm = (DataManager) dataComponent;
        FileManager instance = (FileManager) fileComponent;

        instance.saveData(dm, PATH_WORK + "\\DesignSaveTest.json");
        System.out.println("TestSave Driver save done!");

        instance.exportData(dm, "Z:\\Tester\\src");
        System.out.println("TestSave Driver export done!");
    }
}
