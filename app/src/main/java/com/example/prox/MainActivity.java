package com.example.prox;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import org.apache.commons.io.FileUtils;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.renderscript.ScriptGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prox.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

            SharedPreferences sp;
            sp = getSharedPreferences("user_details", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();

            editor.putString("profile_img", name);
            editor.commit();

            CircleImageView profile_edited_photo;
            profile_edited_photo = findViewById(R.id.profile_edited_photo);
            profile_edited_photo.setImageURI(uri);

   /*         InputStream stream = null;

            try {

                assert uri !=null;
                stream = getContentResolver().openInputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            BitmapFactory.decodeStream(stream);*/


        }
    }
}