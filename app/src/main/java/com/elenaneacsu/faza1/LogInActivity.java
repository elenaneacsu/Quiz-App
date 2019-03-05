package com.elenaneacsu.faza1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public static final String UID = "uid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initView();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void initView() {
        mEditTextEmail = findViewById(R.id.edittext_email);
        mEditTextPassword = findViewById(R.id.edittext_password);
    }

    public void btnLoginOnClick(View view) {
        String email = mEditTextEmail.getText().toString();
        String password = mEditTextPassword.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), getString(R.string.insert_email), Toast.LENGTH_LONG).show();
        }

        if (password.isEmpty()) {
            Toast.makeText(getApplicationContext(), R.string.insert_password, Toast.LENGTH_LONG).show();
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), getString(R.string.wrong_credentials), Toast.LENGTH_LONG).show();
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LogInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), getString(R.string.wrong_credentials), Toast.LENGTH_LONG).show();
                        } else {
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            if (currentUser.isEmailVerified()) {
                                getType(currentUser);
                            } else {
                                Toast.makeText(getApplicationContext(), getString(R.string.verify_email), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void getType(FirebaseUser currentUser) {
        databaseReference.child("USERS").child(currentUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long type = (long) dataSnapshot.child("type").getValue();
                        if (type == 1) {
                            Intent intent = new Intent(LogInActivity.this, TeacherMainActivity.class);
                            intent.putExtra(UID, firebaseAuth.getCurrentUser().getUid());
                            startActivity(intent);
                        }
                        if (type == 0) {
                            Intent intent = new Intent(LogInActivity.this, StudentMainActivity.class);
                            intent.putExtra(UID, firebaseAuth.getCurrentUser().getUid());
                            startActivity(intent);
                        }
                        Log.d("hkh", "onDataChange: " + type);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
