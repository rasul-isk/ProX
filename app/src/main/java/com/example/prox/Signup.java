package com.example.prox;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.prox.databinding.FragmentMapBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Signup extends Fragment {

    TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        textInputEditTextUsername = view.findViewById(R.id.username);
        textInputEditTextFullname = view.findViewById(R.id.fullname);
        textInputEditTextEmail = view.findViewById(R.id.email);
        textInputEditTextPassword = view.findViewById(R.id.password);

        buttonSignUp = view.findViewById(R.id.buttonSignUp);

        textViewLogin = view.findViewById(R.id.login_here);
        progressBar = view.findViewById(R.id.progress);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login newFragment = new Login();
                ReplaceFragment(newFragment);
            }
        });


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textInputEditTextUsername.onEditorAction(EditorInfo.IME_ACTION_DONE);  //KEYBOARD HIDE
                textInputEditTextPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);
                textInputEditTextFullname.onEditorAction(EditorInfo.IME_ACTION_DONE);
                textInputEditTextEmail.onEditorAction(EditorInfo.IME_ACTION_DONE);

                String fullname, username, password, email;

                fullname = String.valueOf(textInputEditTextFullname.getText());
                username = String.valueOf(textInputEditTextUsername.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if (!fullname.equals("") && !username.equals("") && !email.equals("") && !password.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;
                            PutData putData = new PutData("http://172.16.23.134/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

                                        Login newFragment = new Login();
                                        ReplaceFragment(newFragment);
                                    } else {
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "All fields required", Toast.LENGTH_SHORT).show();
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