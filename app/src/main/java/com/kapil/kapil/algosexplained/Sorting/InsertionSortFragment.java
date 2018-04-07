package com.kapil.kapil.algosexplained.Sorting;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kapil.kapil.algosexplained.R;

import java.util.Stack;

/**
 * Created by kapil on 25-03-2018.
 */

public class InsertionSortFragment extends GenericSortFragment {

    private int cmpPrevIndex = 0;
    private int valAtIndex;
    private TextView start;
    private Stack <Holder> stack = new Stack<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        index = 1;
        start = v.findViewById(R.id.button1);
        valAtIndex = getIntAtPos(index);
        //stack.push(new Holder(textView,descriptionText,text,index,cmpPrevIndex,valAtIndex));
        return v;
    }

    private void setInfoText (String s) {
        text.setText(s);
    }

    @Override
    void onClickStart(View v) {

        if (index >= size) {
            return;
        }

        stack.push(new Holder(textView,descriptionText,text,index,cmpPrevIndex,valAtIndex));

        if (start.getText().equals("Start")) {
            start.setText("Next");
            descriptionText[index+1].setText("^");
            setColorBlue(cmpPrevIndex);
            descriptionText[index].setText(textView[index].getText());
            setInfoText("Creating hole at " + String.valueOf(index) + " index");
            textView[index].setText("");
            setColorYellow(index);
            return;
        }

        Log.i("index ",Integer.toString(index));
        Log.i("cmpPrevIndex ",Integer.toString(cmpPrevIndex));

        if (cmpPrevIndex >= 0 && getIntAtPos(cmpPrevIndex) > valAtIndex) {
            setColorBlue(cmpPrevIndex+1);
            textView[cmpPrevIndex+1].setText(textView[cmpPrevIndex].getText());
            setColorYellow(cmpPrevIndex);
            setInfoText("Hole moves to " + String.valueOf(cmpPrevIndex) + " index " +
                       "Since, " + String.valueOf(valAtIndex) + " is smaller than " + textView[cmpPrevIndex].getText());
            textView[cmpPrevIndex].setText("");
            descriptionText[cmpPrevIndex].setText(descriptionText[cmpPrevIndex+1].getText());

            descriptionText[cmpPrevIndex+1].setText("");

            cmpPrevIndex--;
        }
        else {
            textView[cmpPrevIndex+1].setText(String.valueOf(valAtIndex));
            setColorBlue(cmpPrevIndex+1);
            descriptionText[cmpPrevIndex+1].setText("");
            if (cmpPrevIndex == -1)
                setInfoText("Inserting " + textView[cmpPrevIndex+1].getText() + " to hole");
            else
                setInfoText("Inserting " + textView[cmpPrevIndex+1].getText() + " to hole " +
                    "Since, " + textView[cmpPrevIndex+1].getText() + " is greater than " + String.valueOf(valAtIndex));
            index++;
            if (index < size) {
                descriptionText[index].setText("");
                if (index+1 < size) descriptionText[index+1].setText("^");
                valAtIndex = getIntAtPos(index);
                setColorYellow(index);
                descriptionText[index].setText(String.valueOf(valAtIndex));
                textView[index].setText("");
            } else {
                setInfoText("Array is Sorted!!");
                stack.push(new Holder(textView,descriptionText,text,index,cmpPrevIndex,valAtIndex));
            }
            cmpPrevIndex = index - 1;
            setColorBlue(cmpPrevIndex);
        }
    }

    @Override
    void onClickReset(View v) {
        index = 1;
        valAtIndex = getIntAtPos(index);
        cmpPrevIndex = 0;
        for (int i=0;i<size;i++) {
            textView[i].setText(getRandomNumber());
            setColorBlank(i);
            descriptionText[i].setText("");
        }
        setInitialText();
        start.setText("Start");
        Toast.makeText(getContext(),"Reset Successfully",Toast.LENGTH_SHORT).show();
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
            cmpPrevIndex = stack.peek().getCmpPrevIndex();
            valAtIndex = stack.peek().getValAtIndex();
            stack.pop();
        }
    }

    @Override
    void setInitialText() {
        text.setText(R.string.insertion_sort);
    }

    private class Holder {
        private String textView[];
        private Drawable color[];
        private String descriptionText[];
        private String text;
        private int index,cmpPrevIndex,valAtIndex;

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

        public int getCmpPrevIndex() { return cmpPrevIndex; }

        public int getValAtIndex() { return valAtIndex; }

        public Holder (TextView textView[], TextView descriptionText[], TextView text,int index,int cmpPrevIndex,int valAtIndex) {
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
            this.cmpPrevIndex = cmpPrevIndex;
            this.valAtIndex = valAtIndex;
        }
    }

}
