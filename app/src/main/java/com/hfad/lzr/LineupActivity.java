package com.hfad.lzr;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.hfad.lzr.ui.main.ChooseLineupFragment;
import com.hfad.lzr.ui.main.SectionsPagerAdapter;


public class LineupActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineup);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        String teamAName = getIntent().getStringExtra("teamA");
        String teamBName = getIntent().getStringExtra("teamB");
        String teamAId = getIntent().getStringExtra("teamAId");
        String teamBId = getIntent().getStringExtra("teamBId");

        sectionsPagerAdapter.addFragment(ChooseLineupFragment.newInstance(teamAName, teamAId), getIntent().getStringExtra("teamA"));
        sectionsPagerAdapter.addFragment(ChooseLineupFragment.newInstance(teamBName, teamBId), getIntent().getStringExtra("teamB"));
        viewPager.setAdapter(sectionsPagerAdapter);
    }
}