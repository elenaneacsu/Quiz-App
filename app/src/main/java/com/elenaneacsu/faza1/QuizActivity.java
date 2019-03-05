package com.elenaneacsu.faza1;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    public int score;
    public List<TestQuestion> mQuestions = new ArrayList<>();
    private String code;

    public TextView mTextViewQuestion;
    public TextView mTextViewScore;

    public int counter = 0;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            code = bundle.getString(StudentMainActivity.ROOM_NUMBER);
        }

        initView();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        getTest();
    }

    private void initView() {
        mTextViewQuestion = findViewById(R.id.textview_question);
        mTextViewScore = findViewById(R.id.textview_score);
    }

    private void initFragment(List<String> correctAnswers, List<String> incorrectAnswers) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.placeholder, MultipleChoiceFragment.newInstance(correctAnswers, incorrectAnswers));
        fragmentTransaction.commit();
    }

    private void getTest() {
        final ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot test : dataSnapshot.getChildren()) {
                    TestQuestion testQuestion = new TestQuestion();
                    testQuestion.setText((String) test.child("text").getValue());
                    testQuestion.setType((String) test.child("type").getValue());

                    List<String> correctAnswers = new ArrayList<>();
                    long noCorrectAnswers = test.child("correct_answers").getChildrenCount();
                    for (int i = 0; i < noCorrectAnswers; i++) {
                        correctAnswers.add((String) test.child("correct_answers").child(String.valueOf(i)).getValue());
                    }

                    testQuestion.setCorrectAnswers(correctAnswers);

                    List<String> incorrectAnswers = new ArrayList<>();
                    long noIncorrectAnswers = test.child("incorrect_answers").getChildrenCount();
                    for (int i = 0; i < noIncorrectAnswers; i++) {
                        incorrectAnswers.add((String) test.child("incorrect_answers").child(String.valueOf(i)).getValue());
                    }

                    testQuestion.setIncorrectAnswers(incorrectAnswers);

                    mQuestions.add(testQuestion);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.child("TESTS").child(code).addListenerForSingleValueEvent(valueEventListener);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setFragment(mQuestions.get(counter).getType());
                mTextViewQuestion.setText(mQuestions.get(counter).getText());
                setTitle();
            }
        }, 5000);

    }

    private void setFragment(String type) {
        if (type.equals("multiple")) {
            TestQuestion currentTestQuestion = mQuestions.get(counter);
            initFragment(currentTestQuestion.getCorrectAnswers(), currentTestQuestion.getIncorrectAnswers());
        }
        if (type.equals("true_false")) {
            TestQuestion currentTestQuestion = mQuestions.get(counter);
        }
    }

    public void setTitle(){
        mTextViewQuestion.setText(mQuestions.get(counter).getText());
    };
}
