package com.example.prox;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.prox.HomeSection.Home;
import com.example.prox.HomeSection.Search;
import com.google.zxing.Result;

public class Barcode extends Fragment {

    private CodeScanner mCodeScanner;
    TextView back_from_scanner;
    SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner, container, false);


        sp = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);

        back_from_scanner = view.findViewById(R.id.back_from_scanner);

        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);
        mCodeScanner.startPreview();

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("search", result.getText());
                        editor.commit();

                        Search newFragment = new Search();
                        ReplaceFragment(newFragment);
                    }
                });
            }
        });

        back_from_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Home newFragment = new Home();
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