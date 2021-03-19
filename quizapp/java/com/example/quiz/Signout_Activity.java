package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.quiz.databinding.ActivitySignoutBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Signout_Activity extends AppCompatActivity {
   ActivitySignoutBinding binding;
   private FirebaseAuth auth;
   private FirebaseFirestore database;
   ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();
        dialog=new ProgressDialog(this);

        binding=ActivitySignoutBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.createbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,name,referCode;

                email=binding.emailBox.getText().toString();
                password=binding.passBox.getText().toString();
                name=binding.namebox.getText().toString();
                referCode=binding.referCode.getText().toString();

                final User user=new User(name,email,password,referCode);

                dialog.show();

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String uid=task.getResult().getUser().getUid();
                        if(task.isSuccessful()){
                            dialog.dismiss();
                            database.collection("users")
                                    .document(uid)
                                    .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        startActivity(new Intent(Signout_Activity.this, MainActivity.class));
                                        finish();

                                    }else{
                                        dialog.dismiss();
                                        Toast.makeText(Signout_Activity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                        else{
                            dialog.dismiss();
                            Toast.makeText(Signout_Activity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        binding.signcretebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Signout_Activity.this,Login_Activity.class);
                startActivity(intent);

            }
        });


    }
}
