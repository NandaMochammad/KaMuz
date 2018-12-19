package com.example.nandamochammad.kamuz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String ITEM_WORD = "ITEM_WORD";
    public static final String ITEM_TRANSLATE = "ITEM_TRANSLATE";

    TextView tv_cariKata;

    TextView tv_terjemah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        tv_cariKata = findViewById(R.id.tv_word);
        tv_terjemah = findViewById(R.id.tv_translate);

        Log.d("masuk", "onCreate: "+getIntent().getStringExtra(ITEM_WORD));
        tv_cariKata.setText(getIntent().getStringExtra(ITEM_WORD));
        tv_terjemah.setText(getIntent().getStringExtra(ITEM_TRANSLATE));

    }
}
