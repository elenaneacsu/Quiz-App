package com.elenaneacsu.faza1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerViewQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        initView();
    }

    private void initView() {
        Bundle extras = getIntent().getExtras();
        ArrayList<Question> res = extras.getParcelableArrayList("question");

        mRecyclerViewQuestions = findViewById(R.id.recyclerview_questions);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewQuestions.setLayoutManager(layoutManager);

        QuestionAdapter adapter = new QuestionAdapter(res);
        mRecyclerViewQuestions.setAdapter(adapter);

    }
}
