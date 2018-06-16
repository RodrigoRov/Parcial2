package com.rodrigorov.cometela.parcial2.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rodrigorov.cometela.parcial2.Adapters.ExpandableListAdapter;
import com.rodrigorov.cometela.parcial2.Fragments.FavoritosFragment;
import com.rodrigorov.cometela.parcial2.Fragments.GeneralNewsFragment;
import com.rodrigorov.cometela.parcial2.Fragments.GamesViewPagerFragment;
import com.rodrigorov.cometela.parcial2.Fragments.SettingsFragment;
import com.rodrigorov.cometela.parcial2.Models.ExpandedMenuModel;
import com.rodrigorov.cometela.parcial2.Models.User;
import com.rodrigorov.cometela.parcial2.R;
import com.rodrigorov.cometela.parcial2.ViewModel.NoticiaViewModel;
import com.rodrigorov.cometela.parcial2.ViewModel.PlayerViewModel;
import com.rodrigorov.cometela.parcial2.ViewModel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String token;
    UserViewModel userViewModel;
    NoticiaViewModel noticiaViewModel;
    PlayerViewModel playerViewModel;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    AppCompatActivity appCompatActivity = this;
    String [] categorias;
    Boolean change_fragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noticiaViewModel = ViewModelProviders.of(this).get(NoticiaViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        token = sharedPref.getString("TOKEN","");
        if(token.equals("")){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,1);
        }
        playerViewModel.getAllPlayers(token);
        expandableList = findViewById(R.id.navigation_explandable_view);
        prepareListData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        final GeneralNewsFragment generalNewsFragment = new GeneralNewsFragment();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
        GeneralNewsFragment fragment = new GeneralNewsFragment();
        fragment.setFiltro(0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame,fragment);
        fragmentTransaction.commit();

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                //Log.d("DEBUG", "submenu item clicked");
                if(i1 == 0){
                    setGamesViewFragment(1);
                }else if(i1 == 1){
                    setGamesViewFragment(2);
                }else if(i1 == 2){
                    setGamesViewFragment(3);
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                //Log.d("DEBUG", "heading clicked");
                if (i == 0) {
                    GeneralNewsFragment fragment = new GeneralNewsFragment();
                    fragment.setFiltro(0);
                    fragment.setCate(categorias);
                    setFragment(fragment);
                }else if (i == 2) {
                    setFragment(new SettingsFragment());
                } else if (i == 3) {
                    setFragment(new FavoritosFragment());
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            noticiaViewModel.deleteAll();
            userViewModel.deleteAll();
            playerViewModel.deleteAll();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivityForResult(intent,1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame,fragment);
        fragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    void setGamesViewFragment(int filtro){
        GamesViewPagerFragment fragment = new GamesViewPagerFragment();
        fragment.setCate(categorias);
        fragment.setFiltro(filtro);
        setFragment(fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode ==1){
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPref.edit();
            token = data.getStringExtra("token");
            userViewModel.getUserDetail(token).observe(this, new Observer<User>() {
                @Override
                public void onChanged(@Nullable User user) {
                    editor.putString("UserId",user.getId());
                    editor.putString("Favoritos",user.getFavoriteNews());
                    editor.commit();
                }
            });
            playerViewModel.getAllPlayers(token);
            prepareListData();
            change_fragment = true;
            editor.putString("TOKEN",token);
            editor.commit();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (change_fragment){
            GeneralNewsFragment fragment = new GeneralNewsFragment();
            fragment.setFiltro(0);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame,fragment);
            fragmentTransaction.commit();
        }
    }

    /*

        PREPARE DATA FOR LIST

         */
    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();

        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setIconName("News");
        item1.setIconImg(R.drawable.ic_public_black_24dp);
        // Adding data header
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("Games");
        item2.setIconImg(R.drawable.ic_games_black_24dp);
        listDataHeader.add(item2);

        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("Settings");
        item3.setIconImg(R.drawable.ic_settings_black_24dp);
        listDataHeader.add(item3);

        ExpandedMenuModel item4 = new ExpandedMenuModel();
        item4.setIconName("Favoritos");
        item4.setIconImg(R.drawable.ic_favorite_black_24dp);
        listDataHeader.add(item4);

        // Adding child data

        List<String> heading2 = new ArrayList<>();
        heading2.add("Overwatch");
        heading2.add("CSGO");
        heading2.add("League of Legends");
        listDataChild.put(listDataHeader.get(1),heading2);
        categorias = new String[]{"overwatch","csgo","lol"};
        mMenuAdapter = new ExpandableListAdapter(appCompatActivity, listDataHeader, listDataChild, expandableList);
        expandableList.setAdapter(mMenuAdapter);
        /*noticiaViewModel.getCategorias(token).observe(this, new Observer<String[]>() {
            @Override
            public void onChanged(@Nullable String[] strings) {
                categorias = strings != null ? strings.clone() : new String[0];
                List<String> heading2 = null;
                if (strings != null) {
                    heading2 = new ArrayList<>(Arrays.asList(strings));
                }
                // Header, Child data
                listDataChild.put(listDataHeader.get(1), heading2);

                mMenuAdapter = new ExpandableListAdapter(appCompatActivity, listDataHeader, listDataChild, expandableList);
                // setting list adapter
                expandableList.setAdapter(mMenuAdapter);
            }
        });*/


    }

}
