package com.hfad.lzr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.hfad.lzr.ui.main.GamesFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton fab;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.fab);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.setSelected(true);

        tabLayout.setTabTextColors(getResources().getColor(R.color.tab_not_selected),
                getResources().getColor(R.color.white));

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.orange_start));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(GamesFragment.newInstance(true), getResources().getString(R.string.recent_games));
        viewPagerAdapter.addFragment(GamesFragment.newInstance(false), getResources().getString(R.string.upcoming));
        viewPager.setAdapter(viewPagerAdapter);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_games);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateMatchActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getStringExtra("activity") != null) {
            if (getIntent().getStringExtra("activity").equals("CreatingMatchActivity")) {
                TabLayout.Tab tab = tabLayout.getTabAt(1);
                tab.select();
            }
        } else {
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.select();
        }
        navigationView.setCheckedItem(R.id.nav_games);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.nav_games:
                break;
            case R.id.nav_teams:
                intent = new Intent(this, TeamsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_create_league:
                intent = new Intent(this, LeaguesActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_standings:
                intent = new Intent(this, StandingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_player_stats:
                intent = new Intent(this, PlayerStatsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_arenas:
                intent = new Intent(this, ArenasActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_quit:
                finish();
                System.exit(0);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
