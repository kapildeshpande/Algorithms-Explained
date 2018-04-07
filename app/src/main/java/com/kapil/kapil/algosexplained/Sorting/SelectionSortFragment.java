package com.kapil.kapil.algosexplained.Sorting;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kapil.kapil.algosexplained.R;

import java.util.Stack;

/**
 * Created by kapil on 24-03-2018.
 */

public class SelectionSortFragment extends GenericSortFragment {

    private int mini_index = 0;
    private boolean flag = false;
    protected Stack<Holder> stack = new Stack();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        descriptionText[0].setText("^");
        return v;
    }

    /* OnClick Buttons block start*/

    @Override
    public void onClickReset(View v) {
        stack.clear();
        for (int i=0;i<size;i++) {
            textView[i].setText(getRandomNumber());
            setColorBlank(i);
            descriptionText[i].setText("");
        }
        mini_index = 0;
        index = 0;
        flag = false;
        setInitialText();
        start.setText("START");
        Toast.makeText(getContext(),"Reset successfully",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickPrev (View v) {
        if (stack.empty()) {
            index = 0;
            mini_index = 0;
            return;
        }
        Holder holder = stack.peek();
        stack.pop();

        if (flag) {
            //performMinStep
            index--;
            mini_index = holder.getMin_index();
            flag = false;
        } else {
            //performSwappingStep
            flag = true;
        }
        String temp[] = holder.getTextView();
        Drawable color[] = holder.getColor();
        for (int i=0;i<size;i++) {
            textView[i].setText(temp[i]);
            textView[i].setBackground(color[i]);
        }
        temp = holder.getDescriptionText();
        for (int i=0;i<size;i++) {
            descriptionText[i].setText(temp[i]);
        }
        text.setText(holder.getText());
    }

    @Override
    public void onClickStart (View v) {
        if (index >= size || index < 0)
            return;

        start.setText("Next");
        stack.push(new Holder(textView,descriptionText,text,mini_index));

        if (!flag) {
            performMinStep();
        } else {
            performSwappingStep();
        }
    }

    /* OnClick Buttons block ends*/

    @Override
    void setInitialText() {
        text.setText(R.string.selection_sort);
    }

    private void setTextFindMinStep () {
        String minText = (textView[mini_index].getText()).toString();
        text.setText( minText + " is smallest from index " + Integer.toString(index) + " to 5");
    }

    private void setTextSwapStep () {
        String minText = (textView[mini_index].getText()).toString();
        String indexText = (textView[index].getText()).toString();
        text.setText("Swaping " + minText + " with " + indexText);
    }

    private void performMinStep() {
        mini_index = getMinIndex(index);
        setColorYellow(mini_index);
        descriptionText[mini_index].setText("min");
        setTextFindMinStep();
        flag = true;
    }

    private void performSwappingStep () {
        descriptionText[mini_index].setText("");
        if (index != 0) descriptionText[index-1].setText("");
        descriptionText[index].setText("^");

        int num2 = getIntAtPos(index);
        int num1 = getIntAtPos(mini_index);

        setColorBlue(index);
        textView[index].setText(String.valueOf(num1));
        textView[mini_index].setText(String.valueOf(num2));

        if (mini_index != index)
            setColorBlank(mini_index);

        setTextSwapStep();

        flag = false;
        index++;
    }

    private int getMinIndex(int i) {
        int mini = Integer.MAX_VALUE,mini_index = i-1;

        for (int j = i; j < size; j++) {
            int num = getIntAtPos(j);
            if (mini > num) {
                mini = num;
                mini_index = j;
            }
        }
        return mini_index;
    }

    private class Holder {
        private String textView[];
        private Drawable color[];
        private String descriptionText[];
        private String text;
        private int min_index;

        public String[] getTextView() {
            return textView;
        }

        public String[] getDescriptionText() {
            return descriptionText;
        }

        public String getText() {
            return text;
        }

        public Drawable[] getColor() {
            return color;
        }

        public int getMin_index() {
            return min_index;
        }

        public Holder (TextView textView[], TextView descriptionText[], TextView text, int min_index) {
            this.textView = new String[size];
            this.color = new Drawable[size];
            for (int i=0;i<size;i++) {
                this.textView[i] = textView[i].getText().toString();
                color[i] = textView[i].getBackground();
            }

            this.descriptionText = new String[size];
            for (int i=0;i<size;i++) {
                this.descriptionText[i] = descriptionText[i].getText().toString();
            }
            this.text = text.getText().toString();
            this.min_index = min_index;
        }
    }

}
