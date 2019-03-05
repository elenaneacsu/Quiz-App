package com.elenaneacsu.faza1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class PreviewActivity extends AppCompatActivity implements PreviewAdapter.ItemClickListener{
    private static final String TAG = "PreviewActivity";

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        initView();
    }

    private void initView() {
        Bundle extras = getIntent().getExtras();
        ArrayList<SharedResources> res = extras.getParcelableArrayList("preview");

        mRecyclerView = findViewById(R.id.recyler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        Log.d(TAG, "initView: "+res.size());
//        for(int i=0;i<res.size();i++) {
//            Log.d(TAG, "initView: "+ res.get(i).getAuthor());
//        }
        PreviewAdapter adapter = new PreviewAdapter(res, getApplicationContext());
        adapter.setItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, SharedResources resources) {
        Intent intent = new Intent(getApplicationContext(), QuestionsActivity.class);
        intent.putParcelableArrayListExtra("question", resources.getQuestions());
        startActivity(intent);
    }
}
