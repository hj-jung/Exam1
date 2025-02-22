package com.cookandroid.mysonge.Fragment;

import android.content.Intent;
import android.graphics.Color;
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

import com.cookandroid.mysonge.Activity.AddRoutineActivity;
import com.cookandroid.mysonge.Activity.DeleteRoutineActivity;
import com.cookandroid.mysonge.Adapter.RoutineListAdapter;
import com.cookandroid.mysonge.DTO.Routine;
import com.cookandroid.mysonge.Interface.RoutineService;
import com.cookandroid.mysonge.R;
import com.cookandroid.mysonge.Retrofit.RetrofitClient;
import com.cookandroid.mysonge.Util.RoutineAchive;
import com.cookandroid.mysonge.Util.RoutineData;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

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

    PieChart pieChart;

    private int deleteId, checkId, user_id;
    private boolean routineIsChecked;

    private String routineAchive = "NaN";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_routine, container, false);

        routineService = RetrofitClient.getClient().create(RoutineService.class);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        ImageButton Routinebtn = (ImageButton) rootView.findViewById(R.id.addRoutine);
        pieChart = (PieChart) rootView.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(0,0,0,0);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.BLACK);
        pieChart.setTransparentCircleRadius(61f);

        Routinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddRoutineActivity.class);
                intent.putExtra("userID", user_id);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().startActivityForResult(intent, 3);
                if (getArguments() != null) {
                    list = getArguments().getParcelableArrayList("routineList");
                    user_id = getArguments().getInt("userID");
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
            getAchieve();
            list = getArguments().getParcelableArrayList("routineList");
            user_id = getArguments().getInt("userID");

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
                    intent.putExtra("deleteRoutineName", list.get(pos).routineName);
                    getActivity().startActivityForResult(intent, 4);

                }

                @Override
                public void onCheckClick(View v, int pos, boolean isChecked) {
                    checkId = list.get(pos).routineId;
                    routineIsChecked = isChecked;
                    checkRoutine();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getAchieve();
                }
            });

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();

        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getAchieve();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        Call<String> call = routineService.getAchieve(user_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.d("TAG", String.valueOf(response.code()));
                    return;
                }
                routineAchive = response.body();
                ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();

                if (routineAchive.equals("NaN")) {
                    System.out.println("here");
                    yValues.add(new PieEntry(0f,"achieve"));
                    yValues.add(new PieEntry(100f,"none"));
                }
                else {
                    System.out.println("else here");
                    yValues.add(new PieEntry((float) Double.parseDouble(routineAchive), "achieve"));
                    yValues.add(new PieEntry(100 - (float) Double.parseDouble(routineAchive), "none"));
                }

                PieDataSet dataSet = new PieDataSet(yValues,"");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(Color.parseColor("#FDF078"), Color.WHITE);

                PieData data = new PieData((dataSet));
                data.setValueTextSize(13f);
                data.setValueTextColor(Color.BLACK);

                pieChart.invalidate(); // 회전 및 터치 효과 사라짐
                pieChart.setTouchEnabled(false);

                pieChart.setData(data);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }
}