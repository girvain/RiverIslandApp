package me.gavin.riverislandapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.gavin.riverislandapp.model.Product;

public class SearchResultFragment extends AbstractProductListFragment {

    private String query;

    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public List<Product> getProducts() {
        return new ProductFetch().fetchAndFilterByName(query);
    }

    @Override
    public void navigationImp(View v, Product product) {
        NavDirections action = SearchResultFragmentDirections.actionSearchResultFragmentToSingleProductFragment(product);
        Navigation.findNavController(v).navigate(action);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            query = getArguments().getString(MainActivity.ARG_SEARCH_QRY);
            Log.i("query", query);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_search_result, container, false);
//    }
}