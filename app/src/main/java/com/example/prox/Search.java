package com.example.prox;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Search extends Fragment {

    ImageButton search_button;
    TextInputEditText search;
    SharedPreferences sp;
    String search_text;
    View view;

    private RecyclerView recyclerView;
    private List<Product> myProductList;
    Adapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);


        search_button = view.findViewById(R.id.search_button_search);
        search = view.findViewById(R.id.search_input_search);
        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        search_text = sp.getString("search", "");
        search.setText(search_text);

        SearchString(search_text);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!search_text.isEmpty() && !search_text.replace(" ", "").equals("")) {
                    SearchString(search.getText().toString());
                }
            }
        });

        return view;
    }

    protected void SearchString(String search) {
        String[] field = new String[1];
        field[0] = "string";
        //Creating array for data
        String[] data = new String[1];
        data[0] = search;
        PutData putData = new PutData("http://172.16.23.134/SearchDisplay/searchString.php", "POST", field, data);
        if (putData.startPut()) {
            if (putData.onComplete()) {
                String[] result = putData.getResult().split("/");

                myProductList = new ArrayList<>();
                recyclerView = view.findViewById(R.id.recyclerView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                myProductList.add(new Product("Evan", "2522"));
                myProductList.add(new Product("Evan1", "25221"));
                myProductList.add(new Product("Evan2", "25222"));
                myProductList.add(new Product("Evan3", "25223"));
                myProductList.add(new Product("Evan4", "25224"));
                myProductList.add(new Product("Evan5", "25225"));
                myProductList.add(new Product("Evan3", "25223"));
                myProductList.add(new Product("Evan4", "25224"));
                myProductList.add(new Product("Evan5", "25225"));
                myProductList.add(new Product("Evan3", "25223"));
                myProductList.add(new Product("Evan4", "25224"));
                myProductList.add(new Product("Evan5", "25225"));
                myProductList.add(new Product("Evan3", "25223"));
                myProductList.add(new Product("Evan4", "25224"));
                myProductList.add(new Product("Evan5", "25225"));

                adapter = new Adapter(getActivity(), myProductList);
                recyclerView.setAdapter(adapter);


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