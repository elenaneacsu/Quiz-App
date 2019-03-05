package com.elenaneacsu.faza1;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherProfileActivity extends AppCompatActivity {

    Switch mSwitch;
    EditText mEditTextName;
    EditText mEditTextSurname;
    EditText mEditTextSubject;
    Button mButtonAddSubject;

    private String mTeacherUID;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            mTeacherUID = bundle.getString(TeacherMainActivity.TEACHER_UID);
        }

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        initView();
        setName();
        setSurname();
        setSubject();
    }

    public void initView() {
        mSwitch = findViewById(R.id.switch_teacher);
        mEditTextName = findViewById(R.id.edittext_name);
        mEditTextSurname = findViewById(R.id.edittext_surname);
        mEditTextSubject = findViewById(R.id.edittext_subject);
        mButtonAddSubject = findViewById(R.id.btn_addsubject);
        mSwitch.setChecked(false);
        disable();

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEditTextName.setEnabled(true);
                    mEditTextSurname.setEnabled(true);
                    mEditTextSubject.setEnabled(true);
                    mButtonAddSubject.setEnabled(true);
                } else {
                    disable();
                }
            }
        });
    }

    public void disable() {
        mEditTextName.setEnabled(false);
        mEditTextSurname.setEnabled(false);
        mEditTextSubject.setEnabled(false);
        mButtonAddSubject.setEnabled(false);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setName() {
        mDatabaseReference.child("USERS").child(mTeacherUID)
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
        mDatabaseReference.child("USERS").child(mTeacherUID)
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

    private void setSubject() {
        mDatabaseReference.child("USERS").child(mTeacherUID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child("subject").getValue()!=null) {
                            mEditTextSubject.setText(dataSnapshot.child("subject").getValue().toString());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private void saveTeacher() {
        mDatabaseReference.child("USERS").child(mTeacherUID).child("name").setValue(mEditTextName.getText().toString());
        mDatabaseReference.child("USERS").child(mTeacherUID).child("surname").setValue(mEditTextSurname.getText().toString());
        mDatabaseReference.child("USERS").child(mTeacherUID).child("subject").setValue(mEditTextSubject.getText().toString());
    }

    public void addNewSubject(View view) {
        LinearLayout parent = findViewById(R.id.teacher_profile_layout);
        LinearLayout child = new LinearLayout(this);

        child.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        child.setOrientation(LinearLayout.HORIZONTAL);

        parent.addView(child);

        TextView textView = new TextView(this);
        EditText editText = new EditText(this);

        textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        editText.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2));
        textView.setText(R.string.subject);
        editText.setHint(R.string.subject);

        child.addView(textView);
        child.addView(editText);
    }

    public void btnSaveTeacherOnClick(View view) {
        saveTeacher();
        Toast.makeText(getApplicationContext(), getString(R.string.success), Toast.LENGTH_LONG).show();
    }
}
