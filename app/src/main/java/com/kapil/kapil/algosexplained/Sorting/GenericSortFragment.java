package com.kapil.kapil.algosexplained.Sorting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kapil.kapil.algosexplained.R;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class GenericSortFragment extends Fragment {

    protected static final int size = 6;
    protected TextView textView[];
    protected TextView descriptionText[];
    protected TextView text;
    protected Random rand = new Random();
    protected int index = 0;
    protected Button start;


    public GenericSortFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_generic_sort,container,false);
        textView = new TextView[6];
        descriptionText = new TextView[6];
        initTextViews(v);
        initButtons(v);
        return v;
    }

    protected void initButtons (View v) {

        start = (Button) v.findViewById(R.id.button1);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStart(v);
            }
        });
        Button prev = (Button) v.findViewById(R.id.button2);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPrev(v);
            }
        });
        Button reset = (Button) v.findViewById(R.id.button3);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickReset(v);
            }
        });

    }

    protected int getIntAtPos (int pos) {
        return Integer.parseInt(textView[pos].getText().toString());
    }

    abstract void onClickStart (View v);

    abstract void onClickPrev (View v);

    abstract void onClickReset (View v);

    abstract void setInitialText ();

    protected void initTextViews (View view) {
        for (int i=0;i<size;i++) {
            textView[i] = view.findViewById(getId(i));
            textView[i].setText(getRandomNumber());
        }

        for (int i=0;i<size;i++) {
            descriptionText[i] = view.findViewById(getDescriptionId(i));
        }

        text = view.findViewById(R.id.textView);
        setInitialText();
    }

    protected String getRandomNumber() {
        return Integer.toString(rand.nextInt(100));
    }

    protected int getId (int index) {
        switch (index) {
            case 0:
                return R.id.zero;
            case 1:
                return R.id.one;
            case 2:
                return R.id.two;
            case 3:
                return R.id.three;
            case 4:
                return R.id.four;
            case 5:
                return R.id.five;
        }
        return -1;
    }

    protected int getDescriptionId (int index) {
        switch (index) {
            case 0:
                return R.id.zero_name;
            case 1:
                return R.id.one_name;
            case 2:
                return R.id.two_name;
            case 3:
                return R.id.three_name;
            case 4:
                return R.id.four_name;
            case 5:
                return R.id.five_name;
        }
        return -1;
    }


    protected void setColorBlank (int i) {
        textView[i].setBackground(getResources().getDrawable(R.drawable.black_background_surround));
    }

    protected void setColorBlue (int i) {
        textView[i].setBackground(getResources().getDrawable(R.drawable.black_background_surround_blue));
    }

    protected void setColorYellow (int i) {
        textView[i].setBackground(getResources().getDrawable(R.drawable.black_background_surround_yellow));
    }

}
