package com.example.prox.HomeSection;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prox.ProductAdapter.AdapterProduct;
import com.example.prox.ProductAdapter.ListenerProduct;
import com.example.prox.ProductAdapter.Product;
import com.example.prox.ProductPage.ProductView;
import com.example.prox.R;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.List;

public class Search extends Fragment implements ListenerProduct {

    ImageButton search_button,barcode_button, search_history_search;
    TextInputEditText search;
    SharedPreferences sp;
    String search_text;
    View view;
    Button back_to_home;

    String url,username;

    private RecyclerView recyclerView;
    private List<Product> myProductList;
    AdapterProduct adapterProduct;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);


        search_button = view.findViewById(R.id.search_button_search);
        back_to_home = view.findViewById(R.id.back_to_home);
        search = view.findViewById(R.id.search_input_search);
        barcode_button = view.findViewById(R.id.barcode_button_search);
        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        search_text = sp.getString("search", "");
        username = sp.getString("username", "");
        search.setText(search_text);
        url = sp.getString("ip","");
        search_history_search = view.findViewById(R.id.search_history_search);

        SearchString(search_text);

        search_history_search.setOnClickListener(new View.OnClickListener() {
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
                if (!search_text.isEmpty() && !search_text.replace(" ", "").equals("")) {
                    SearchString(search.getText().toString());
                }
            }
        });

        back_to_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Home newFragment = new Home();
                ReplaceFragment(newFragment);
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

    protected void SearchString(String search) {
        String[] field = new String[2];
        field[0] = "string";
        field[1] = "username";
        //Creating array for data
        String[] data = new String[2];
        data[0] = search;
        data[1] = username;
        PutData putData = new PutData("http://" + url + "/SearchDisplay/searchString.php", "POST", field, data);
        if (putData.startPut()) {
            if (putData.onComplete()) {
                //Toast.makeText(getActivity(), putData.getResult(), Toast.LENGTH_SHORT).show();
                String[] result = putData.getResult().split("/");

                myProductList = new ArrayList<>();
                recyclerView = view.findViewById(R.id.recyclerViewProducts);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

                if (!result[0].isEmpty()) {
                    int loop = 0;
                    for (String row : result) {

                        if(loop<15)
                        {
                            String[] items = row.split(",");
                            //Toast.makeText(getActivity(), row, Toast.LENGTH_SHORT).show();
                            myProductList.add(new Product(items[0], items[1], "€" + items[2]));
                            loop++;
                        }
                        else
                        {
                            break;
                        }

                    }
                    adapterProduct = new AdapterProduct(getActivity(), myProductList, this);
                    recyclerView.setAdapter(adapterProduct);
                } else {
                    Toast.makeText(getActivity(), "No results", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public void onItemClicked(Product product) {

        SharedPreferences.Editor editor = sp.edit();

        editor.putString("search",search.getText().toString());
        editor.putString("product", product.getName());
        editor.commit();


        ProductView newFragment = new ProductView();
        ReplaceFragment(newFragment);
    }


    protected void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.main_fragment_container, fragment, null);
        fragmentTransaction.commit();
    }

}