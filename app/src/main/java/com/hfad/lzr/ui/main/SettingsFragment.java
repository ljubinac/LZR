package com.hfad.lzr.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;

import androidx.annotation.Nullable;

import com.hfad.lzr.R;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragment {

    public static final String THEMES = "themes";
    public static final String LANGUAGES = "languages";
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals(THEMES)){
                    Preference themesPref = findPreference(key);
                    themesPref.setSummary(sharedPreferences.getString(key, ""));
                }

                if(key.equals(LANGUAGES)){
                    Preference languagesPref = findPreference(key);
                    languagesPref.setSummary(sharedPreferences.getString(key, ""));
                }
            }
        };



    }

    /*public void setLocale(String lang){
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, AndroidLocale.class);
        finish();
         startActivity(refresh}*/

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        Preference themesPref = findPreference(THEMES);
        themesPref.setSummary(getPreferenceScreen().getSharedPreferences().getString(THEMES, ""));

        Preference languagesPref = findPreference(LANGUAGES);
        languagesPref.setSummary(getPreferenceScreen().getSharedPreferences().getString(LANGUAGES, ""));
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
