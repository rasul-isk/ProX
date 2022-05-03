package com.example.prox;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Profile_edit extends Fragment {
    SharedPreferences sp;
    Button signout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_edit, container, false);
/*

        DON'T FORGET REASSIGNING EDITED PASSWORD/USERNAME
        signout = view.findViewById(R.id.sign_out);
        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);


        if (sp.getString("username", "") != "" && sp.getString("password", "") != "") {


        }
        else {
            Login newFragment = new Login();
            ReplaceFragment(newFragment);
        }

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sp.edit();

                editor.putString("username", "");
                editor.putString("password", "");
                editor.commit();

                Login newFragment = new Login();
                ReplaceFragment(newFragment);
            }
        });

 */

        return view;
    }
    protected void ReplaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.main_fragment_container, fragment, null);
        fragmentTransaction.commit();
    }
}