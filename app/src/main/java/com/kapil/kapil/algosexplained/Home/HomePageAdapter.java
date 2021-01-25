package com.kapil.kapil.algosexplained.Home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kapil.kapil.algosexplained.R;
import com.kapil.kapil.algosexplained.Sorting.SortActivity;

import java.util.ArrayList;

/**
 * Created by kapil on 19-03-2018.
 */

public class HomePageAdapter extends RecyclerView.Adapter<HomePageAdapter.ItemViewHolder> {

    ArrayList <String> arrayList;
    Context context;

    public HomePageAdapter(ArrayList<String> arrayList,Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public HomePageAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_layout,parent,false);

            return new ItemViewHolder(view,viewType);
        }
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_caption_image,parent,false);

        return new ItemViewHolder(view,viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder,final int pos) {
        final int position = holder.getAdapterPosition();
        if (holder.viewType == 1) {
            holder.sortName.setText(arrayList.get(position-1));
            holder.sortName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    switch (position) {
                        case 1:
                            intent = new Intent(context, SortActivity.class);
                            intent.putExtra("fragmentName","Selection Sort");
                            break;
                        case 2:
                            intent = new Intent(context, SortActivity.class);
                            intent.putExtra("fragmentName","Bubble Sort");
                            break;
                        case 3:
                            intent = new Intent(context, SortActivity.class);
                            intent.putExtra("fragmentName","Insertion Sort");
                            break;
                        case 4:
                            intent = new Intent(context, SortActivity.class);
                            intent.putExtra("fragmentName","Merge Sort");
                            break;
                        case 5:
                            intent = new Intent(context, SortActivity.class);
                            intent.putExtra("fragmentName","Heap Sort");
                            break;
                    }
                    if (position >= 1 &&  position <= 5)
                        context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size() + 1;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView sortName;
        TextView headerText;
        int viewType;

        public ItemViewHolder(View itemView,int viewType) {
            super(itemView);
            if (viewType == 1) {
                sortName = (TextView) itemView.findViewById(R.id.text_view);
                this.viewType = 1;
            } else {
                headerText = (TextView) itemView.findViewById(R.id.header_text);
                this.viewType = 0;
            }
        }
    }

}
