package me.gavin.riverislandapp.categoryFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.gavin.riverislandapp.AbstractProductListFragment;
import me.gavin.riverislandapp.ProductFetch;
import me.gavin.riverislandapp.R;
import me.gavin.riverislandapp.model.Product;

public class NightAndSlippFragment extends AbstractProductListFragment {

    @Override
    public List<Product> getProducts() {
        return new ProductFetch().fetchAndFilterByCategory("Nightwear & Slippers");
    }

    @Override
    public void navigationImp(View v, Product p) {
       NavDirections action = NightAndSlippFragmentDirections.actionNightAndSlippFragmentToSingleProductFragment(p);
       Navigation.findNavController(v).navigate(action);
    }

}