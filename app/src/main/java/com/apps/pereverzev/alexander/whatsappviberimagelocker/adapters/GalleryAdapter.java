package com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.GalleryRow;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.adapters.components.Image;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.holders.GalleryAdapterHolder;
import com.apps.pereverzev.alexander.whatsappviberimagelocker.utils.MyImageLoader;

import java.util.List;

/**
 * Created by opereverzyev on 11.02.15.
 */
public class GalleryAdapter extends BaseAdapter {
    public final static int MARGIN_IMAGE = 4;
    private List<GalleryRow> _galleryRows;
    private Activity _context;
    private MyImageLoader imageLoader;

    public GalleryAdapter(List<GalleryRow> galleryRows, Activity context) {
        this._galleryRows = galleryRows;
        this._context = context;
        this.imageLoader = MyImageLoader.getInstance(context);
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

        return convertView;
    }

    private View getConvertView(int position) {
        LinearLayout result = new LinearLayout(getContext());
        result.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
        image.setOnClickListener(imageOnClickListener(String.valueOf(position) + String.valueOf(i)));
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

    private View.OnClickListener imageOnClickListener(final String positionOfImage) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "image position number: " + positionOfImage, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void setImageBitmap(GalleryAdapterHolder holder, GalleryRow row, int i) {
        Image image = row.getImage(i);
        imageLoader.setImage(holder.images.get(i),(int)Math.ceil(image.getIconSize().width),
                (int)Math.ceil(image.getIconSize().height), image.getImagePath(), null);
    }
}
