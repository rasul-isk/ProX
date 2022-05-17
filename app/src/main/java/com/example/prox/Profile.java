package com.example.prox;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    String username, password;
    SharedPreferences sp;
    Button signout;
    TextView full_name, email, feedback, rate, favourite, location, phone, edit_profile;
    CircleImageView profile_photo, profile_photo_product;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        signout = view.findViewById(R.id.sign_out);

        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        username = sp.getString("username", "");
        password = sp.getString("password", "");

        full_name = view.findViewById(R.id.full_name);
        email = view.findViewById(R.id.email);
        feedback = view.findViewById(R.id.recent_feedbacks);
        rate = view.findViewById(R.id.average_rate);
        favourite = view.findViewById(R.id.favourite_categories);
        location = view.findViewById(R.id.location);
        phone = view.findViewById(R.id.phone);
        edit_profile = view.findViewById(R.id.edit_profile);
        profile_photo = view.findViewById(R.id.profile_image);

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

                    if (!result[8].equals("")) {
                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + result[8].substring(0, result[8].length() - 1);

                        File imgFile = new File(path);

                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            profile_photo.setImageBitmap(myBitmap);
                            //profile_photo_product.setImageBitmap(myBitmap);
                        }

                    }

                    feedback.setText(result[9]);
                    rate.setText(result[10]);
                } else {
                    Toast.makeText(getActivity(), putData.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        } else {
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

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile_edit newFragment = new Profile_edit();
                ReplaceFragment(newFragment);
            }
        });

        return view;
    }

    protected void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.main_fragment_container, fragment, null);
        fragmentTransaction.commit();
    }
}