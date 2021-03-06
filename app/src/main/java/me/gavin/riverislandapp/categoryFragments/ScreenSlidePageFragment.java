package me.gavin.riverislandapp.categoryFragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import me.gavin.riverislandapp.HomeFragmentDirections;
import me.gavin.riverislandapp.R;
import me.gavin.riverislandapp.model.Product;

public class ScreenSlidePageFragment extends Fragment {

    private static final String ARG_IMG_URL = "img_urls";
    private static final String ARG_PRODUCT = "product";

    private ImageView mImageView;
    private String url;
    private Product product;


    public static ScreenSlidePageFragment newInstance(String img) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_IMG_URL, img);
        fragment.setArguments(args);
        return fragment;
    }

    public static ScreenSlidePageFragment newInstance(Product product) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the urls list from SingleProductFragment use of newInstance
        if (getArguments() != null) {
            url = getArguments().getString(ARG_IMG_URL);
            product = (Product) getArguments().getSerializable(ARG_PRODUCT);
            Log.i("","");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screenslide_page, container, false);

        mImageView = rootView.findViewById(R.id.single_slide_page_img);
        ProgressBar progressBar = rootView.findViewById(R.id.progressBar);

        RequestListener requestListener = new RequestListener() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                return false;
            }
        };

        // if there is a url send in arg, get the url from the imageview from there, otherwise
        // get the image url from the product object. This situation comes from which newInstance
        // method has been used to construct the fragment for the viewpager.
        if (url != null) {
            Glide.with(getContext())
                    .load(url)
                    .skipMemoryCache(true)
                    .listener(requestListener)
                    //.fitCenter()
                    //.centerCrop()
                    .into(mImageView);
        } else {
            Glide.with(getContext())
                    .load(product.getAllImages().get(0))
                    .skipMemoryCache(true)
                    .listener(requestListener)
                    //.placeholder(R.drawable.loading)
                    .into(mImageView);
            mImageView.setOnClickListener(v -> {
                NavDirections action = HomeFragmentDirections.actionHomeFragmentToSingleProductFragment(product);
                Navigation.findNavController(v).navigate(action);
            });
        }


        return rootView;
    }

}
