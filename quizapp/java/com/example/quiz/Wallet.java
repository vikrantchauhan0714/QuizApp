package com.example.quiz;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quiz.databinding.FragmentWalletBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sdsmdg.tastytoast.TastyToast;


public class Wallet extends Fragment {



    public Wallet() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentWalletBinding binding;
    FirebaseFirestore database;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentWalletBinding.inflate(inflater,container,false);
        database=FirebaseFirestore.getInstance();
        // Inflate the layout for this fragment


        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                user=documentSnapshot.toObject(User.class);

                binding.amount.setText(user.getCoins() + "");
            }
        });
        binding.sendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getCoins()>50000){
                  String uid=  FirebaseAuth.getInstance().getUid();
                  String paypalEmail=binding.emailBox.getText().toString();
                  long coins=user.getCoins();
                    Withdraw_Request request=new Withdraw_Request(uid,paypalEmail,user.getName(),coins);
                    database.collection("withdraws")
                            .document()
                            .set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            TastyToast.makeText(getContext(),"Request sent successfully.",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                        }
                    });
                }
                else{
                    TastyToast.makeText(getContext(),"You need to more coins to withdraw!",TastyToast.LENGTH_LONG,TastyToast.WARNING);
                }
            }
        });
        return binding.getRoot();
    }
}
