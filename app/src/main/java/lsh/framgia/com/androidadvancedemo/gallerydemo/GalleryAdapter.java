package lsh.framgia.com.androidadvancedemo.gallerydemo;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import lsh.framgia.com.androidadvancedemo.R;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<Bitmap> mBitmaps;

    public GalleryAdapter(List<Bitmap> bitmaps) {
        this.mBitmaps = bitmaps;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new GalleryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        holder.bindData(mBitmaps.get(position));
    }

    @Override
    public int getItemCount() {
        return mBitmaps == null ? 0 : mBitmaps.size();
    }

    class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageGallery;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            mImageGallery = itemView.findViewById(R.id.image_gallery);
        }

        void bindData(Bitmap bitmap) {
            mImageGallery.setImageBitmap(bitmap);
        }
    }
}
