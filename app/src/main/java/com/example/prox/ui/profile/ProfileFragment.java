package com.example.prox.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.prox.R;

public class ProfileFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        if (sp.getString("username", "") != "" && sp.getString("password", "") != "") {
            //STOPPED HERE, START TO DEVELOP PROFILE SECTION
            Toast.makeText(getActivity(), sp.getString("username", "") + "  " + sp.getString("password", ""), Toast.LENGTH_SHORT).show();
        }


        return view;
    }
}