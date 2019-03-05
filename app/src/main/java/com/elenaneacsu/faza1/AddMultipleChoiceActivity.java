package com.elenaneacsu.faza1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddMultipleChoiceActivity extends AppCompatActivity {

    public static final String QUESTION_COUNTER = "0";
    public static final String UNIQUE_ID = "unique_id";
    public static final String TEACHER_UID = "teacher_uid";

    private EditText mEditTextQuestion;
    private EditText mEditTextFirstAns;
    private EditText mEditTextSecondAns;
    private EditText mEditTextThirdAns;
    private EditText mEditTextFourthAns;
    private CheckBox mCheckBox1;
    private CheckBox mCheckBox2;
    private CheckBox mCheckBox3;
    private CheckBox mCheckBox4;
    private DatabaseReference mDatabaseReference;
    private int questionCounter;
    private String uniqueID;
    private String teacherUID;
    private List<String> correctAnswers = new ArrayList<>();
    private List<String> incorrectAnswers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_multiple_choice);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            questionCounter = bundle.getInt(AddQuizActivity.QUESTION_COUNTER);
            uniqueID = bundle.getString(AddQuizActivity.UNIQUE_ID);
            teacherUID = bundle.getString(TEACHER_UID);
        }

        initView();
    }

    private void initView() {
        mEditTextQuestion = findViewById(R.id.edittext_question);
        mEditTextFirstAns = findViewById(R.id.edittext_firstans);
        mEditTextSecondAns = findViewById(R.id.edittext_secondans);
        mEditTextThirdAns = findViewById(R.id.edittext_thirdans);
        mEditTextFourthAns= findViewById(R.id.edittext_fourthans);

        mCheckBox1 = findViewById(R.id.checkBox1);
        mCheckBox2 = findViewById(R.id.checkBox2);
        mCheckBox3 = findViewById(R.id.checkBox3);
        mCheckBox4 = findViewById(R.id.checkBox4);
    }


    public void btnSaveMCOnclick(View view) {
        DatabaseReference questionReference = mDatabaseReference.child("TESTS").child(uniqueID).child("question" + questionCounter);
        questionReference.child("text").setValue(mEditTextQuestion.getText().toString());
        questionReference.child("type").setValue("multiple");
        checkAnswers();
        for (int i = 0; i < correctAnswers.size(); i++) {
            questionReference.child("correct_answers").child(String.valueOf(i)).setValue(correctAnswers.get(i));
        }

        for (int i = 0; i < incorrectAnswers.size(); i++) {
            questionReference.child("incorrect_answers").child(String.valueOf(i)).setValue(incorrectAnswers.get(i));
        }

        correctAnswers.clear();
        incorrectAnswers.clear();

        questionCounter++;

        emptyFields();

        Toast.makeText(getApplicationContext(), "Successfully added", Toast.LENGTH_LONG).show();
    }

    private void checkAnswers() {
        if(mCheckBox1.isChecked()){
            correctAnswers.add(mEditTextFirstAns.getText().toString());
        } else {
            incorrectAnswers.add(mEditTextFirstAns.getText().toString());
        }

        if(mCheckBox2.isChecked()){
            correctAnswers.add(mEditTextSecondAns.getText().toString());
        } else {
            incorrectAnswers.add(mEditTextSecondAns.getText().toString());
        }

        if(mCheckBox3.isChecked()){
            correctAnswers.add(mEditTextThirdAns.getText().toString());
        } else {
            incorrectAnswers.add(mEditTextThirdAns.getText().toString());
        }

        if(mCheckBox4.isChecked()){
            correctAnswers.add(mEditTextFourthAns.getText().toString());
        } else {
            incorrectAnswers.add(mEditTextFourthAns.getText().toString());
        }
    }

    private void emptyFields() {
        mEditTextQuestion.setText("");
        mEditTextSecondAns.setText("");
        mEditTextFirstAns.setText("");
        mEditTextThirdAns.setText("");
        mEditTextFourthAns.setText("");
        mCheckBox1.setChecked(false);
        mCheckBox2.setChecked(false);
        mCheckBox3.setChecked(false);
        mCheckBox4.setChecked(false);

    }

    public void btnGoBackFromMCAOnClick(View view) {
        Intent intent = new Intent(AddMultipleChoiceActivity.this, AddQuizActivity.class);
        intent.putExtra(QUESTION_COUNTER, questionCounter);
        intent.putExtra(UNIQUE_ID, uniqueID);
        intent.putExtra(TEACHER_UID, teacherUID);
        Log.d("uid", "btnGoBackFromMCAOnClick: teacher uid "+teacherUID);
        startActivity(intent);
    }
}
