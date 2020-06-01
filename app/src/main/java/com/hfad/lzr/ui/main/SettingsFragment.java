package com.hfad.lzr.ui.main;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.hfad.lzr.MainActivity;
import com.hfad.lzr.R;

import java.util.Locale;


public class SettingsFragment extends PreferenceFragmentCompat {

    ListPreference languages;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        languages = findPreference("languages");


        languages.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override

            public boolean onPreferenceChange(Preference preference, Object o) {

                if (o.equals("1"))
                    setLocal("en");
                else if (o.equals("2"))
                    setLocal("sr");
                else
                    setLocal("en");
                getActivity().recreate();
                return true;

            }

        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void setLocal(String lang) {

        lang = (getResources().getConfiguration().locale.toString().toLowerCase().equals(Locale.UK.toString().toLowerCase())) ? "sr" : Locale.UK.toString();
        Locale myLocale = new Locale(lang); //ovo je mozda visak
        Resources res = getResources();

// Change locale settings in the app.

        DisplayMetrics dm = res.getDisplayMetrics();

        android.content.res.Configuration conf = res.getConfiguration();

        conf.setLocale(new Locale(lang.toLowerCase())); // API 17+ only.
        conf.locale = myLocale; //ovo je mozda visak
// Use conf.locale = new Locale(...) if targeting lower versions

        res.updateConfiguration(conf, dm);

        Intent refresh = new Intent(getContext(), MainActivity.class);
        startActivity(refresh);
        getActivity().finish();


    }
}
