package com.kapil.kapil.algosexplained.Sorting;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.kapil.kapil.algosexplained.R;

public class SortActivity extends AppCompatActivity {

    public static String fragmentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sorting_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentName = getIntent().getStringExtra("fragmentName");
        getSupportActionBar().setTitle(fragmentName);

        SectionPagerAdapter pagerAdapter = new
                SectionPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    private class SectionPagerAdapter extends FragmentPagerAdapter {

        public SectionPagerAdapter (FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount () {
            return 2;
        }

        @Override
        public Fragment getItem (int position) {
            if (position == 0) {

                switch (fragmentName) {
                    case "Selection Sort":
                        return new SelectionSortFragment();

                    case "Bubble Sort":
                        return new BubbleSortFragment();

                    case "Insertion Sort":
                        return new InsertionSortFragment();

                    case "Merge Sort":
                        return new MergeSortFragment();
                    case "Heap Sort":
                        return new HeapSortFragment();
                }
            } else if (position == 1) {
                return new CodeFragment();
            }
            return  null;
        }

        //for name of each tab
        @Override
        public CharSequence getPageTitle (int position) {
            switch (position) {
                case 0:
                    return "Animation";
                case 1:
                    return "Code";
            }
            return null;
        }
    }


}