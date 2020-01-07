package com.example.exercise_02;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class ListAdapterB extends RecyclerView.Adapter {
    private List<Data> Data;;
    private Context context;

    public ListAdapterB(List<Data> data, Context context) {
        this.context = context;
        Data = data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtView;
        private ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            txtView =(TextView) view.findViewById(R.id.character);
            imageView=(ImageView) view.findViewById(R.id.image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_menu, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((MyViewHolder)holder).imageView.setImageResource(Data.get(position).imageId);
        ((MyViewHolder)holder).txtView.setText(Data.get(position).txt);

    }

    @Override
    public int getItemCount() {
        return Data.size();
    }
}