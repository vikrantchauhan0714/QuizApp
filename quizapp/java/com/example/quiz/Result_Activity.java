package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.quiz.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class Result_Activity extends AppCompatActivity {
    ActivityResultBinding binding;
    int points=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int correctAnswere=getIntent().getIntExtra("correct",0);
        int totalQuestion=getIntent().getIntExtra("total",0);

        long points=correctAnswere*10;

        binding.score.setText(String.format("%d/%d",correctAnswere,totalQuestion));
        binding.coinswin.setText(String.valueOf(points));

        FirebaseFirestore database=FirebaseFirestore.getInstance();

        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .update("coins", FieldValue.increment(points));
    }
}
