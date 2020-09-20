package me.gavin.riverislandapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import me.gavin.riverislandapp.model.Product;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends Fragment {

    private static final String TAG = "ProductListFragment";
    private RecyclerView mProductRecyclerView;
    private List<Product> mItems = new ArrayList<>();

    private String productRequest;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public ProductListFragment() {
        // Required empty public constructor
    }


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
        if (getArguments() != null) {
           productRequest = getArguments().getString("tops");
           //Log.i(TAG, tops);
        }

        new ProductFetchTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product_list, container,false);

        mProductRecyclerView = v.findViewById(R.id.product_recycler_view);
        mProductRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        setupAdapter();



        return v;
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

        public ProductHolder(View itemView) {
            super(itemView);
            mProductTrendOrNew = itemView.findViewById(R.id.prod_trend_or_new_textview);
            mProductName = itemView.findViewById(R.id.product_name_textview);
            mProductCost = itemView.findViewById(R.id.product_cost_textview);
            mProdImgMain = itemView.findViewById(R.id.product_img_main);
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
                    //.centerCrop()
                    .into(productHolder.mProdImgMain);

            //TODO : this might not go here
            productHolder.mProdImgMain.setOnClickListener(v -> {
                NavDirections action = ProductListFragmentDirections.actionProductListFragmentToSingleProductFragment(product);
                Navigation.findNavController(v).navigate(action);
            });
        }
        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }

    private class ProductFetchTask extends AsyncTask<Void, Void, List<Product>> {
        @Override
        protected List<Product> doInBackground(Void... voids) {
            return new ProductFetch().fetchItems();
        }

        @Override
        protected void onPostExecute(List<Product> items) {
            mItems = items;
            setupAdapter();
        }
    }
}
