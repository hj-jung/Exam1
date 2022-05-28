package com.cookandroid.exam.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cookandroid.exam.Activity.AddRoutineActivity;
import com.cookandroid.exam.Activity.DeleteRoutineActivity;
import com.cookandroid.exam.Adapter.RoutineListAdapter;
import com.cookandroid.exam.DTO.Routine;
import com.cookandroid.exam.Interface.RoutineService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Retrofit.RetrofitClient;
import com.cookandroid.exam.Util.RoutineAchive;
import com.cookandroid.exam.Util.RoutineData;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutineFragment extends Fragment {

    private static final String TAG = "RoutineFragment";

    private RecyclerView recyclerView;
    private RoutineListAdapter adapter;
    private ArrayList<RoutineData> list = new ArrayList<RoutineData>();

    RoutineService routineService;

    TextView routineRate;

    private int deleteId, checkId;
    private boolean routineIsChecked;

    private String routineAchive = "NaN";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_routine, container, false);

        routineService = RetrofitClient.getClient().create(RoutineService.class);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        ImageButton Routinebtn = (ImageButton) rootView.findViewById(R.id.addRoutine);
        routineRate = (TextView) rootView.findViewById(R.id.routine_rate);

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
                    getAchieve();
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

                @Override
                public void onCheckClick(View v, int pos, boolean isChecked) {
                    checkId = list.get(pos).routineId;
                    routineIsChecked = isChecked;
                    checkRoutine();
                    getAchieve();
                }
            });

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();

        }

        return rootView;
    }

    private void checkRoutine() {
        RoutineAchive routineAchive = new RoutineAchive(routineIsChecked);
        Call<Routine> call = routineService.checkRoutine(checkId, routineAchive);
        call.enqueue(new Callback<Routine>() {
            @Override
            public void onResponse(Call<Routine> call, Response<Routine> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAG", String.valueOf(response.code()));
                    return;
                }
                Routine routineResponse = response.body();
                System.out.println(routineResponse.toString());
            }

            @Override
            public void onFailure(Call<Routine> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }

    private void getAchieve() {
        Call<String> call = routineService.getAchieve();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAG", String.valueOf(response.code()));
                    return;
                }
                routineAchive = response.body();
                if (routineAchive.equals("NaN")) routineRate.setText("등록된 루틴이 없습니다");
                else routineRate.setText(routineAchive + "%");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }
}