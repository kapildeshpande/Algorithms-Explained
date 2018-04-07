package com.kapil.kapil.algosexplained.Sorting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kapil.kapil.algosexplained.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeapSortFragment extends GenericSortFragment {

    private TextView[] heapText;
    private TextView[] lines;
    private int flag = 0;

    public HeapSortFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_heap_sort, container, false);
        initButtons(v);
        initTextViews(v);
        index = size - 1;
        return v;
    }

    private void heapify (int n,int i) {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;
        if ( l < n && getIntAtPos(l) > getIntAtPos(largest) )
            largest = l;

        if (r < n && getIntAtPos(r) > getIntAtPos(largest))
            largest = r;

        if (largest != i) {
            //swap
            int temp = getIntAtPos(i);
            textView[i].setText(textView[largest].getText());
            textView[largest].setText(String.valueOf(temp));

            heapText[i].setText(textView[i].getText());
            heapText[largest].setText(textView[largest].getText());

            heapify(n,largest);
        }
    }

    private void builtHeap () {
        for (int i=(size/2)-1;i>=0;i--) {
            heapify(size,i);
        }
    }

    private void setInfoText (String s) {
        text.setText(s);
    }

    @Override
    void onClickStart (View v) {

        start.setText("Next");
        if (index < 0) {
            return;
        } else if (index == 0) {
            setColorBlue(index);
            heapText[index].setVisibility(View.INVISIBLE);
            descriptionText[index].setText("^");
            descriptionText[index+1].setText("");
            setInfoText("Array is now Sorted!!");
            return;
        }

        if (flag == 0) {
            builtHeap();
            setInfoText("Building heap from 0 to " + String.valueOf(index));
            flag = 1;
            descriptionText[0].setText("max");
            setColorYellow(0);
            setColorYellow(index);
            return;
        }
        else if (flag == 1) {
            //swap
            int temp = getIntAtPos(0);
            setInfoText("Swapping " + String.valueOf(temp) + " with " + textView[index].getText());
            descriptionText[0].setText("");
            textView[0].setText(textView[index].getText());
            textView[index].setText(String.valueOf(temp));

            heapText[0].setText(textView[0].getText());
            heapText[index].setText(textView[index].getText());
            heapText[index].setVisibility(View.INVISIBLE);

            lines[index-1].setVisibility(View.INVISIBLE);
            setColorBlue(index);
            if (index + 1 < size) descriptionText[index+1].setText("");
            descriptionText[index].setText("^");
            flag = 2;
        }
        else if (flag == 2) {
            heapify(index, 0);
            flag = 1;
            descriptionText[0].setText("max");
            index--;
            setColorYellow(0);
            setInfoText("Building heap from 0 to " + String.valueOf(index));
            if (index >= 0) setColorYellow(index);
        }
    }

    @Override
    void onClickReset (View v) {
        index = size - 1;
        flag = 0;
        for (int i=0;i<size;i++) {
            textView[i].setText(getRandomNumber());
            setColorBlank(i);
            heapText[i].setText(textView[i].getText());
            heapText[i].setVisibility(View.VISIBLE);
            descriptionText[i].setText("");
            if (i < size - 1) {
                lines[i].setVisibility(View.VISIBLE);
            }
        }
        start.setText("Start");
        setInitialText();
        Toast.makeText(getContext(),"Reset Successfully",Toast.LENGTH_SHORT).show();
    }

    @Override
    void onClickPrev (View v) {}

    @Override
    protected void initButtons (View v) {
        start = (Button) v.findViewById(R.id.button1);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStart(v);
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

    protected void initTextViews(View view) {
        textView = new TextView[size];
        descriptionText = new TextView[size];
        heapText = new TextView[size];
        lines = new TextView[size-1];

        for (int i=0;i<size;i++) {
            textView[i] = view.findViewById(getId(i));
            heapText[i] = view.findViewById(getHeapId(i));
            textView[i].setText(getRandomNumber());
            heapText[i].setText(textView[i].getText());
            descriptionText[i] = view.findViewById(getDescriptionId(i));
            if (i < size - 1) {
                lines[i] = view.findViewById(getLineId(i));
                if (i % 2 == 1)//using unicode character
                    lines[i].setText(Html.fromHtml("&#92;"));
            }
        }

        text = view.findViewById(R.id.textView);
        setInitialText();
    }

    private int getLineId (int i) {
        switch (i) {
            case 0:
                return R.id.line_zero;
            case 1:
                return R.id.line_one;
            case 2:
                return R.id.line_two;
            case 3:
                return R.id.line_three;
            case 4:
                return R.id.line_four;
        }
        return -1;
    }

    private int getHeapId (int i) {
        switch (i) {
            case 0:
                return R.id.heap_zero;
            case 1:
                return R.id.heap_one;
            case 2:
                return R.id.heap_two;
            case 3:
                return R.id.heap_three;
            case 4:
                return R.id.heap_four;
            case 5:
                return R.id.heap_five;
        }
        return -1;
    }

    @Override
    void setInitialText () {
        text.setText(R.string.heap_sort);
    }

}
