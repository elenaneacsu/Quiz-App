package com.elenaneacsu.faza1;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TeacherMainActivity extends AppCompatActivity {
    List<Integer>l=new ArrayList<>();

    public static final String TEACHER_UID = "teacher_uid";


    private String mTeacherUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        l.add(10);
        l.add(9);
        l.add(8);
//        l.add(10);
//        l.add(5);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            mTeacherUID = bundle.getString(LogInActivity.UID);
        }
    }

    public void viewTeacherProfile(View view) {
        Intent intent = new Intent(this, TeacherProfileActivity.class);
        intent.putExtra(TEACHER_UID, mTeacherUID);
        startActivity(intent);
    }

    public void planNewActiviy(View view) {
        Intent intent = new Intent(this, PlanActivity.class);
        startActivity(intent);
    }

    public void getResources(View view) {
        GetJSONResources obj = new GetJSONResources(){
            @Override
            protected void onPostExecute(List<SharedResources> res) {
                Intent intent = new Intent(getApplicationContext(), PreviewActivity.class);
                intent.putParcelableArrayListExtra("preview", (ArrayList<? extends Parcelable>) res);
                startActivity(intent);
            }
        };
        obj.execute("https://api.myjson.com/bins/1a3cu6");
    }

    public void btnAddQuizOnClick(View view) {
        Intent intent = new Intent(TeacherMainActivity.this, AddQuizActivity.class);
        intent.putExtra(TEACHER_UID, mTeacherUID);
        Log.d("uid", "btnAddQuizOnClick: teacher uid "+mTeacherUID);
        startActivity(intent);
    }

    public void test(View view) {
        ArrayList<Teste>lista=new ArrayList<>();

       Teste t1=new Teste("Elena Neacsu","Pocatilu Paul","Android",10,"7 feb");
       Teste t2=new Teste("Popescu Alina","Pocatilu Paul","Android",7,"2 ian");
       Teste t3=new Teste("Popescu Madalina","Pocatilu Paul","Android",6,"10 feb");
       Teste t4=new Teste("Popescu Izabela","Pocatilu Paul","Android",9,"13 feb");
        Teste t5=new Teste(" Izabela","Pocatilu Paul","Android",3,"13 feb");
        lista.add(t1);
        lista.add(t2);
        lista.add(t3);
        lista.add(t4);
        lista.add(t5);

        DBAdapter adapter=new DBAdapter(getApplicationContext(),1);
        adapter.openConnection();
        for (Teste test:lista) {
           adapter.insertTest(test);
        }

        adapter.closeConnection();

    }

    public void onDraw(View view) {
        //startActivity(new Intent(TeacherMainActivity.this, Histograma.class));
        Histograma h= new Histograma(this,l);
        setContentView(h);
    }

    public void veziRaport(View view) {
        DBAdapter adapter=new DBAdapter(getApplicationContext(),1);
        adapter.openConnection();
        l=adapter.noteTeste();
        List<Teste> lista=adapter.testePromovate();
        for(int i=0;i<3;i++) {
            Toast.makeText(this, lista.get(i).toString(), Toast.LENGTH_LONG).show();
        }
        adapter.closeConnection();
    }
}
