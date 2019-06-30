package edu.wm.cs.cs301.amazebylukedyer.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Handles maze graphics.
 */
public class MazePanel extends View {
    // TODO: please check
    // https://developer.android.com/training/custom-views/create-view
    // https://developer.android.com/training/custom-views/custom-drawing
    // on how to implement your own View class
    //

    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;

    /**
     * Constructor with one context parameter.
     * @param context
     */
    public MazePanel(Context context) {
        // call super class constructor as necessary
        // TODO: initialize instance variables as necessary
        super(context);
        bitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        paint = new Paint();
        canvas = new Canvas(bitmap);

    }
    /**
     * Constructor with two parameters: context and attributes.
     * @param context
     * @param app
     */
    public MazePanel(Context context, AttributeSet app) {
        // call super class constructor as necessary
        // TODO: initialize instance variables as necessary
        super(context, app);
        bitmap = Bitmap.createBitmap(Constants.VIEW_WIDTH, Constants.VIEW_HEIGHT, Bitmap.Config.ARGB_8888);
        paint = new Paint();
        canvas = new Canvas(bitmap);
    }


    /**
     * Draws given canvas.
     * @param c
     */
    @Override
    public void onDraw(Canvas c) {
        // TODO: draw bitmap
        super.onDraw(c);
        c.drawBitmap(bitmap, 0, 0, paint);
        System.out.println("onDraw called");


    }

    /**
     * Measures the view and its content to determine the measured width and the measured height.
     * @param width
     * @param height
     */
    @Override
    public void onMeasure(int width, int height) {
        width = this.getMeasuredWidth();
        height = this.getMeasuredHeight();
        setMeasuredDimension(getWidth(), getHeight());
    }

    /**
     * Updates maze graphics.
     */
    public void update() {
        //TODO: update maze graphics
        invalidate();
    }

    /**
     * Takes in color string, sets paint color to corresponding color.
     * @param c
     */
    public void setColor(String c) {
        // TODO: same as setColor(int) but for string parameters
        if ("Red".equals(c)) {
            paint.setColor(Color.RED);
        }
        else if ("Blue".equals(c)) {
            paint.setColor(Color.BLUE);
        }
        else if("Black".equals(c)) {
            paint.setColor(Color.BLACK);
        }
        else if("Gray".equals(c)) {
            paint.setColor(Color.GRAY);
        }
        else if("White".equals(c)) {
            paint.setColor(Color.WHITE);
        }
        else if ("Yellow".equals(c)) {
            paint.setColor(Color.YELLOW);
        }
        else if ("DarkGray".equals(c)) {
            paint.setColor(Color.DKGRAY);

        }
        else if ("Pink".equals(c)) {
            paint.setColor(Color.MAGENTA);
        }
        else if ("Cyan".equals(c)) {
            paint.setColor(Color.CYAN);
        }
    }

    /**
     * Sets paint object color attribute to given color.
     * @param color
     */
    public void setColor(int color) {
        // TODO: set the current color
        paint.setColor(color);
    }

    public void setColor(int color1, int color2, int color3) {
        paint.setColor(Color.rgb(color1,color2,color3));
    }
    /**
     * Takes in color integer values [0-255], returns corresponding color-int value.

     */
//    public static int getColorEncoding(int red, int green, int blue) {
//        // TODO: provide rgb color encoding
//        int color = Color.rgb(red, green, blue);
//        return color;
//    }

    //modified from StackOverflow
    public static class ColorEncoding extends Color {

        private int RGB = -1;
        private int r, g, b, a = 255;

        public ColorEncoding(int i, int j, int k) {
            super();
            r = i;
            g = j;
            b = k;
        }
        public ColorEncoding(int rgb) {
            super();
            RGB = rgb;
        }

        public int getR() {
            return r;
        }

        public int getG() {
            return g;
        }
        public int getB() {
            return b;
        }
    }

    /**
     * Returns the RGB value representing the current color.
     * @return integer RGB value
     */
    public int getColor() {
        // TODO return the current color setting
        return 1;
    }

    /**
     * Takes in rectangle params, fills rectangle in canvas based on these.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillRect(int x, int y, int width, int height) {
        // draw a filled rectangle on the canvas, requires decision on its color
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new Rect(x,y, x+width, y+height), paint);
    }

    /**
     * Takes in polygon params, fills polygon in canvas based on these.
     * Paint is always that for corn.
     * @param xPoints
     * @param yPoints
     * @param nPoints
     */
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints){
        // translate the points into a path
        // draw a path on the canvas
        paint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.moveTo(xPoints[0], yPoints[0]);
        for (int n = 1; n < nPoints; n++) {
            path.lineTo(xPoints[n], yPoints[n]);
        }
        path.lineTo(xPoints[0], yPoints[0]);
        canvas.drawPath(path, paint);
    }

    /**
     * Takes in line params, draws line in canvas based on these.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        // TODO: draw a line on the canvas
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    /**
     * Takes in oval params, fills oval in canvas based on these.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillOval(int x, int y, int width, int height) {
        // TODO: draw an oval on the canvas
        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(new RectF(x,y,x+width,y+height),paint);

    }

}