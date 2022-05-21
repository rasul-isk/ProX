package com.example.prox.ProfileSection;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prox.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_edit extends Fragment {
    SharedPreferences sp;
    TextView return_back;
    Button save_changes;
    String username, password;
    TextInputLayout full_name, email, favourite, location, phone;
    TextInputEditText full_name_input, email_input, favourite_input, location_input, phone_input;
    CircleImageView profile_edited_photo;
    String url;

    ImageView imageButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_edit, container, false);


        /*DON'T FORGET REASSIGNING EDITED PASSWORD/USERNAME*/
        return_back = view.findViewById(R.id.back_to_profile);

        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        username = sp.getString("username", "");
        password = sp.getString("password", "");
        url = sp.getString("ip","");

        full_name = view.findViewById(R.id.full_name_text_e);
        email = view.findViewById(R.id.email_text_e);
        favourite = view.findViewById(R.id.favourite_text_e);
        location = view.findViewById(R.id.location_text_e);
        phone = view.findViewById(R.id.phone_text_e);
        save_changes = view.findViewById(R.id.save_changes);
        imageButton = view.findViewById(R.id.profile_edited_photo);
        profile_edited_photo = view.findViewById(R.id.profile_edited_photo);

        full_name_input = view.findViewById(R.id.full_name_e);
        email_input = view.findViewById(R.id.email_capture_e);
        favourite_input = view.findViewById(R.id.favourite_categories_e);
        location_input = view.findViewById(R.id.location_e);
        phone_input = view.findViewById(R.id.phone_e);

        if (username != "" && password != "") {
            String[] field = new String[1];
            field[0] = "username";
            //Creating array for data
            String[] data = new String[1];
            data[0] = username;

            PutData putData = new PutData("http://" + url + "/LoginRegister/getdata.php", "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String[] result = putData.getResult().split(",");


                    email_input.setText(result[1]);
                    full_name_input.setText(result[2]);

                    if (!result[3].equals("0")) {
                        phone_input.setText(result[3]);
                    } else {
                        phone_input.setHint("Unknown");
                    }

                    if (!result[4].equals("")) {
                        favourite_input.setText(result[4]);
                    } else {
                        favourite_input.setHint("Unknown");
                    }

                    if (!result[5].equals("")) {
                        location_input.setText(result[5]);
                    } else {
                        location_input.setHint("Unknown");
                    }

                    if (!result[8].equals("")) {
                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + result[8].substring(0, result[8].length() - 1);

                        File imgFile = new File(path);

                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            profile_edited_photo.setImageBitmap(myBitmap);
                            //profile_photo_product.setImageBitmap(myBitmap);
                        }

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
                String[] field = new String[7];
                field[0] = "fullname";
                field[1] = "username";
                field[2] = "favCateg";
                field[3] = "email";
                field[4] = "phone";
                field[5] = "location";
                field[6] = "profile_img";
                //Creating array for data
                String[] data = new String[7];
                data[0] = full_name_input.getText().toString();
                data[1] = username;
                data[2] = favourite_input.getText().toString();
                data[3] = email_input.getText().toString();
                data[4] = phone_input.getText().toString();
                data[5] = location_input.getText().toString();
                data[6] = sp.getString("profile_img", "");


                PutData putData = new PutData("http://" + url + "/LoginRegister/update_profile.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if (result.equals("Edited Successfully")) {
                            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("profile_img", "");
                            editor.commit();
                        } else {
                            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                Profile newFragment = new Profile();
                ReplaceFragment(newFragment);

            }
        });

        return_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sp.edit();

                editor.putString("profile_img", "");
                editor.commit();

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
                        .saveDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))
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