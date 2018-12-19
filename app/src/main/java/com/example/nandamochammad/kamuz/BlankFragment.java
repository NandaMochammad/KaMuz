package com.example.nandamochammad.kamuz;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nandamochammad.kamuz.DB.KamusHelper;
import com.example.nandamochammad.kamuz.adapter.SearchAdapter;
import com.example.nandamochammad.kamuz.adapter.itemCallBack;
import com.example.nandamochammad.kamuz.model.KamusModel;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */

public class BlankFragment extends Fragment implements MaterialSearchBar.OnSearchActionListener{

    Context context;

    private KamusHelper kamusHelper;
    private SearchAdapter adapter;

    MaterialSearchBar search_bar;

    RecyclerView recycler_view;


    private ArrayList<KamusModel> list = new ArrayList<>();
    private boolean isEnglish = true;
    public String a = "";

    public static BlankFragment newInstance(String translate) {
        BlankFragment f = new BlankFragment();

        Bundle args = new Bundle();
        args.putString("someInt", translate);
        f.setArguments(args);

        if (translate.equals("true")){
            boolean isEnglish = true;
            Log.d(TAG, "BlankFragment: isEnglish bernilai "+translate );
        }else if(translate.equals("false")){
            boolean isEnglish = false;
            Log.d(TAG, "BlankFragment: isEnglish bernilai "+translate );
        }

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment, container, false);

        context = getContext();
        kamusHelper = new KamusHelper(context);
        search_bar = v.findViewById(R.id.search_bar);
        search_bar.setOnSearchActionListener(this);
        recycler_view  = v.findViewById(R.id.recycler_view);

        setupList();
        loadData();

        return v;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        loadData(String.valueOf(text));
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

    private void setupList() {
        adapter = new SearchAdapter();
        adapter.setCallBack(new itemCallBack() {
            @Override
            public void onItemClick(int position, View view) {
                KamusModel model = adapter.getItem(position);
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.ITEM_WORD, model.getKata());
                intent.putExtra(DetailActivity.ITEM_TRANSLATE, model.getTerjemah());
                view.getContext().startActivity(intent);
            }
        });
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        recycler_view.setAdapter(adapter);
    }

    private void loadData(String search) {
        try {
            kamusHelper.open();
            if (search.isEmpty()) {
                list = kamusHelper.getAllData(isEnglish);
                Log.d(TAG, "loadData: isEnglish bernilai"+String.valueOf(isEnglish));
            } else {
                list = kamusHelper.getDataByName(search, isEnglish);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            kamusHelper.close();
        }
//        Log.d("masuk", "loadData: "+list.get(1).getKata());
        adapter.replaceAll(list);
    }

    private void loadData() {
        loadData("");
    }


}
