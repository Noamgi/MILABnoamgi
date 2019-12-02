package com.example.exercise_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public class Lan extends AppCompatActivity {

    private static String TAG = "lannister";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lan);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_lan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Data> data = fillDataArray();
        recyclerView.setAdapter(new ListAdapterB(data, Lan.this));
    }

    public List<Data> fillDataArray(){
        List<Data> dataArray = new ArrayList<>();
        dataArray.add(new Data(R.drawable.tywin, "Tywin Lannister" ));
        dataArray.add(new Data(R.drawable.jaime, "Jaime Lannister" ));
        dataArray.add(new Data(R.drawable.c, "Cersei Lannister" ));
        dataArray.add(new Data(R.drawable.tyrion, "Tyrion Lannister" ));
        dataArray.add(new Data(R.drawable.joffrey, "Joffrey Lannister" ));
        dataArray.add(new Data(R.drawable.tom, "Tommen Lannister" ));
        dataArray.add(new Data(R.drawable.myrcella, "Myrcella Lannister" ));


        return dataArray;
    }
}
