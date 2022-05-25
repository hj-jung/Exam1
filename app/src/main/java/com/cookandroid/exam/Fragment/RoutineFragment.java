package com.cookandroid.exam.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.cookandroid.exam.Activity.AddRoutineActivity;
import com.cookandroid.exam.Activity.DeleteRoutineActivity;
import com.cookandroid.exam.Adapter.RoutineListAdapter;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Util.RoutineData;


import java.util.ArrayList;

public class RoutineFragment extends Fragment {

    private static final String TAG = "RoutineFragment";

    private RecyclerView recyclerView;
    private RoutineListAdapter adapter;
    private ArrayList<RoutineData> list = new ArrayList<RoutineData>();

    private int deleteId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_routine, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        ImageButton Routinebtn = (ImageButton) rootView.findViewById(R.id.addRoutine);

        Routinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity(), AddRoutineActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().startActivityForResult(new Intent(getContext(), AddRoutineActivity.class), 3);
                if (getArguments() != null) {
                    list = getArguments().getParcelableArrayList("routineList");
                    recyclerView.setHasFixedSize(true);
                    adapter = new RoutineListAdapter(getActivity(), list);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter);
                }
                //startActivity(intent);
            }
        });

        if (getArguments() != null) {
            list = getArguments().getParcelableArrayList("routineList");

            recyclerView.setHasFixedSize(true);
            adapter = new RoutineListAdapter(getActivity(), list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new RoutineListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int pos) {
                    Intent intent = new Intent(getContext(), DeleteRoutineActivity.class);
                    deleteId = list.get(pos).routineId;
                    intent.putExtra("deleteID", deleteId);
                    getActivity().startActivityForResult(intent, 4);

                }
            });

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();

        }

        return rootView;
    }
}