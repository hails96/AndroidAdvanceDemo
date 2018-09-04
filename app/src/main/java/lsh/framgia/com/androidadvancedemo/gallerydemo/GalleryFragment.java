package lsh.framgia.com.androidadvancedemo.gallerydemo;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lsh.framgia.com.androidadvancedemo.R;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    private static final int PICK_PHOTO_REQUEST_CODE = 2009;

    private Button mButtonOpenGallery;
    private RecyclerView mRecyclerViewGallery;
    private GalleryAdapter mGalleryAdapter;
    private List<Bitmap> mBitmaps;

    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mBitmaps = new ArrayList<>();
        setupReferences(view);
        setupListeners();
        setupRecyclerView();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_REQUEST_CODE && resultCode == RESULT_OK) {
            new LoadImageAsyncTask().execute(data);
        }
    }

    private void setupReferences(View view) {
        mButtonOpenGallery = view.findViewById(R.id.button_open_gallery);
        mRecyclerViewGallery = view.findViewById(R.id.recycler_view_gallery);
    }

    private void setupListeners() {
        mButtonOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_PHOTO_REQUEST_CODE);
            }
        });
    }

    private void setupRecyclerView() {
        mRecyclerViewGallery.setHasFixedSize(true);
        mRecyclerViewGallery.setLayoutManager(new LinearLayoutManager(getActivity()));
        mGalleryAdapter = new GalleryAdapter(mBitmaps);
        mRecyclerViewGallery.setAdapter(mGalleryAdapter);
    }

    class LoadImageAsyncTask extends AsyncTask<Intent, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Intent... intents) {
            Bitmap resultBitmap = null;
            try {
                Intent data = intents[0];
                Uri imageUri = data.getData();
                InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                resultBitmap = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return resultBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mBitmaps.add(bitmap);
            mGalleryAdapter.notifyItemInserted(mBitmaps.size() - 1);
        }
    }
}
