package com.elenaneacsu.faza1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initView();
    }

    public void initView() {
        mListView = findViewById(R.id.listview_info);
        ArrayList<Information> info = getIntent().getParcelableArrayListExtra("info");
        InformationAdapter infoAdapter = new InformationAdapter(this, R.layout.fact_item, info);
        mListView.setAdapter(infoAdapter);
    }

}
