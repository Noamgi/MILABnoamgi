package com.example.exercise_02;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class Stark extends AppCompatActivity {
    private static String TAG = "stark";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stark);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_stark);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<Data> data = fillDataArray();
        recyclerView.setAdapter(new ListAdapterB(data, Stark.this));
    }

    public List<Data> fillDataArray(){
        List<Data> dataArray = new ArrayList<>();
        dataArray.add(new Data(R.drawable.aryaa, "Arya Stark" ));
        dataArray.add(new Data(R.drawable.bran, "Bran Stark" ));
        dataArray.add(new Data(R.drawable.catelyn, "Catelyn Stark" ));
        dataArray.add(new Data(R.drawable.ned, "Ned Stark" ));
        dataArray.add(new Data(R.drawable.rob, "Rob Stark" ));
        dataArray.add(new Data(R.drawable.rickon, "Rickon Stark" ));
        dataArray.add(new Data(R.drawable.sansa, "Sansa Stark" ));

        return dataArray;
    }

}
