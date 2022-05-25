package com.example.prox.HomeSection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prox.R;
import com.example.prox.ReviewAdapter.AdapterReview;
import com.example.prox.ReviewAdapter.Review;
import com.example.prox.databinding.FragmentHomeBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private RecyclerView recyclerView;
    private List<Review> myReviewList;
    AdapterReview adapterReview;

    ImageButton search_button, barcode_button, sneakers,tshirt,nike,men,shorts,sandals,cosmetics,women, search_history_home;
    TextInputEditText search;
    SharedPreferences sp;
    String search_text,url,username;



    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        search_button = view.findViewById(R.id.search_button_home);
        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        search = view.findViewById(R.id.search_input_home);
        search_text = sp.getString("search", "");

        search.setText(search_text);
        url = sp.getString("ip","");
        username = sp.getString("username","");

        sneakers = view.findViewById(R.id.sneakers);
        tshirt = view.findViewById(R.id.tshirt);
        nike = view.findViewById(R.id.nike);
        men = view.findViewById(R.id.male);
        shorts = view.findViewById(R.id.shorts);
        sandals = view.findViewById(R.id.sandals);
        cosmetics = view.findViewById(R.id.cosmetics);
        women = view.findViewById(R.id.female);
        search_history_home = view.findViewById(R.id.search_history_home);

        barcode_button = view.findViewById(R.id.barcode_button_home);


        String[] field = new String[1];
        field[0] = "string";
        //Creating array for data
        String[] data = new String[1];
        data[0] = "";

        PutData getReviews = new PutData("http://" + url + "/Reviews/getReviews.php", "POST", field, data);

        if (getReviews.startPut()) {
            if (getReviews.onComplete()) {
                myReviewList = new ArrayList<>();
                recyclerView = view.findViewById(R.id.recyclerViewReviewHome);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

                String[] result = getReviews.getResult().split("/");
                if (!result[0].isEmpty()) {
                    int loop = 0;

                    for (String row : result) {
                        if (loop < 10) {
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
            }
        }

        search_history_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.equals(""))
                {
                    search_text = search.getText().toString();

                    if (!search_text.equals("") && !search_text.replace(" ", "").equals("")) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("search", search_text);
                        editor.commit();
                    }

                    History newFragment = new History();
                    ReplaceFragment(newFragment);
                }
                else
                {
                    Toast.makeText(getActivity(), "Please login to see search history!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                search_text = search.getText().toString();

                if (!search_text.equals("") && !search_text.replace(" ", "").equals("")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("search", search_text);
                    editor.commit();

                    Search newFragment = new Search();
                    ReplaceFragment(newFragment);
                }
            }
        });

        sneakers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text = "sneakers";

                if (!search_text.equals("") && !search_text.replace(" ", "").equals("")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("search", search_text);
                    editor.commit();

                    Search newFragment = new Search();
                    ReplaceFragment(newFragment);
                }
            }
        });

        tshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text = "t-shirt";

                if (!search_text.equals("") && !search_text.replace(" ", "").equals("")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("search", search_text);
                    editor.commit();

                    Search newFragment = new Search();
                    ReplaceFragment(newFragment);
                }
            }
        });

        nike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text = "nike";

                if (!search_text.equals("") && !search_text.replace(" ", "").equals("")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("search", search_text);
                    editor.commit();

                    Search newFragment = new Search();
                    ReplaceFragment(newFragment);
                }
            }
        });

        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text = "shirt men";

                if (!search_text.equals("") && !search_text.replace(" ", "").equals("")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("search", search_text);
                    editor.commit();

                    Search newFragment = new Search();
                    ReplaceFragment(newFragment);
                }
            }
        });


        shorts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text = "shorts";

                if (!search_text.equals("") && !search_text.replace(" ", "").equals("")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("search", search_text);
                    editor.commit();

                    Search newFragment = new Search();
                    ReplaceFragment(newFragment);
                }
            }
        });

        sandals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text = "sandals";

                if (!search_text.equals("") && !search_text.replace(" ", "").equals("")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("search", search_text);
                    editor.commit();

                    Search newFragment = new Search();
                    ReplaceFragment(newFragment);
                }
            }
        });

        cosmetics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text = "cosmetics";

                if (!search_text.equals("") && !search_text.replace(" ", "").equals("")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("search", search_text);
                    editor.commit();

                    Search newFragment = new Search();
                    ReplaceFragment(newFragment);
                }
            }
        });

        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_text = "women";

                if (!search_text.equals("") && !search_text.replace(" ", "").equals("")) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("search", search_text);
                    editor.commit();

                    Search newFragment = new Search();
                    ReplaceFragment(newFragment);
                }
            }
        });

        barcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Barcode newFragment = new Barcode();
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