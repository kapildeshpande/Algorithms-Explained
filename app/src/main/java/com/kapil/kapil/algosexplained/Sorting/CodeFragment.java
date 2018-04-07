package com.kapil.kapil.algosexplained.Sorting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kapil.kapil.algosexplained.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CodeFragment extends Fragment {


    public CodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_code, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.code_image);
        switch (SortActivity.fragmentName) {
            case "Selection Sort":
                imageView.setImageResource(R.drawable.selectionsort);
                break;
            case "Bubble Sort":
                imageView.setImageResource(R.drawable.bubblesort);
                break;
            case "Insertion Sort":
                imageView.setImageResource(R.drawable.insertionsort);
                break;
            case "Merge Sort":
                imageView.setImageResource(R.drawable.mergesort);
                break;
            case "Heap Sort":
                imageView.setImageResource(R.drawable.heapsort);
                break;
        }
        return v;
    }

}
