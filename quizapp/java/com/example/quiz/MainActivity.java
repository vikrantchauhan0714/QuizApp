package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.Toast;

import com.example.quiz.databinding.ActivityMainBinding;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
   ;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding=ActivityMainBinding.inflate(getLayoutInflater());// do not use of findviewbyid......

        setContentView(binding.getRoot());
        binding.toolbar.setTitleTextColor(Color.WHITE
        );
           setSupportActionBar(binding.toolbar);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content,new Home_Fragment());
        transaction.commit();

           binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {

               @Override
               public boolean onItemSelect(int i) {
                   FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                   switch(i) {
                       case 0 : transaction.replace(R.id.content,new Home_Fragment());
                           transaction.commit();
                           break;
                       case 1 :
                           transaction.replace(R.id.content,new Leaderboard());
                           transaction.commit();
                           break;
                       case 2 :
                           transaction.replace(R.id.content,new Wallet());
                           transaction.commit();
                           break;
                       case 3 :


                           break;
                   }
                   return false;
               }
           });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wallet_menu,menu);
    return super.onCreateOptionsMenu(menu);
    }



}
