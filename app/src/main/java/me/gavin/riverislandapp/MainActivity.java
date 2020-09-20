package me.gavin.riverislandapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.productListFragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);


        if (navController.getCurrentDestination().getId() == R.id.homeFragment) {
            navigationView.setCheckedItem(R.id.homeFragment);
        }
        // Custom override for passing arguments to productListFragment
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i("Nav Item", item.toString());
                int id = item.getItemId();
                if (id == R.id.homeFragment) {
                    navController.navigate(R.id.homeFragment);
                    drawer.close();
                }

                else if (item.getTitle().equals("All Products")) {
                    if (navController.getCurrentDestination().getId() != R.id.productListFragment) {
                        NavDirections action = HomeFragmentDirections.actionHomeFragmentToProductListFragment();
                        navController.navigate(action);
                        drawer.close();
                    } else {
                        navigationView.setCheckedItem(item);
                        drawer.close();
                    }
                }

                else if (item.getTitle().equals("Tops")) {
                    if (navController.getCurrentDestination().getId() != R.id.productListFragment) {
                        NavDirections action = HomeFragmentDirections.actionHomeFragmentToProductListFragment();
                        navController.navigate(action);
                        navigationView.setCheckedItem(item);
                        drawer.close();
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("tops", "tops");
                        navController.navigate(R.id.productListFragment, bundle);
                        navigationView.setCheckedItem(item);
                        drawer.close();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
