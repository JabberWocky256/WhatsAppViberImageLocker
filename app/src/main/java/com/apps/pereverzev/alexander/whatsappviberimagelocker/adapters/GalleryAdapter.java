package com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.GalleryRow;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.Image;

import java.util.List;

/**
 * Created by opereverzyev on 11.02.15.
 */
public class GalleryAdapter extends BaseAdapter {
    private List<GalleryRow> _galleryRows;
    private Activity _context;

    public GalleryAdapter(List<GalleryRow> galleryRows, Activity context) {
        this._galleryRows = galleryRows;
        this._context = context;
    }

    public Activity getContext(){
        return _context;
    }

    @Override
    public int getCount() {
       return _galleryRows.size();
    }

    @Override
    public Object getItem(int position) {
        return _galleryRows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = getConvertView();
        }

        GalleryRow row = (GalleryRow)getItem(position);
        int length = row.getCount();
        for(int i = 0; i<length; i++){
            addImageView(row.getImage(i), (LinearLayout)convertView);
        }

        return convertView;
    }

    private View getConvertView() {
        LinearLayout result = new LinearLayout(getContext());
        result.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        result.setLayoutParams(LLParams);

        return result;
    }

    private void addImageView(Image image, LinearLayout convertView){
        ImageView mImage = new ImageView(getContext());
        mImage.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mImage.setPadding(10,10,10,10);
        mImage.setImageBitmap(image.getImageBitmap());

        convertView.addView(mImage);
    }
}
