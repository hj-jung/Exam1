package com.cookandroid.exam.Fragment;

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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

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

    PieChart pieChart;

    private int deleteId, checkId;
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
            getAchieve();
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
        Call<String> call = routineService.getAchieve();
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

                Description description = new Description();
                description.setText("루틴 성취율  "); //라벨
                description.setTextSize(15);
                pieChart.setDescription(description);

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