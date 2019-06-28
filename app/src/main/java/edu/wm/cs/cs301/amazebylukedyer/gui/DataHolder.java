package edu.wm.cs.cs301.amazebylukedyer.gui;

import android.graphics.Bitmap;

import edu.wm.cs.cs301.amazebylukedyer.generation.MazeConfiguration;

/**
 * Singleton class to hold and share data between activities
 */
public class DataHolder {

    //variables that will be shared
    private Bitmap bitmap;
    private Controller controller;
    private MazeConfiguration mc;
    private static final DataHolder dh = new DataHolder();

    public void setBitmap(Bitmap bit) {
        this.bitmap = bit;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

//    public void setController(Controller con) {
//        this.controller = con;
//    }
//    public Controller getController() {
//        return controller;
//    }

    public void setMazeConfiguration(MazeConfiguration mazConfig) { this.mc = mazConfig;}
    public MazeConfiguration getMazeConfiguration() { return mc; }

    public static DataHolder getInstance() {
        return dh;
    }



}
