package com.cookandroid.exam.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cookandroid.exam.Activity.AddRoutineActivity;
import com.cookandroid.exam.Activity.EditMypageActivity;
import com.cookandroid.exam.Adapter.RoutineListAdapter;
import com.cookandroid.exam.Model.RetrofitClient;
import com.cookandroid.exam.Model.RoutineService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Util.Routine;
import com.cookandroid.exam.Util.RoutineData;


import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutineFragment extends Fragment {

    private static final String TAG = "RoutineFragment";

    private RecyclerView recyclerView;
    private RoutineListAdapter adapter;
    private ArrayList<RoutineData> list = new ArrayList<RoutineData>();

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

                System.out.println("list.isEmpty is " + list.isEmpty());
                //startActivity(intent);
            }
        });

        if (getArguments() != null) {
            list = getArguments().getParcelableArrayList("routineList");

            recyclerView.setHasFixedSize(true);
            adapter = new RoutineListAdapter(getActivity(), list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }

        return rootView;
    }
}