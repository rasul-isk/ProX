package com.example.prox.ProductPage;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prox.HomeSection.Search;
import com.example.prox.Map;
import com.example.prox.R;
import com.example.prox.ReviewAdapter.AdapterReview;
import com.example.prox.ReviewAdapter.Review;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProductView extends Fragment {

    LinearLayout review_heading;
    SharedPreferences sp;
    Button find_store, share_review_button;
    ImageView product_image;
    String product_name_text, username,store_name;
    TextInputEditText review_input,rate_input;
    CircleImageView profile_photo_product, profile_image_product_fragment;
    TextView product_price, product_name, product_store, product_rating, product_category, product_description,back_to_search,store_type;

    String url;

    private RecyclerView recyclerView;
    private List<Review> myReviewList;
    AdapterReview adapterReview;

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
        url = sp.getString("ip","");

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
        back_to_search = view.findViewById(R.id.back_to_search);
        store_type = view.findViewById(R.id.store_type);
        rate_input = view.findViewById(R.id.rate_input);
        review_heading = view.findViewById(R.id.review_product_section);

        find_store = view.findViewById(R.id.find_store_button);

        //SetImageForProfile(username); Crashes once set image bitmap being called

        if (!product_name_text.equals("")) {
            String[] field = new String[1];
            field[0] = "string";
            //Creating array for data
            String[] data = new String[1];
            data[0] = product_name_text;
            PutData putData = new PutData("http://" + url + "/SearchDisplay/searchItem.php", "POST", field, data);


            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String[] result;
                    String temp = putData.getResult();
                    String url = "";
                    if (temp.contains("H&M")) {

                        int start = temp.indexOf("https://");
                        int end = temp.indexOf("[file:/product/style]") + 21;

                        url = temp.substring(start,end);
                        temp = temp.replace(url," ");
                        Picasso.get()//result[5]
                                .load(url)
                                .fit()
                                .into(product_image);

                        result = temp.split(",");

                    }
                    else if(temp.contains("Nike"))
                    {
                        int start = temp.indexOf("https://");
                        int end = temp.indexOf(".png") + 4;

                        url = temp.substring(start,end);
                        temp = temp.replace(url," ");
                        Picasso.get()//result[5]
                                .load(url)
                                .fit()
                                .into(product_image);

                        result = temp.split(",");
                    }
                    else
                    {
                        result = temp.split(",");
                        Picasso.get()
                                .load(result[5])
                                .fit()
                                .into(product_image);
                    }



                    //Toast.makeText(getActivity(), result[6], Toast.LENGTH_SHORT).show();

                    String price = "€" + result[1];
                    String store = "From " + result[4];
                    store_name = result[4];

                    String category = result[2].replace("_", " ").replace("-", " ");
                    String description = result[3].replace("_", " ").replace("-", " ");

                    product_name.setText(result[0]);
                    product_price.setText(price);
                    product_category.setText(category);
                    product_description.setText(description);
                    product_store.setText(store);

                    store_type.setText("Type: " + result[7]);
                    product_rating.setText("Rating: " + result[8]);

                    if (result[7].equals("Online")) {
                        find_store.setVisibility(View.INVISIBLE);
                    } else {
                        find_store.setVisibility(View.VISIBLE);
                    }

                }
            }

            PutData getReviews = new PutData("http://" + url + "/Reviews/getReviews.php", "POST", field, data);

            if (getReviews.startPut()) {
                if (getReviews.onComplete()) {
                    myReviewList = new ArrayList<>();
                    recyclerView = view.findViewById(R.id.recyclerViewReview);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

                    String[] result = getReviews.getResult().split("/");
                    if (!result[0].isEmpty()) {
                        int loop = 0;

                        review_heading.setVisibility(View.VISIBLE);

                        for (String row : result) {
                            if (loop < 5) {
                                String[] items = row.split(",");
                                //Toast.makeText(getActivity(), row, Toast.LENGTH_SHORT).show();
                                myReviewList.add(new Review(items[0], items[1], items[2]));
                                loop++;
                            } else {
                                break;
                            }
                            adapterReview = new AdapterReview(getActivity(), myReviewList);
                            recyclerView.setAdapter(adapterReview);
                        }
                    }
                    else
                    {
                        review_heading.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }

        find_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("map", store_name);
                editor.commit();

                Map newFragment = new Map();
                ReplaceFragment(newFragment);
            }
        });

        back_to_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search newFragment = new Search();
                ReplaceFragment(newFragment);
            }
        });

        share_review_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.equals(""))
                {
                    if(!review_input.getText().toString().isEmpty() && Float.parseFloat(rate_input.getText().toString()) <= 5)
                    {
                        String[] field = new String[4];
                        field[0] = "username";
                        field[1] = "product";
                        field[2] = "rate";
                        field[3] = "feedback";
                        //Creating array for data
                        String[] data = new String[4];
                        data[0] = username;
                        data[1] = product_name_text;
                        data[2] = rate_input.getText().toString();
                        data[3] = review_input.getText().toString();

                        PutData shareReview = new PutData("http://" + url + "/Reviews/shareReview.php", "POST", field, data);

                        if (shareReview.startPut()) {
                            if (shareReview.onComplete()) {
                                Toast.makeText(getActivity(), shareReview.getResult() + ", thank you!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        ProductView newFragment = new ProductView();
                        ReplaceFragment(newFragment);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Please enter feedback and rate from 1 to 5", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Please login for sharing feedback!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }

    protected void SetImageForProfile(String username) {
        String[] field = new String[1];
        field[0] = "username";
        //Creating array for data
        String[] data = new String[1];
        data[0] = username;

        PutData putData = new PutData("http://" + url + "/LoginRegister/getdata.php", "POST", field, data);

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
