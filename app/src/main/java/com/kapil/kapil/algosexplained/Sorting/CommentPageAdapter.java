package com.kapil.kapil.algosexplained.Sorting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.kapil.kapil.algosexplained.Home.LoginActivity;
import com.kapil.kapil.algosexplained.R;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;

public class CommentPageAdapter extends RecyclerView.Adapter<CommentPageAdapter.ItemViewHolder> {

    private static final String TAG = "CommentPageAdapter";

    ArrayList <JSONData> arrayList;
    Context context;

    public CommentPageAdapter(Context context) {
        this.context = context;
    }

    public void setItems(ArrayList<JSONData> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public CommentPageAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_comment_layout,parent,false);

            return new CommentPageAdapter.ItemViewHolder(view,viewType);
        }
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_reply_layout,parent,false);

        return new CommentPageAdapter.ItemViewHolder(view,viewType);
    }

    @Override
    public int getItemViewType(int position) {
        return arrayList.get(position).getType();
    }

    @Override
    public void onBindViewHolder(final CommentPageAdapter.ItemViewHolder holder, final int pos) {
        final int position = holder.getAdapterPosition();
        int color = (pos % 3 == 0) ? Color.RED: (pos % 3 == 1) ? Color.BLUE: Color.GREEN;
        char text = arrayList.get(position).getUsername().charAt(0);
        TextDrawable drawable = TextDrawable.builder().buildRound(Character.toString(text), color);

        if (holder.viewType == 1) {
            //reply
            holder.usernameReply.setText(arrayList.get(position).getUsername());
            holder.replyText.setText(arrayList.get(position).getComment());
            holder.replyImage.setImageDrawable(drawable);
        }
        else if (holder.viewType == 0) {
            //comment
            holder.usernameComment.setText(arrayList.get(position).getUsername());
            holder.commentText.setText(arrayList.get(position).getComment());
            holder.commentImage.setImageDrawable(drawable);
            holder.replyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: ");
                    String value = holder.editTextReply.getText().toString();
                    if (value == null || value.isEmpty()) {
                        Toast.makeText(context, "Enter Reply",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    holder.editTextReply.setText("");
                    ( (SortActivity) context).onCalledReply(value,arrayList.get(position).getId(),position+1);
                    holder.loadReplyView.setVisibility(View.VISIBLE);
                    holder.addReply.setVisibility(View.GONE);
                }
            });
            holder.loadReplyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.loadReplyView.setVisibility(View.GONE);
                    holder.addReply.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (arrayList == null)
            return 0;
        return arrayList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView usernameComment, usernameReply;
        TextView commentText, replyText, loadReplyView;
        ImageView commentImage, replyImage;
        ImageButton replyButton;
        AppCompatEditText editTextReply;
        LinearLayout addReply;
        int viewType;

        public ItemViewHolder(View itemView, int viewType) {
            super(itemView);

            if (viewType == 1) {
                //reply
                replyText = (TextView) itemView.findViewById(R.id.text_view_reply);
                usernameReply = (TextView) itemView.findViewById(R.id.text_view_username_reply);
                replyImage = (ImageView) itemView.findViewById(R.id.info_image_reply);
                this.viewType = 1;
            } else {
                //comment
                commentText = (TextView) itemView.findViewById(R.id.text_view_comment);
                usernameComment = (TextView) itemView.findViewById(R.id.text_view_username);
                commentImage = (ImageView) itemView.findViewById(R.id.info_image_comment);
                replyButton = itemView.findViewById(R.id.reply_comment_button);
                editTextReply = itemView.findViewById(R.id.reply_editText);
                loadReplyView = itemView.findViewById(R.id.text_view_reply);
                addReply = itemView.findViewById(R.id.comment_layout);
                this.viewType = 0;
            }
        }
    }

}
