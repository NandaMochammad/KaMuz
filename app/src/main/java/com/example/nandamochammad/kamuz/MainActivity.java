package com.example.nandamochammad.kamuz;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    String translate = "true";

    DrawerLayout drawer_layout;
    private boolean isEnglish = true;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer_layout = findViewById(R.id.drawerr_layout);
        toolbar = findViewById(R.id.toolbar);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(MainActivity.this);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment, new BlankFragment());
        fragmentTransaction.commit();


        loadData();
        nav_view.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_english_indonesia:
                translate = "true";
                isEnglish = true;
                fragmentTransaction.replace(R.id.container_fragment, new BlankFragment());
                Log.d("MASUK", "onNavigationItemSelected: isEnglish Bernilai "+translate);
                loadData();
                break;
            case R.id.nav_indonesia_english:
                translate = "false";
                isEnglish = false;
                fragmentTransaction.replace(R.id.container_fragment, new BlankFragment());
                Log.d("MASUK", "onNavigationItemSelected: isEnglish Bernilai "+translate);
                loadData();
                break;
            case R.id.nav_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_name) + "\n\n" + getString(R.string.share_description));
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.share)));
                break;
        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadData() {
        try {

            if (isEnglish==true) {
                toolbar.setSubtitle("English - Indonesia");
            } else {
                toolbar.setSubtitle("Indonesia - English");
            }
        } catch (Exception e) {
            Log.d("masuk", "loadData: "+e);
        }
    }

}
