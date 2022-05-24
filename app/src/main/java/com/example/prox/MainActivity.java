package com.example.prox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prox.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ip", "172.16.23.134");//10.40.14.82  | 172.16.23.134
        editor.commit();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_map, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.main_fragment_container);

        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_OK) {
            Uri uri = data.getData();
            String[] result = uri.toString().split("/");
            String name = result[result.length - 1];

            sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("profile_img", name);

            editor.commit();

            CircleImageView profile_edited_photo;
            profile_edited_photo = findViewById(R.id.profile_edited_photo);
            profile_edited_photo.setImageURI(uri);
        }
    }

}