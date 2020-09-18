package me.gavin.riverislandapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ScreenSlidePageFragment extends Fragment {

    private static final String ARG_IMG_URL = "img_urls";

    private ImageView mImageView;
    private String url;


    public static ScreenSlidePageFragment newInstance(String img) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMG_URL, img);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the urls list from SingleProductFragment use of newInstance
        if (getArguments() != null) {
            url = getArguments().getString(ARG_IMG_URL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screenslide_page, container, false);

        mImageView = rootView.findViewById(R.id.single_slide_page_img);
        Glide.with(getContext())
                .load(url)
                //.centerCrop()
                .into(mImageView);

        return rootView;
    }
}
