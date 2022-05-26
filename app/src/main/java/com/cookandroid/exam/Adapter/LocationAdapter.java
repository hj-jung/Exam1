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

    private Context context;
    private ArrayList<LocationItem> itemList;
    private EditText editText;
    private RecyclerView recyclerView;

    public LocationAdapter(ArrayList<LocationItem> itemList, Context context, EditText editText, RecyclerView recyclerView){
        this.context = context;
        this.itemList = itemList;
        this.editText = editText;
        this.recyclerView = recyclerView;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationAdapter.ViewHolder holder, int position) {
        holder.name.setText(itemList.get(position).getName());
        holder.road.setText(itemList.get(position).getRoad());
        holder.address.setText(itemList.get(position).getAddress());
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView road;
        TextView address;

        public ViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.location_name);
            road = (TextView) view.findViewById(R.id.location_road);
            address = (TextView) view.findViewById(R.id.location_address);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int pos);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) { this.mListener= listener; }

    public void clear() { itemList.clear(); }

    public void addItem(LocationItem item) { itemList.add(item); }

}
