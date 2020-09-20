package me.gavin.riverislandapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.gavin.riverislandapp.categoryFragments.ScreenSlidePageFragment;
import me.gavin.riverislandapp.model.Product;


public class SingleProductFragment extends Fragment {

    public static final String TAG = "SingleProductFragment";


    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;
    private Product product; // sent from productListFragment

    private TextView mTrendOrNewTextView;
    private TextView mNameTextView;
    private TextView mPriceTextView;

    public SingleProductFragment() {
        // Required empty public constructor
    }

    public static SingleProductFragment newInstance(String param1, String param2) {
        SingleProductFragment fragment = new SingleProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_single_product, container, false);

        // moving the argument recieving to here from onViewCreated solved the null pointer error
        // when trying to set the viewpager getCount() to size of url list.
        product = SingleProductFragmentArgs.fromBundle(getArguments()).getProduct();

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) v.findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(pagerAdapter);

        return v;
    }

    // callback to recieve the product object
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //product = SingleProductFragmentArgs.fromBundle(getArguments()).getProduct();

        // bind the rest the product data
        mTrendOrNewTextView = view.findViewById(R.id.single_prod_trend_or_new_textview);
        if (product.isTrending()) {
            mTrendOrNewTextView.setText("TRENDING");
        } else if (product.isNewArrival()) {
            mTrendOrNewTextView.setText("NEW ARRIVAL");
        }

        mNameTextView = view.findViewById(R.id.single_prod_name_textview);
        mNameTextView.setText(product.getName());

        mPriceTextView = view.findViewById(R.id.single_prod_price_textview);
        mPriceTextView.setText(product.getCost());

    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.newInstance(product.getAllImages().get(position));
        }

        @Override
        public int getCount() {
            return product.getAllImages().size();
        }
    }
}
