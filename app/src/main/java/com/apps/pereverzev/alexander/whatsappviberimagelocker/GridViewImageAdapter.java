package com.apps.pereverzev.alexander.whatsappviberimagelocker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.NonViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Александр on 02.11.2014.
 */
public class GridViewImageAdapter extends BaseAdapter {
    private GridViewActivity gridViewActivity;
    private Activity _activity;
    private static ArrayList<String> _filePaths = new ArrayList<String>();
    private int imageWidth;
    private Drawable imageId;

    ImageLoaderConfiguration config ;

    ImageLoader imageLoader;


    DisplayImageOptions options;

    public GridViewImageAdapter(GridViewActivity grid, Activity activity,
                                int imageWidth) {
        this.gridViewActivity = grid;
        this._activity = activity;
        this.imageWidth = imageWidth;
        clear();
        imageId = _activity.getResources().getDrawable(R.drawable.load);

        config = new ImageLoaderConfiguration.Builder(_activity.getApplicationContext())
                .threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();


        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);


        DownloadImagePathes downloadImagePathes = new DownloadImagePathes();
        downloadImagePathes.execute();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void setPath(String path){
        _filePaths.add(path);
    }

    public static synchronized String getPath(int position){
        return  _filePaths.get(position);
    }

  /*  public static synchronized void changePath(int position, String value){
        _filePaths.remove(position);
        _filePaths.add(position, value);
    }*/

    public static synchronized void clear(){
        _filePaths = new ArrayList<String>();
    }

    public static synchronized int getFilesSize(){
        return _filePaths.size();
    }

    @Override
    public int getCount() {
        return getFilesSize();
    }

    @Override
    public Object getItem(int position) {
        return this.getPath(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View grid;
        ImageView imageView = null;
        LayoutInflater inflater = (LayoutInflater) _activity.getApplicationContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            grid = new View(_activity.getApplicationContext());
            grid = inflater.inflate(R.layout.grid_single, null);

        } else {
            grid = (View) convertView;
        }

        imageView = (ImageView)grid.findViewById(R.id.grid_image);

        imageView.setMaxWidth(50);
        imageView.setMaxHeight(50);

        imageView.getLayoutParams().height = imageWidth;
        imageView.getLayoutParams().width = imageWidth;

        imageView.requestLayout();

        grid.setLayoutParams(new GridView.LayoutParams(imageWidth,
                imageWidth));


       /*
        if (convertView == null) {
            imageView = new ImageView(_activity);
        } else {
            imageView = (ImageView) convertView;
        }

       imageView.setMaxWidth(50);
       imageView.setMaxHeight(50);
       imageView.setClickable(true);
       */

        // get screen dimensions

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
     /*   imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
                imageWidth));*/


/*        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inTempStorage = new byte[16*1024];

        Bitmap bitmap = BitmapFactory.decodeFile(getPath(position), options2);
        imageView.setImageBitmap(bitmap);*/



        if(getPath(position).substring(getPath(position).length() - 3, getPath(position).length()).equals("evg")){
            options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(new RoundedBitmapDisplayer(1000))
                    .showImageOnLoading(imageId)
                    .build();

            imageView.getLayoutParams().height = imageWidth-16;
            imageView.getLayoutParams().width = imageWidth-16;
        } else {
            options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .showImageOnLoading(imageId)
                    .build();
        }

        imageLoader.displayImage("file:///" + getPath(position), imageView, options);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GridViewActivity.onRestart = true;
                // on selecting grid view image
                // launch full screen activity
                Intent i = new Intent(_activity, FullScreenViewActivity.class);
                i.putExtra("position", position);
                _activity.startActivity(i);
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String path = getPath(position);
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(_activity);

                DialogInterface.OnClickListener okButtonListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPath = "";
                        if(getPath(position).substring(getPath(position).length() - 3, getPath(position).length()).equals("evg")) {
                            File file = new File(getPath(position));
                            newPath = getPath(position).substring(0, getPath(position).length() - 3);
                            File file2 = new File(newPath);
                            file.renameTo(file2);

                        } else {
                            File file = new File(getPath(position));
                            newPath = getPath(position) + "evg";
                            File file2 = new File(newPath);
                            file.renameTo(file2);
                        }

                        //  changePath(_postion, newPath);

                        clear();
                        //       Utils.getFilesFromViberWhatsApp();

                        gridViewActivity.setAdapter();
                        //  new SingleMediaScanner(_activity.getApplicationContext(), fileMain);

                        //_activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                    }
                };

                String message = "";
                if(path.substring(path.length()-3, path.length()).equals("evg")){
                    message = "Открыть доступ к изображению?" ;
                } else {
                    message = "Скрыть изображение?";
                }

                alertBuilder
                        .setTitle("Скрытие")
                        .setMessage(message)
                        .setPositiveButton("Да", okButtonListener)
                        .setNegativeButton("Нет", null)
                        .show();

                return true;
            }
        });

        return grid;
    }




    private class DownloadImagePathes extends AsyncTask{
        @Override
        protected Object doInBackground(Object[] params) {
            Utils.getFilesFromViberWhatsApp();
            return null;
        }
    }

    class OnImageClickListener implements View.OnClickListener {

        int _postion;

        // constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            GridViewActivity.onRestart = true;
            // on selecting grid view image
            // launch full screen activity
            Intent i = new Intent(_activity, FullScreenViewActivity.class);
            i.putExtra("position", _postion);
            _activity.startActivity(i);
        }
    }

    class OnImageLongClickListener implements View.OnLongClickListener {

        int _postion;

        // constructor
        public OnImageLongClickListener(int position) {
            this._postion = position;
        }

        @Override
        public boolean onLongClick(View v) {
            String path = getPath(_postion);
            final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(_activity);

            DialogInterface.OnClickListener okButtonListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newPath = "";
                    if(getPath(_postion).substring(getPath(_postion).length() - 3, getPath(_postion).length()).equals("evg")) {
                        File file = new File(getPath(_postion));
                        newPath = getPath(_postion).substring(0, getPath(_postion).length() - 3);
                        File file2 = new File(newPath);
                        file.renameTo(file2);

                    } else {
                        File file = new File(getPath(_postion));
                        newPath = getPath(_postion) + "evg";
                        File file2 = new File(newPath);
                        file.renameTo(file2);
                    }

                    //  changePath(_postion, newPath);

                    clear();
                    //       Utils.getFilesFromViberWhatsApp();

                    gridViewActivity.setAdapter();
                    //  new SingleMediaScanner(_activity.getApplicationContext(), fileMain);

                    //_activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                }
            };

            String message = "";
            if(path.substring(path.length()-3, path.length()).equals("evg")){
                message = "Открыть доступ к изображению?" ;
            } else {
                message = "Скрыть изображение?";
            }

            alertBuilder
                    .setTitle("Скрытие")
                    .setMessage(message)
                    .setPositiveButton("Да", okButtonListener)
                    .setNegativeButton("Нет", null)
                    .show();

            return true;
        }

    }
}