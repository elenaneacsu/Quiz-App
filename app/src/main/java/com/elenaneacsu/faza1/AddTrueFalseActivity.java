package com.elenaneacsu.faza1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTrueFalseActivity extends AppCompatActivity {

    public static final String QUESTION_COUNTER = "0";
    public static final String UNIQUE_ID = "unique_id";
    public static final String TEACHER_UID = "teacher_uid";

    private EditText mEditTextQuestion;
    private RadioButton mRadioButtonTrue;
    private RadioButton mRadioButtonFalse;
    private DatabaseReference mDatabaseReference;
    private int questionCounter;
    private String uniqueID;
    private String teacherUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_true_false);

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
        mRadioButtonTrue = findViewById(R.id.radiobutton_true);
        mRadioButtonFalse = findViewById(R.id.radiobutton_false);
    }


    public void btnSaveTFOnClick(View view) {
        DatabaseReference questionReference = mDatabaseReference.child("TESTS").child(uniqueID).child("question" + questionCounter);
        questionReference.child("text").setValue(mEditTextQuestion.getText().toString());
        questionReference.child("type").setValue("true_false");
        if(mRadioButtonTrue.isChecked()){
            questionReference.child("correct_answer").setValue("true");
        }
        if(mRadioButtonFalse.isChecked()) {
            questionReference.child("correct_answer").setValue("false");
        }

        emptyFields();

        questionCounter++;
    }

    private void emptyFields() {
        mEditTextQuestion.setText("");
    }

    public void btnGoBackFromTFAOnClick(View view) {
        Intent intent = new Intent(AddTrueFalseActivity.this, AddQuizActivity.class);
        intent.putExtra(QUESTION_COUNTER, questionCounter);
        intent.putExtra(UNIQUE_ID, uniqueID);
        intent.putExtra(TEACHER_UID, teacherUID);
        Log.d("uid", "btnGoBackFromTFAOnClick: teacher uid "+teacherUID);
        startActivity(intent);
    }
}
