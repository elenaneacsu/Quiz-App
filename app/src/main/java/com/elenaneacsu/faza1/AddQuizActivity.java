package com.elenaneacsu.faza1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class AddQuizActivity extends AppCompatActivity {
    public static final String QUESTION_COUNTER = "0";
    public static final String UNIQUE_ID = "unique_id";
    public static final String TEACHER_UID = "teacher_uid";


    private int questionCounter = 0;
    private String uniqueID;
    private String teacherUID;
    private long no_tests;

    private TextView mTextViewTestId;
    private EditText mEditTextTitle;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            teacherUID = bundle.getString(TEACHER_UID);
            questionCounter = bundle.getInt(QUESTION_COUNTER);
            uniqueID = bundle.getString(UNIQUE_ID);
        }
        if (uniqueID == null) {
            setUniqueID();
        }

        mTextViewTestId = findViewById(R.id.textview_testid);
        mTextViewTestId.setText(uniqueID);

        mEditTextTitle = findViewById(R.id.edittext_testtitle);

        Log.d("uid", "onCreate: teacher uid "+teacherUID);

    }

    private void setUniqueID() {
        uniqueID = UUID.randomUUID().toString();
    }

    public void btnAddMultipleChoiceQuestionOnClick(View view) {
        Intent intent = new Intent(AddQuizActivity.this, AddMultipleChoiceActivity.class);
        intent.putExtra(QUESTION_COUNTER, questionCounter);
        intent.putExtra(UNIQUE_ID, uniqueID);
        intent.putExtra(TEACHER_UID, teacherUID);
        Log.d("uid", "btnAddMultipleChoiceQuestionOnClick: teacher uid "+teacherUID);
        startActivity(intent);
    }

    public void btnAddTrueFalseQuestionOnClick(View view) {
        Intent intent = new Intent(AddQuizActivity.this, AddTrueFalseActivity.class);
        intent.putExtra(QUESTION_COUNTER, questionCounter);
        intent.putExtra(UNIQUE_ID, uniqueID);
        intent.putExtra(TEACHER_UID, teacherUID);
        Log.d("uid", "btnAddTrueFalseQuestionOnClick: teacher uid "+teacherUID);
        startActivity(intent);
    }

    public void btnSaveTestOnClick(View view) {
        mDatabaseReference.child("TESTS").child(uniqueID).child("title").setValue(mEditTextTitle.getText().toString());
        Toast.makeText(getApplicationContext(), "Test saved!", Toast.LENGTH_LONG).show();
        getNumberOfTests();
        mDatabaseReference.child("USERS").child(teacherUID).child("tests").child(String.valueOf(no_tests)).setValue(uniqueID);
        no_tests++;
        mDatabaseReference.child("USERS").child(teacherUID).child("no_tests").setValue(no_tests);
        questionCounter = 0;
        mEditTextTitle.setText("");
    }

    private void getNumberOfTests() {
        mDatabaseReference.child("USERS").child(teacherUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        no_tests = (long) dataSnapshot.child("no_tests").getValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
