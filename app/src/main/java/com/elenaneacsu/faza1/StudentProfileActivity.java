package com.elenaneacsu.faza1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfileActivity extends AppCompatActivity {
    private EditText mEditTextName;
    private EditText mEditTextSurname;
    private EditText mEditTextUniversity;
    private EditText mEditTextFaculty;
    private Switch mSwitch;

    private String mStudentUID;
    private DatabaseReference mDatabaseReference;
    private static final String TAG = "StudentProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mStudentUID = bundle.getString(StudentMainActivity.STUDENT_UID);
            Log.d(TAG, "onCreate: " + mStudentUID);
        } else {
            Log.d(TAG, "onCreate: BUNDLE IS NULL");
        }

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        setName();
        setSurname();
        setUniversity();
        setFaculty();

        Log.d(TAG, "onCreate: "+mStudentUID);
    }

    private void initView() {
        mSwitch = findViewById(R.id.switch_student);
        mEditTextName = findViewById(R.id.edittext_name);
        mEditTextSurname = findViewById(R.id.edittext_surname);
        mEditTextUniversity = findViewById(R.id.edittext_university);
        mEditTextFaculty = findViewById(R.id.edittext_faculty);
        mSwitch.setChecked(false);
        disable();

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEditTextName.setEnabled(true);
                    mEditTextSurname.setEnabled(true);
                    mEditTextUniversity.setEnabled(true);
                    mEditTextFaculty.setEnabled(true);
                } else {
                    disable();
                }
            }
        });
    }

    private void disable() {
        mEditTextName.setEnabled(false);
        mEditTextSurname.setEnabled(false);
        mEditTextUniversity.setEnabled(false);
        mEditTextFaculty.setEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setName() {
        mDatabaseReference.child("USERS").child(mStudentUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mEditTextName.setText(dataSnapshot.child("name").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void setSurname() {
        mDatabaseReference.child("USERS").child(mStudentUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mEditTextSurname.setText(dataSnapshot.child("surname").getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void setUniversity() {
        mDatabaseReference.child("USERS").child(mStudentUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("university").getValue()!=null) {
                            mEditTextUniversity.setText(dataSnapshot.child("university").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void setFaculty() {
        mDatabaseReference.child("USERS").child(mStudentUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("faculty").getValue()!=null) {
                            mEditTextFaculty.setText(dataSnapshot.child("faculty").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void btnSaveOnClick(View view) {
        saveStudent();
        Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG).show();
    }

    private void saveStudent() {
        mDatabaseReference.child("USERS").child(mStudentUID).child("name").setValue(mEditTextName.getText().toString());
        mDatabaseReference.child("USERS").child(mStudentUID).child("surname").setValue(mEditTextSurname.getText().toString());
        mDatabaseReference.child("USERS").child(mStudentUID).child("university").setValue(mEditTextUniversity.getText().toString());
        mDatabaseReference.child("USERS").child(mStudentUID).child("faculty").setValue(mEditTextFaculty.getText().toString());
    }
}
