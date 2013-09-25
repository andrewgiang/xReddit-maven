package com.andrew.giang.xreddit.fragments;

/**
 * Created by andrew on 9/17/13.
 */


import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.andrew.giang.xreddit.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageFragment extends Fragment {

    static final String PHOTO_TAP_TOAST_STRING = "Photo Tap! X: %.2f %% Y:%.2f %%";
    private ImageView mImageView;
    private TextView mCurrMatrixTv;
    private PhotoViewAttacher mAttacher;
    private Toast mCurrentToast;
    private RectF mAttacherDisplayRect;
    private ActionBarActivity activity;

    public ImageFragment() {

    }

    public static ImageFragment getInstance(String url) {
        Bundle bundle = new Bundle();
        ImageFragment fragment = new ImageFragment();
        bundle.putString("image_url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = (ActionBarActivity) ImageFragment.this.getActivity();
        final FrameLayout root = (FrameLayout) inflater.inflate(R.layout.fragment_image_layout, container, false);

        final Bundle arguments = getArguments();
        if (arguments != null) {
            final String image_url = arguments.getString("image_url");

            mImageView = (ImageView) root.findViewById(R.id.iv_photo);
            mCurrMatrixTv = new TextView(getActivity());


            // The MAGIC happens here!
            mAttacher = new PhotoViewAttacher(mImageView);
            mAttacher.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


            Picasso.with(getActivity()).load(image_url).noFade().into(mImageView, new Callback() {
                @Override
                public void onSuccess() {
                    mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    root.findViewById(R.id.progress).setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });
            // Lets attach some listeners, not required though!
            mAttacher.setOnMatrixChangeListener(new MatrixChangeListener());
            mAttacher.setOnPhotoTapListener(new PhotoTapListener());

        }
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Need to call clean-up
        mAttacher.cleanup();
    }

    private class PhotoTapListener implements PhotoViewAttacher.OnPhotoTapListener {

        @Override
        public void onPhotoTap(View view, float x, float y) {

            float xPercentage = x * 100f;
            float yPercentage = y * 100f;

            if (null != mCurrentToast) {
                mCurrentToast.cancel();
            }

            mCurrentToast = Toast.makeText(getActivity(),
                    String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage), Toast.LENGTH_SHORT);
            mCurrentToast.show();
        }
    }

    private class MatrixChangeListener implements PhotoViewAttacher.OnMatrixChangedListener {

        @Override
        public void onMatrixChanged(RectF rect) {
        }
    }

}