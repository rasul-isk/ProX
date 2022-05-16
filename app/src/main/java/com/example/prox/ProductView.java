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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProductView extends Fragment {

    SharedPreferences sp;
    Button find_store, share_review_button;
    ImageView product_image;
    String product_name_text, username;
    TextInputEditText review_input;
    CircleImageView profile_photo_product, profile_image_product_fragment;
    TextView product_price, product_name, product_store, product_rating, product_category, product_description;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_view, container, false);

        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        product_name_text = sp.getString("product", "");
        username = sp.getString("username", "");
        profile_photo_product = getActivity().findViewById(R.id.profile_image_product_fragment);


        product_image = view.findViewById(R.id.product_image);
        product_name = view.findViewById(R.id.product_name);
        product_price = view.findViewById(R.id.product_price);
        product_store = view.findViewById(R.id.product_store);
        product_rating = view.findViewById(R.id.product_rating);
        product_category = view.findViewById(R.id.product_category);
        product_description = view.findViewById(R.id.product_description);
        profile_image_product_fragment = view.findViewById(R.id.profile_image_product_fragment);
        review_input = view.findViewById(R.id.review_input);
        share_review_button = view.findViewById(R.id.share_review_button);

        find_store = view.findViewById(R.id.find_store_button);

        //SetImageForProfile(username); Crashes once set image bitmap being called

        if (!product_name_text.equals("")) {
            String[] field = new String[1];
            field[0] = "string";
            //Creating array for data
            String[] data = new String[1];
            data[0] = product_name_text;
            PutData putData = new PutData("http://172.16.23.134/SearchDisplay/searchItem.php", "POST", field, data);


            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String[] result = putData.getResult().split(",");
                    String price = "€" + result[1];
                    product_name.setText(result[0]);
                    product_price.setText(price);
                    product_category.setText(result[2]);
                    product_description.setText(result[3]);
                    product_store.setText(result[4]);
                    Toast.makeText(getActivity(), result[5], Toast.LENGTH_SHORT).show();

                    Picasso.get()
                            .load(result[5])
                            .fit()
                            .into(product_image);


                }
            }
        }

        return view;
    }

    protected void SetImageForProfile(String username) {
        String[] field = new String[1];
        field[0] = "username";
        //Creating array for data
        String[] data = new String[1];
        data[0] = username;

        PutData putData = new PutData("http://172.16.23.134/LoginRegister/getdata.php", "POST", field, data);

        if (putData.startPut()) {
            if (putData.onComplete()) {
                String[] result = putData.getResult().split(",");

                if (!result[8].equals("")) {
                    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + result[8].substring(0, result[8].length() - 1);

                    File imgFile = new File(path);

                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        Toast.makeText(getActivity(), "Crashes here...", Toast.LENGTH_SHORT).show();
                        profile_photo_product.setImageBitmap(myBitmap);
                    }
                }
            } else {
                Toast.makeText(getActivity(), putData.getResult(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    protected void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.main_fragment_container, fragment, null);
        fragmentTransaction.commit();
    }

}
