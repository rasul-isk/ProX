package com.example.prox;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prox.databinding.FragmentHomeBinding;
import com.google.android.material.textfield.TextInputEditText;

public class HomeFragment extends Fragment {

    ImageButton search_button;
    TextInputEditText search;
    SharedPreferences sp;
    String search_text;


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        search_button = view.findViewById(R.id.search_button_home);
        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        search = view.findViewById(R.id.search_input_home);
        search_text = sp.getString("search", "");
        search.setText(search_text);

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