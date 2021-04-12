package com.iprokopyuk.shopcatalog.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;

import com.iprokopyuk.shopcatalog.R;
import com.iprokopyuk.shopcatalog.common.Constants;
import com.iprokopyuk.shopcatalog.databinding.ActivityCatalogBinding;
import com.google.android.material.navigation.NavigationView;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class CatalogActivity extends DaggerAppCompatActivity {

    @Inject
    CatalogPresenter catalogPresenter;

    private ActivityCatalogBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCatalogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.includedLayout.toolbar);

        binding.includedLayout.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.includedLayout.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                binding.drawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.nav_phone:
                        catalogPresenter.loadProducts(Constants.CATALOG_CATEGORY_PHONE);
                        break;
                    case R.id.nav_tv:
                        catalogPresenter.loadProducts(Constants.CATALOG_CATEGORY_TV);
                        break;
                    case R.id.nav_watch:
                        catalogPresenter.loadProducts(Constants.CATALOG_CATEGORY_WATCH);
                        break;
                    case R.id.nav_fridge:
                        catalogPresenter.loadProducts(Constants.CATALOG_CATEGORY_FRIDGE);
                        break;
                    case R.id.nav_furniture:
                        catalogPresenter.loadProducts(Constants.CATALOG_CATEGORY_FURNITURE);
                        break;
                }
                return true;
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
        super.onSupportNavigateUp();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}