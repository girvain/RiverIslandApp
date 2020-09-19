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

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import me.gavin.riverislandapp.model.Product;


public class HomeFragment extends Fragment {

    private ViewPager mPagerNewArivals;
    private ViewPager mPagerFaceMasks;

    private PagerAdapter mPagerNewArrAdapter;
    private PagerAdapter mPagerFaceMskAdapter;

    private List<Product> products;
    private List<Product> newProducts;
    private List<Product> maskProducts;

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

        mPagerNewArivals = (ViewPager) v.findViewById(R.id.pager_new_arrivals_home_fragment);
        mPagerFaceMasks = (ViewPager) v.findViewById(R.id.pager_face_mask_home_fragment);

        return v;
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
            newProducts = items.subList(0, 8);
            maskProducts = mProductFetch.filterByName(items,"face covering");
            Log.i("fsd", "ffsadf");

            // configure the adapters after data has arrived from get request
            mPagerNewArrAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), newProducts);
            mPagerNewArivals.setAdapter(mPagerNewArrAdapter);

            mPagerFaceMskAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(), maskProducts);
            mPagerFaceMasks.setAdapter(mPagerFaceMskAdapter);
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
            return ScreenSlidePageFragment.newInstance(urls.get(position));
        }

        @Override
        public int getCount() {
            return urls.size();
        }
    }

}
