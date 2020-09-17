package me.gavin.riverislandapp;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

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
        private TextView mProductName;
        private ImageView mProdImgMain;
        private TextView mProductCost;

        public ProductHolder(View itemView) {
            super(itemView);
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
        public void onBindViewHolder(ProductHolder productHolder, int position) {
            Product product = mProducts.get(position);
            productHolder.mProductName.setText(product.getName());
            productHolder.mProductCost.setText("£ " + product.getCost());
            //Drawable placeholder = getResources().getDrawable(R.drawable.pic);
            //photoHolder.bindDrawable(placeholder);

            Glide.with(getContext())
                    .load(product.getAllImages().get(0))
                    //.centerCrop()
                    .into(productHolder.mProdImgMain);
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
