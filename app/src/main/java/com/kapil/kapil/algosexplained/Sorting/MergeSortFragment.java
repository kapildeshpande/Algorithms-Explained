package com.kapil.kapil.algosexplained.Sorting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kapil.kapil.algosexplained.R;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class MergeSortFragment extends Fragment {

    private TextView textView[][];
    private TextView descriptionText[][];
    private TextView text;
    private static final int size = 6;
    private Random rand = new Random();
    private Button start;
    private int left = 0, right = 0, depth = 0, parent_index = 0, child_left, child_right;
    private boolean flag = false;
    private Queue<Holder> queue = new LinkedList<>();

    public MergeSortFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_merg_sort, container, false);
        initTextViews(v);
        initButtons(v);
        //queue.add(new Holder(0,size-1,0));
        mergSort(0,size-1,0);
        return v;
    }

    private void mergSort (int left,int right,int depth) {
        if (left < right) {
            int mid = (left + right)/2;
            queue.add(new Holder(left,mid,depth+1));
            mergSort(left,mid,depth+1);
            queue.add(new Holder(mid+1,right,depth+1));
            mergSort(mid+1,right,depth+1);
            //merge step
            queue.add(new Holder(left,mid+1,-1));//depth = -1 to check if it is merg step
        }
    }

    private void initButtons (View v) {
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

    private void onClickStart (View v) {

        start.setText("Next");
        if (queue.isEmpty())
            return;

        if (flag) {
            merge();
            return;
        }

        if (queue.peek().depth != -1) {
            left = queue.peek().left;
            right = queue.peek().right;
            depth = queue.peek().depth;
            queue.remove();
            if (left == 1 || left == 2 || left == 3 || left == 4 || left == 5) {
                setInfoText("Dividing right part of array from " + String.valueOf(left) + " to " + String.valueOf(right));
            } else {
                setInfoText("Dividing left part of array from " + String.valueOf(left) + " to " + String.valueOf(right));
            }
            for (int i = left; i <= right; i++) {
                textView[depth][i].setVisibility(View.VISIBLE);
            }
        } else {
            setInfoText("Merging left and right part of array");
            child_left = queue.peek().left;
            child_right = queue.peek().right;
            parent_index = child_left;

            descriptionText[depth][child_right].setText("^");
            descriptionText[depth][child_left].setText("^");
            descriptionText[depth-1][parent_index].setText("^");
            setColorYellow(child_left,depth);
            setColorYellow(child_right,depth);

            flag = true;

        }
    }

    private void merge () {

        int num1,num2;
        if (child_left >= queue.peek().right && (child_right >= size || textView[depth][child_right].getVisibility() == View.INVISIBLE) ) {
            if (depth > 0) {
                for (int i = 0; i < size; i++) {
                    textView[depth][i].setVisibility(View.INVISIBLE);
                    descriptionText[depth][i].setText("");
                    setColorBlank(i,depth);
                    if (depth > 1) setColorBlank(i,depth-1);
                }
                depth--;
            }
            if (depth == 0) {
                setInfoText("Array is Sorted!!");
            }
            flag = false;
            queue.remove();
            return;
        } else if (child_left >= queue.peek().right) {
            num1 = Integer.MAX_VALUE;
            num2 = getIntAtPos(child_right, depth);
        } else if (child_right >= size || textView[depth][child_right].getVisibility() == View.INVISIBLE) {
            num2 = Integer.MAX_VALUE;
            num1 = getIntAtPos(child_left, depth);
        } else {
            num1 = getIntAtPos(child_left, depth);
            num2 = getIntAtPos(child_right, depth);
        }

        if (num1 > num2) {
            textView[depth-1][parent_index].setText(textView[depth][child_right].getText());
            parent_index++;
            child_right++;

            setInfoText("Since, " + String.valueOf(num2) + " is smaller therefore inserting it to " + String.valueOf(parent_index-1) + " index");
            if ((child_right < size && textView[depth][child_right].getVisibility() == View.VISIBLE)) {
                descriptionText[depth][child_right].setText("^");
                setColorYellow(child_right,depth);
            }
            descriptionText[depth][child_right - 1].setText("");
            setColorBlank(child_right-1,depth);

            if (parent_index < size && textView[depth-1][parent_index].getVisibility() == View.VISIBLE) {
                descriptionText[depth - 1][parent_index].setText("^");
            }
            descriptionText[depth-1][parent_index-1].setText("");
            setColorBlue(parent_index-1,depth-1);
        } else {
            textView[depth-1][parent_index].setText(textView[depth][child_left].getText());
            parent_index++;
            child_left++;

            setInfoText("Since, " + String.valueOf(num1) + " is smaller therefore inserting it to " + String.valueOf(parent_index-1) + " index");
            if (child_left < queue.peek().right) {
                descriptionText[depth][child_left].setText("^");
                setColorYellow(child_left,depth);
            }
            descriptionText[depth][child_left-1].setText("");
            setColorBlank(child_left-1,depth);

            if (parent_index < size && textView[depth-1][parent_index].getVisibility() == View.VISIBLE) {
                descriptionText[depth - 1][parent_index].setText("^");
            }
            descriptionText[depth-1][parent_index-1].setText("");
            setColorBlue(parent_index-1,depth-1);
        }
    }

    protected int getIntAtPos (int pos,int d) {
        return Integer.parseInt(textView[d][pos].getText().toString());
    }


    private void onClickReset (View v) {

        queue.clear();
        mergSort(0,size-1,0);

        for (int i=0;i<4;i++) {
            for (int j=0;j<size;j++) {
                if (i == 0) {
                    textView[i][j].setText(getRandomNum());
                } else {
                    textView[i][j].setText(textView[i-1][j].getText());
                    textView[i][j].setVisibility(View.INVISIBLE);
                }
                descriptionText[i][j].setText("");
                setColorBlank(j,i);
            }
        }

        left = right = depth = parent_index = 0;
        flag = false;

        start.setText("Start");
        setInitialText();
        Toast.makeText(getContext(),"Reset Successfully",Toast.LENGTH_SHORT).show();

    }

    private void initTextViews (View v) {
        textView = new TextView[4][6];
        descriptionText = new TextView[4][6];

        int id1[][] = { {R.id.zero,R.id.one,R.id.two,R.id.three,R.id.four,R.id.five},
                        {R.id.one_zero,R.id.one_one,R.id.one_two,R.id.one_three,R.id.one_four,R.id.one_five},
                        {R.id.two_zero,R.id.two_one,R.id.two_two,R.id.two_three,R.id.two_four,R.id.two_five},
                        {R.id.three_zero,R.id.three_one,R.id.three_two,R.id.three_three,R.id.three_four,R.id.three_five}};

        int id2[][] = { {R.id.zero_desc,R.id.one_desc,R.id.two_desc,R.id.three_desc,R.id.four_desc,R.id.five_desc},
                        {R.id.one_zero_desc,R.id.one_one_desc,R.id.one_two_desc,R.id.one_three_desc,R.id.one_four_desc,R.id.one_five_desc},
                        {R.id.two_zero_desc,R.id.two_one_desc,R.id.two_two_desc,R.id.two_three_desc,R.id.two_four_desc,R.id.two_five_desc},
                        {R.id.three_zero_desc,R.id.three_one_desc,R.id.three_two_desc,R.id.three_three_desc,R.id.three_four_desc,R.id.three_five_desc}};

        for (int i=0;i<4;i++) {
            for (int j=0;j<size;j++) {
                textView[i][j] = (TextView) v.findViewById(id1[i][j]);
                descriptionText[i][j] = (TextView) v.findViewById(id2[i][j]);
                if (i == 0) {
                    textView[i][j].setText(getRandomNum());
                } else {
                    textView[i][j].setText(textView[i-1][j].getText());
                }
            }
        }

        text = v.findViewById(R.id.textView);
        setInitialText();
    }

    private void setInitialText () {
        text.setText(R.string.merge_sort);
    }

    private String getRandomNum () {
         return String.valueOf(rand.nextInt(100));
    }

    protected void setColorBlank (int i,int d) {
        textView[d][i].setBackground(getResources().getDrawable(R.drawable.black_background_surround));
    }

    protected void setColorBlue (int i,int d) {
        textView[d][i].setBackground(getResources().getDrawable(R.drawable.black_background_surround_blue));
    }

    protected void setColorYellow (int i,int d) {
        textView[d][i].setBackground(getResources().getDrawable(R.drawable.black_background_surround_yellow));
    }

    private void setInfoText (String s) {
        text.setText(s);
    }

    private class Holder {
        public int left,right,depth;

        public Holder (int left,int right,int depth) {
            this.left = left;
            this.right = right;
            this.depth = depth;
        }

    }

}
