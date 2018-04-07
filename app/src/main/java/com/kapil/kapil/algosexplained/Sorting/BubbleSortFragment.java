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

public class BubbleSortFragment extends GenericSortFragment {

    private int currIndex = 0;
    private Stack <Holder> stack = new Stack<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    void onClickReset(View v) {
        currIndex = 0;
        for (int i=0;i<size;i++) {
            textView[i].setText(getRandomNumber());
            setColorBlank(i);
            descriptionText[i].setText("");
        }
        start.setText("Start");
        index = 0;
        setInitialText();
        Toast.makeText(getContext(),"Reset Successfully",Toast.LENGTH_SHORT).show();
    }

    @Override
    void onClickStart(View v) {

        start.setText("Next");

        if (index >= size) {
            return;
        }

        stack.push(new Holder(textView,descriptionText,text,index,currIndex));

        if (currIndex < size-index-1) {

            int num1 = Integer.parseInt(textView[currIndex].getText().toString());
            int num2 = Integer.parseInt(textView[currIndex+1].getText().toString());

            if (currIndex != 0) setColorBlank(currIndex-1);
            setColorYellow(currIndex);
            setColorYellow(currIndex+1);

            if (num1 > num2) {
                //swap
                setInfoText("Comparing " + String.valueOf(num1) + " & " + String.valueOf(num2) +
                                "\n Since, " + String.valueOf(num2) + " is smaller therefore swapping "
                                + String.valueOf(num1) + " & " + String.valueOf(num2));
                textView[currIndex].setText(Integer.toString(num2));
                textView[currIndex+1].setText(Integer.toString(num1));
            } else {
                setInfoText("Comparing " + String.valueOf(num1) +  " & " + String.valueOf(num2));
            }

            currIndex++;

        } else {
            setInfoText("Array is Sorted from " + String.valueOf(currIndex) + " index to " + String.valueOf(size-1) + " index");
            if (index == size - 1) {
                setColorBlue(currIndex);
                descriptionText[currIndex].setText("^");
                descriptionText[currIndex+1].setText("");
                index++;
                return;
            }
            setColorBlue(currIndex);
            if (currIndex+1 < size)
                descriptionText[currIndex+1].setText("");
            descriptionText[currIndex].setText("^");
            setColorBlank(currIndex-1);
            currIndex = 0;
            index++;
        }
    }

    private void setInfoText (String s) {
        text.setText(s);
    }

    @Override
    void onClickPrev(View v) {

        if (!stack.empty()) {
            String temp[] = stack.peek().getTextView();
            Drawable color[] = stack.peek().getColor();
            for (int i=0;i<size;i++) {
                textView[i].setText(temp[i]);
                textView[i].setBackground(color[i]);
            }
            temp = stack.peek().getDescriptionText();
            for (int i=0;i<size;i++) {
                descriptionText[i].setText(temp[i]);
            }
            setInfoText(stack.peek().getText());
            index = stack.peek().getIndex();
            currIndex = stack.peek().getCurrIndex();
            stack.pop();
        }

    }

    @Override
    void setInitialText() {
        text.setText(R.string.bubble_sort);
    }

    private class Holder {
        private String textView[];
        private Drawable color[];
        private String descriptionText[];
        private String text;
        private int currIndex,index;

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

        public int getIndex() { return index; }

        public int getCurrIndex() { return currIndex; }


        public Holder (TextView textView[], TextView descriptionText[], TextView text, int index, int currIndex) {
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
            this.index = index;
            this.currIndex = currIndex;
        }
    }
}
