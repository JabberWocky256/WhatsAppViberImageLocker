package com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.R;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.activities.FullScreenViewActivity;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.GalleryRow;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.Image;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.holders.GalleryAdapterHolder;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.utils.MyImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by opereverzyev on 11.02.15.
 */
public class GalleryAdapter extends BaseAdapter {
    public final static int MARGIN_IMAGE = 4;
    private List<GalleryRow> _galleryRows;
    private Activity _context;
    private MyImageLoader imageLoader;
    public static List<Image> imagesInGallery;

    public GalleryAdapter(List<GalleryRow> galleryRows, Activity context) {
        this._galleryRows = galleryRows;
        this._context = context;
        this.imageLoader = MyImageLoader.getInstance(context);
    }

    public int getPositionOfImageInList(int rowPosition, int imagePosition){
        int currentPosition = 0;
        if(imagesInGallery != null) {
            Image tapImage = ((GalleryRow)getItem(rowPosition)).getImage(imagePosition);
            for(int i = 0; i<imagesInGallery.size(); i++){
                if(imagesInGallery.get(i).equals(tapImage)){
                    currentPosition = i;
                    return currentPosition;
                }
            }
        }

        ListIterator<GalleryRow> rows = _galleryRows.listIterator();
        imagesInGallery = new ArrayList<>();

        while(rows.hasNext()){
            int currentIndex = rows.nextIndex();
            GalleryRow row = rows.next();

            for(int i =0; i<row.getCount(); i++){
                Image image = row.getImage(i);
                imagesInGallery.add(image);
                if(rowPosition == currentIndex && imagePosition == i){
                    return currentPosition;
                }

                currentPosition++;
            }
        }

        return currentPosition;
    }

    public Activity getContext() {
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
        GalleryAdapterHolder holder;
        GalleryRow row = (GalleryRow) getItem(position);
        int imagesCount = row.getCount();

        if (convertView == null) {
            convertView = getConvertView(position);
            holder = new GalleryAdapterHolder();

            for (int i = 0; i < imagesCount; i++) {
                ImageView image = initImageView(position, holder, row, i);
                setImageBitmap(holder, row, i);
                ((LinearLayout) convertView).addView(image);
            }

            convertView.setTag(holder);
        } else {
            holder = (GalleryAdapterHolder) convertView.getTag();
            //for every numbers of image must be own holder or need to recreate new getView with null convertView
            if (holder.images.size() != imagesCount)
                return getView(position, null, parent);

            for (int i = 0; i < imagesCount; i++) {
                setImageBitmap(holder, row, i);
            }
        }

        for (int i = 0; i < imagesCount; i++) {
            holder.images.get(i).setOnClickListener(imageOnClickListener(position, i));
        }

        return convertView;
    }

    private View getConvertView(int position) {
        LinearLayout result = new LinearLayout(getContext());
        result.setOrientation(LinearLayout.HORIZONTAL);
        AbsListView.LayoutParams llParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        result.setLayoutParams(llParams);

        int bottomPadding = getBottomPadding(position);
        result.setPadding(GalleryAdapter.MARGIN_IMAGE, GalleryAdapter.MARGIN_IMAGE * 2, GalleryAdapter.MARGIN_IMAGE * 2, bottomPadding);

        return result;
    }

    //we need top padding only for upper view
    private int getBottomPadding(int position) {
        int result = 0;
        if (getCount() - 1 == position)
            result = GalleryAdapter.MARGIN_IMAGE * 2;

        return result;
    }

    private ImageView initImageView(int position, GalleryAdapterHolder holder, GalleryRow row, int i) {
        ImageView image = addImageView(row.getImage(i));
        holder.images.add(image);
        return image;
    }

    private ImageView addImageView(Image image) {
        ImageView mImage = new ImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) image.getIconSize().width - MARGIN_IMAGE, (int) image.getIconSize().height - MARGIN_IMAGE);
        params.setMargins(MARGIN_IMAGE, 0, MARGIN_IMAGE, 0);
        mImage.setLayoutParams(params);
        mImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return mImage;
    }

    private View.OnClickListener imageOnClickListener(final int rowPosition, final int columnPosition) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(_context, FullScreenViewActivity.class);
                i.putExtra("position", getPositionOfImageInList(rowPosition, columnPosition));
                _context.startActivity(i);
            }
        };
    }

    private void setImageBitmap(GalleryAdapterHolder holder, GalleryRow row, int i) {
        Image image = row.getImage(i);
        imageLoader.setImage(holder.images.get(i), (int) Math.ceil(image.getIconSize().width),
                (int) Math.ceil(image.getIconSize().height), image.getImagePath(), null);
    }
}
