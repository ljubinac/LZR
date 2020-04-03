package com.hfad.lzr.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hfad.lzr.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChooseLineupFragment extends Fragment {

    TextView tv;
    String teamName;
    String teamId;

    public static ChooseLineupFragment newInstance(String teamName, String teamId) {
        ChooseLineupFragment myFragment = new ChooseLineupFragment();

        Bundle args = new Bundle();
        args.putString("teamName", teamName);
        args.putString("teamId", teamId);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        teamName = getArguments().getString("teamName");
        teamId = getArguments().getString("teamId");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_lineup, container, false);

        tv = root.findViewById(R.id.section_label);
        tv.setText(teamName + "    "  + teamId);



        return root;
    }
}