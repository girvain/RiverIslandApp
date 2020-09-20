package me.gavin.riverislandapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import me.gavin.riverislandapp.model.Product;


public class HomeFragment extends Fragment {
    public static final String SCROLL_POS = "scroll_pos";

    private ScrollView mScrollView; // the root view, id needed to get scroll position
    private ViewPager mPagerNewArivals;
    private ViewPager mPagerFaceMasks;
    private ViewPager mPagerJeans;

    private PagerAdapter mPagerNewArrAdapter;
    private PagerAdapter mPagerFaceMskAdapter;
    private PagerAdapter mPagerJeansAdapter;

    private List<Product> products;
    private List<Product> newProducts;
    private List<Product> maskProducts;
    private List<Product> jeans;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the full list of products
        new ProductFetchTask().execute();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mScrollView = v.findViewById(R.id.home_scrollview);

        mPagerNewArivals = (ViewPager) v.findViewById(R.id.pager_new_arrivals_home_fragment);
        mPagerFaceMasks = (ViewPager) v.findViewById(R.id.pager_face_mask_home_fragment);
        mPagerJeans = (ViewPager) v.findViewById(R.id.pager_jeans_home_fragment);

        // restore the previous page position
        if (savedInstanceState != null) {
            int scrollPos = savedInstanceState.getInt(SCROLL_POS);
            mScrollView.setVerticalScrollbarPosition(scrollPos);
        }

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SCROLL_POS, mScrollView.getVerticalScrollbarPosition());
    }


    /**
     * The adapters need recreated and set on the viewpagers for screen rotation and back button
     * presses
     */
    @Override
    public void onResume() {
        super.onResume();
        if (maskProducts != null) {
            mPagerNewArrAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), newProducts);
            mPagerNewArivals.setAdapter(mPagerNewArrAdapter);

            mPagerFaceMskAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), maskProducts);
            mPagerFaceMasks.setAdapter(mPagerFaceMskAdapter);

            mPagerJeansAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), jeans);
            mPagerJeans.setAdapter(mPagerJeansAdapter);

        }

    }


    private class ProductFetchTask extends AsyncTask<Void, Void, List<Product>> {
        ProductFetch mProductFetch;

        @Override
        protected List<Product> doInBackground(Void... voids) {
            mProductFetch = new ProductFetch();
            return mProductFetch.fetchItems();
        }

        @Override
        protected void onPostExecute(List<Product> items) {
            products = items;
            newProducts = products.subList(0, 8);
            maskProducts = mProductFetch.filterByName(items,"face covering");
            jeans = mProductFetch.filterByName(items, "jeans");
            Log.i("fsd", "ffsadf");

            // configure the adapters after data has arrived from get request
            mPagerNewArrAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), newProducts);
            mPagerNewArivals.setAdapter(mPagerNewArrAdapter);

            mPagerFaceMskAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), maskProducts);
            mPagerFaceMasks.setAdapter(mPagerFaceMskAdapter);

            mPagerJeansAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), jeans);
            mPagerJeans.setAdapter(mPagerJeansAdapter);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        List<Product> mProductList;
        List<String> urls;

        // Extract the Urls from each product in the productList being passed into the viewPager
        public ScreenSlidePagerAdapter(FragmentManager fm, List<Product> productList) {
            super(fm);
            //TODO: clean this up
            this.mProductList = productList;
            urls = new ArrayList<>();
            for (Product p : mProductList) {
                urls.add(p.getAllImages().get(0));
            }
        }


        @Override
        public Fragment getItem(int position) {
            //return ScreenSlidePageFragment.newInstance(urls.get(position));
            return ScreenSlidePageFragment.newInstance(mProductList.get(position));
        }

        @Override
        public int getCount() {
            return mProductList.size();
        }
    }

}
