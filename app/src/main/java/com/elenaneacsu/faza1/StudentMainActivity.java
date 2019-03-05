package com.elenaneacsu.faza1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class StudentMainActivity extends AppCompatActivity {
    private static final String TAG = "StudentMainActivity";
    public static final String STUDENT_UID = "student uid";
    public static final String ROOM_NUMBER = "room number";

    private String studentUID;

    private EditText mEditTextRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            studentUID = bundle.getString(LogInActivity.UID);
        }

        mEditTextRoom = findViewById(R.id.edittext_room);
    }

    public void viewStudentProfile(View view) {
        Intent intent = new Intent(this, StudentProfileActivity.class);
        intent.putExtra(STUDENT_UID, studentUID);
        startActivity(intent);
    }

    public void didYouKnow(View view) {
        GetJSONInfo obj = new GetJSONInfo(){
            @Override
            protected void onPostExecute(List<Information> information) {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putExtra("info", (ArrayList<Information>)information);
                startActivity(intent);
            }
        };
        obj.execute("https://api.myjson.com/bins/hj66e");
    }

    public void btnJoinTestOnClick(View view) {
        Intent intent = new Intent(StudentMainActivity.this, QuizActivity.class);
        intent.putExtra(ROOM_NUMBER, mEditTextRoom.getText().toString());
        startActivity(intent);
    }
}
