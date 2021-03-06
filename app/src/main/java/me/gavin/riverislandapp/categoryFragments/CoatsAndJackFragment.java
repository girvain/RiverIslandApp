package me.gavin.riverislandapp.categoryFragments;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.View;

import java.util.List;

import me.gavin.riverislandapp.AbstractProductListFragment;
import me.gavin.riverislandapp.ProductFetch;
import me.gavin.riverislandapp.model.Product;


public class CoatsAndJackFragment extends AbstractProductListFragment {

    @Override
    public List<Product> getProducts() {
        return new ProductFetch().fetchAndFilterByCategory("Coats & Jackets");
    }

    @Override
    public void navigationImp(View v, Product p) {
        NavDirections action = CoatsAndJackFragmentDirections.actionCoatsAndJackFragmentToSingleProductFragment(p);
        Navigation.findNavController(v).navigate(action);
    }

}