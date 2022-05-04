package com.example.prox;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Profile_edit extends Fragment {
    SharedPreferences sp;
    TextView return_back;
    Button save_changes;
    String username, password;
    TextView full_name, email, favourite, location, phone, edit_profile;


    ImageView imageButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_edit, container, false);


        /*DON'T FORGET REASSIGNING EDITED PASSWORD/USERNAME*/
        return_back = view.findViewById(R.id.back_to_profile);

        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        username = sp.getString("username", "");
        password = sp.getString("password", "");

        full_name = view.findViewById(R.id.full_name);
        email = view.findViewById(R.id.email);
        favourite = view.findViewById(R.id.favourite_categories);
        location = view.findViewById(R.id.location);
        phone = view.findViewById(R.id.phone);
        save_changes = view.findViewById(R.id.save_changes);
        imageButton = view.findViewById(R.id.profile_edited_photo);

        if (username != "" && password != "") {
            String[] field = new String[1];
            field[0] = "username";
            //Creating array for data
            String[] data = new String[1];
            data[0] = username;

            PutData putData = new PutData("http://172.16.23.134/LoginRegister/getdata.php", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String[] result = putData.getResult().split(",");

                    email.setText(result[1]);
                    full_name.setText(result[2]);

                    if (!result[3].equals("0")) {
                        phone.setText(result[3]);
                    } else {
                        phone.setText("Unknown");
                    }

                    if (!result[4].equals("")) {
                        favourite.setText(result[4]);
                    } else {
                        favourite.setText("Unknown");
                    }

                    if (!result[5].equals("")) {
                        location.setText(result[5]);
                    } else {
                        location.setText("Unknown");
                    }

                } else {
                    Toast.makeText(getActivity(), putData.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Login newFragment = new Login();
            ReplaceFragment(newFragment);
        }

        save_changes.setOnClickListener(new View.OnClickListener() {
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

        return_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile newFragment = new Profile();
                ReplaceFragment(newFragment);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(getActivity())
                        .cropSquare()
                        .compress(1024)
                        .maxResultSize(320, 320)
                        .start();

            }
        });

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