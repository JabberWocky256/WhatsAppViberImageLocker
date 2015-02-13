package com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by opereverzyev on 13.02.15.
 */
public class DisplaySize {
    private String TAG = getClass().getSimpleName().toString();
    private Display display;

    public Size getLargeElementSize(Context context){
        Size result = getDisplaySize(context);
        result.height = result.width/2;

        Log.d(TAG, "get large element size: width = " + result.width + ", height = " + result.height);
        return result;
    }

    public Size getMediumElementSize(Context context){
        Size result = getDisplaySize(context);
        result.divideWidth(1.5);
        result.height = getSmallElementSize(context).height;

        Log.d(TAG, "get medium element size: width = " + result.width + ", height = " + result.height);
        return result;
    }

    public Size getSmallElementSize(Context context){
        Size result = getDisplaySize(context);
        result.divideWidth(3);
        result.height = result.width*1.2;

        Log.d(TAG, "get small element size: width = " + result.width + ", height = " + result.height);
        return result;
    }


    public Size getDisplaySize(Context context){
            int width;
            int height;
            Size display;

            Display displaySize = initDisplay(context);

            if (Build.VERSION.SDK_INT >= 13) {
                Point size = new Point();
                displaySize.getSize(size);
                width = size.x;
                height = size.y;
            } else {
                width = displaySize.getWidth();
                height = displaySize.getHeight();
            }
            display = new Size(height, width);

            Log.d(TAG, "display size: width = " + width + ", height = " + height);

        return display;
    }

    private Display initDisplay(Context context){
        Log.d(TAG, "init window manager");
        if(display != null)
            return display;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay();
    }

    public class Size{
        public double height;
        public double width;

        public Size(int height, int width) {
            this.height = height;
            this.width = width;
        }

        public void divideWidth (double dividerWidth){
            width /= dividerWidth;
        }

        public void minus(int variable){
            height -= variable;
            width -= variable;
        }

        private double getSquare(){
            return height*width;
        }

        public int compareTo(Object o){
            Size anotherSize;
            try{
                anotherSize = (Size)o;
            } catch (ClassCastException cce){
                return -1;
            }

            if(this.equals(anotherSize)){
                return 0;
            } else if(anotherSize.getSquare()> this.getSquare()) {
                return -10;
            } else {
                return 10;
            }
        }

        @Override
        public boolean equals(Object o) {
            Size anotherSize;
            try{
                anotherSize = (Size)o;
            } catch (ClassCastException cce){
                return false;
            }

            if(this.getSquare() > anotherSize.getSquare())
                return true;
            else
                return false;
        }


        @Override
        public int hashCode() {
            return (int)width ^ (int)height;
        }
    }
}
