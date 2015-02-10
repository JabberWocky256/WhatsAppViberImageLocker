package com.apps.pereverzev.alexander.whatsappviberimagelocker.oldproject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


import com.apps.pereverzev.alexander.whatsappviberimagelocker.MainActivity;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.R;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.oldproject.constants.AppConstant;

import java.util.ArrayList;

/**
 * Created by Александр on 02.11.2014.
 */
public class GridViewActivity extends Activity {

    private Utils utils;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private GridViewImageAdapter adapter;
    private GridView gridView;
    private int columnWidth;
    public static boolean onRestart = false;

    private static final int REQUEST = 1;
    private String imagePath = "";
    private Image image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gridView = (GridView) findViewById(R.id.grid_view);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int a =10;
                a++;
            }
        });

        utils = new Utils();

        // Initilizing Grid View
        InitilizeGridLayout();

        setAdapter();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add ("Change password");
        item.setOnMenuItemClickListener (new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick (MenuItem item){
                changePass();
                return true;
            }
        });
        return true;
    }

    private void changePass(){
        Intent registr = new Intent(GridViewActivity.this, Registr.class);
        startActivity(registr);
    }

    public void setAdapter(){
        // loading all image paths from SD card


        // Gridview adapter
        adapter = new GridViewImageAdapter(this, GridViewActivity.this,
                columnWidth);
        // setting grid view adapter
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        if(onRestart == false)
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        onRestart = false;
        super.onPause();
    }


    @Override
    protected void onRestart() {
        if(onRestart = false) {
            Intent intent = new Intent(GridViewActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            onRestart = true;
        }

        super.onRestart();
    }

    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConstant.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((utils.getScreenWidth(getApplicationContext()) - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);

        gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            imagePath = getRealPathFromURI(selectedImage);

            image = new Image(imagePath);
            image.saveImage();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getRealPathFromURI(Uri contentUri)
    {
        String[] proj = { MediaStore.Video.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
