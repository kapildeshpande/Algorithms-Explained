package com.kapil.kapil.algosexplained.Home;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.kapil.kapil.algosexplained.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList <String> arrayList = new ArrayList<>(Arrays.asList("Selection Sort","Bubble Sort","Insertion Sort","Merge Sort","Heap Sort","More are coming soon.."));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initRecyclerView();
    }

    private void initToolbar () {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    // Collapsed
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                } else if (verticalOffset == 0) {
                    // Expanded
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                } else {
                    // Somewhere in between
                    getSupportActionBar().setDisplayShowTitleEnabled(false);
                }
            }
        });
    }

    private void initRecyclerView () {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sectioned_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        HomePageAdapter homePageAdapter = new HomePageAdapter(arrayList,this);
        recyclerView.setAdapter(homePageAdapter);
    }

}