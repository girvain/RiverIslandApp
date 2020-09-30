package me.gavin.riverislandapp;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import me.gavin.riverislandapp.categoryFragments.ProductListFragment;
import me.gavin.riverislandapp.model.Product;


public abstract class AbstractProductListFragment extends Fragment {

    private static final String TAG = "ProductListFragment";
    private RecyclerView mProductRecyclerView;
    private List<Product> mItems = new ArrayList<>();
    private ProgressBar mProgressBar;
    private TextView mNotFound;
    private int currentPos;


    /**
     * This method is to specify what product list to get from the server. It is
     * used with the async task within the class and must return a List<Product>
     * @return
     */
    public abstract List<Product> getProducts();

    /**
     * This method is to specify what fragment to navigate to when an ImageView in the
     * RecyclerView list is clicked, along with passing the Product arg into the
     * navigation process.
     * @param v
     * @param product
     */
    public abstract void navigationImp(View v, Product product);



    public static ProductListFragment newInstance(String param1, String param2) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new ProductFetchTask().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product_list, container, false);

        mProductRecyclerView = v.findViewById(R.id.product_recycler_view);
        mProductRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mProgressBar = v.findViewById(R.id.progressBarRecycler);
        mNotFound = v.findViewById(R.id.not_found);

        new ProductFetchTask().execute();

        setupAdapter();



        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putInt(mProductRecyclerView.getVerticalScrollbarPosition());
    }

    @Override
    public void onResume() {
        super.onResume();
        mProductRecyclerView.scrollToPosition(currentPos); // not working!!!!!!!
        mProductRecyclerView.smoothScrollToPosition(10);
    }

    private void setupAdapter() {
        if (isAdded()) {
            mProductRecyclerView.setAdapter(new ProductAdapter(mItems));
        }
    }


    private class ProductHolder extends RecyclerView.ViewHolder {
        private TextView mProductTrendOrNew;
        private TextView mProductName;
        private ImageView mProdImgMain;
        private TextView mProductCost;
        private ProgressBar mProgressBar;

        public ProductHolder(View itemView) {
            super(itemView);
            mProductTrendOrNew = itemView.findViewById(R.id.prod_trend_or_new_textview);
            mProductName = itemView.findViewById(R.id.product_name_textview);
            mProductCost = itemView.findViewById(R.id.product_cost_textview);
            mProdImgMain = itemView.findViewById(R.id.product_img_main);
            mProgressBar = itemView.findViewById(R.id.progressBarList);
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
        private List<Product> mProducts;

        public ProductAdapter(List<Product> products) {
            mProducts = products;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_product_item, viewGroup, false);

            return new ProductHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductHolder productHolder, final int position) {
            final Product product = mProducts.get(position);
            if (product.isTrending()) {
                productHolder.mProductTrendOrNew.setText("TRENDING");
            } else if (product.isNewArrival()) {
                productHolder.mProductTrendOrNew.setText("NEW ARRIVAL");
            }
            productHolder.mProductName.setText(product.getName());
            productHolder.mProductCost.setText("£" + product.getCost());


            Glide.with(getContext())
                    .load(product.getAllImages().get(0))
                    .skipMemoryCache(true)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            productHolder.mProgressBar.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    //.centerCrop()
                    .into(productHolder.mProdImgMain);

            //TODO : this might not go here
            productHolder.mProdImgMain.setOnClickListener(v -> {
                // ---------------------------------- Fix -------------------------------------- //
                currentPos = productHolder.getAdapterPosition(); // set the current position before leaving
                navigationImp(v, product);
            });

        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }

    private class ProductFetchTask extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Product> doInBackground(Void... voids) {
            return getProducts();
        }

        @Override
        protected void onPostExecute(List<Product> items) {
            mItems = items;
            setupAdapter();
            mProgressBar.setVisibility(View.INVISIBLE);

            if (items.size() == 0) {
                mNotFound.setVisibility(View.VISIBLE);
            }
        }


    }
}
