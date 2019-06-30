package edu.wm.cs.cs301.amazebylukedyer.gui;

import android.graphics.Bitmap;

import edu.wm.cs.cs301.amazebylukedyer.generation.MazeConfiguration;

/**
 * Singleton class to hold and share data between activities
 */
public class DataHolder {

    private static DataHolder instance;
    //variables that will be shared
    private Bitmap bitmap;
    private Controller controller;
    private MazeConfiguration mc;
    public static DataHolder Instance() {
        if(instance == null) {
            instance = new DataHolder();
        }
        return instance;
    }

    public void setBitmap(Bitmap bit) {
        this.bitmap = bit;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }


    public void setMazeConfiguration(MazeConfiguration mazConfig) { this.mc = mazConfig;}
    public MazeConfiguration getMazeConfiguration() { return mc; }





}
