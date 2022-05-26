package com.cookandroid.exam.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.cookandroid.exam.R;
import com.cookandroid.exam.Util.LocationItem;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{

    Context context;
    ArrayList<LocationItem> itemList;
    EditText editText;
    RecyclerView recyclerView;

    public LocationAdapter(Context context, EditText editText, RecyclerView recyclerView){
        this.context = context;
        this.editText = editText;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(itemList.get(position).getName());
        holder.road.setText(itemList.get(position).getRoad());
        holder.address.setText(itemList.get(position).getAddress());
        //클릭이벤트처리
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(itemList.get(position).getName());
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView road;
        TextView address;

        public ViewHolder(@NonNull final View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.location_name);
            road = (TextView) itemView.findViewById(R.id.location_road);
            address = (TextView) itemView.findViewById(R.id.location_address);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
