package com.cookandroid.mysonge.Model;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.mysonge.R;

public class GridViewHolder extends RecyclerView.ViewHolder {
    public RecyclerView recyclerView;
    public TextView eventName;

    public GridViewHolder(View itemView){
        super(itemView);
        recyclerView = itemView.findViewById(R.id.calendar);
        eventName = itemView.findViewById(R.id.calendar_memo);
    }
}
