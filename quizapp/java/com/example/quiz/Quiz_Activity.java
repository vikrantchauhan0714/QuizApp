package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.quiz.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Random;

public class Quiz_Activity extends AppCompatActivity {

    ActivityQuizBinding binding;
    ArrayList<Question> questions;
    int index=0 ;
    Question question;
    CountDownTimer timer;
    FirebaseFirestore database;
    int correctAnswere=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        questions = new ArrayList<>();
        database=FirebaseFirestore.getInstance();

        final String catId=getIntent().getStringExtra("catId");
        Random random=new Random();
        final int rand=random.nextInt(12);

        database.collection("categories")
                .document(catId)
                .collection("questions")
                .whereGreaterThanOrEqualTo("index",rand)
                .orderBy("index")
                .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.getDocuments().size()< 5)
                {
                    database.collection("categories")
                            .document(catId)
                            .collection("questions")
                            .whereLessThanOrEqualTo("index",rand)
                            .orderBy("index")
                            .limit(5).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (DocumentSnapshot snapshot :queryDocumentSnapshots){
                                Question question=snapshot.toObject(Question.class);
                                questions.add(question);
                            }
                        setNextQuestion();
                        }

                    });
                }else{
                    for (DocumentSnapshot snapshot :queryDocumentSnapshots){
                        Question question=snapshot.toObject(Question.class);
                        questions.add(question);
                    }


                    setNextQuestion();

                }
            }
        });

        resetTimer();

    }
    void resetTimer(){

        timer=new CountDownTimer(24000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
             binding.timers.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {


             }
            };
        }



    void showAnswere(){
        if (question.getAnswere().equals(binding.option1.getText().toString())){
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if (question.getAnswere().equals(binding.option2.getText().toString())){
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));}
        else if (question.getAnswere().equals(binding.option1.getText().toString())) {
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if (question.getAnswere().equals(binding.option3.getText().toString())) {
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
        else if (question.getAnswere().equals(binding.option4.getText().toString())) {
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));
        }
    }


    void  checkAnswere(TextView textview){
        String selectAnswere=textview.getText().toString();
        if(selectAnswere.equals(question.getAnswere())){

            correctAnswere++;


            textview.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else{
            showAnswere();

            textview.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }

    }
    void reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.options_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.options_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.options_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.options_unselected));
    }

    void setNextQuestion() {
        if(timer!=null)
            timer.cancel();

        timer.start();
        if (index < questions.size()) {
            binding.quesCounter.setText(String.format("%d/%d",(index+1),questions.size()));
             question = questions.get(index);
            binding.questio.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());


        }

    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.option1 :
            case R.id.option2 :
            case R.id.option3 :
            case R.id.option4 :
                if(timer!=null)
                    timer.cancel();


                TextView selected=(TextView) view;
                checkAnswere(selected);
break;
            case R.id.next :
                reset();
                if (index <= questions.size()){
                    index++;
                setNextQuestion();
                }
                else{
                    Intent intent=new Intent(Quiz_Activity.this,Result_Activity.class);
                    intent.putExtra("correct",correctAnswere);
                    intent.putExtra("total",questions.size());
                    startActivity(intent);
                    TastyToast.makeText(getApplication(),"Quized Finished",TastyToast.LENGTH_LONG,TastyToast.WARNING);
                }
                break;
        }

    }
}