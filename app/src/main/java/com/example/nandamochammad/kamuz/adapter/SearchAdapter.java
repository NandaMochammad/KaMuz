package com.example.nandamochammad.kamuz.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nandamochammad.kamuz.R;
import com.example.nandamochammad.kamuz.model.KamusModel;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    public ArrayList<KamusModel> list = new ArrayList<>();
    private itemCallBack callback;

    public SearchAdapter() {

    }

    public void replaceAll(ArrayList<KamusModel> items){
        list =items;
        notifyDataSetChanged();
    }

    public void setCallBack(itemCallBack callback){
        this.callback=callback;
    }

    public KamusModel getItem(int position){
        return list.get(position);
    }
    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false);
        final SearchViewHolder holder = new SearchViewHolder(view);
        if(callback!=null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onItemClick(holder.getPosition(), view);

                }
            });
        }

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Log.d("masuk", "onBindViewHolder: "+getItem(position).getKata());
        holder.tv_cariKata.setText(getItem(position).getKata());
        holder.tv_terjemah.setText(getItem(position).getTerjemah());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        TextView tv_cariKata;
        TextView tv_terjemah;

        public  SearchViewHolder(@NonNull final View itemView){
            super(itemView);
            tv_cariKata = itemView.findViewById(R.id.tv_main_word);
            tv_terjemah = itemView.findViewById(R.id.tv_main_translate);


        }
    }
}
