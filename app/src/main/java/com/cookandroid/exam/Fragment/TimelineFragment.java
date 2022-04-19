package com.cookandroid.exam.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cookandroid.exam.Adapter.TimelineAdapter;
import com.cookandroid.exam.R;


public class TimelineFragment extends ListFragment {
    TimelineAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //  return inflater.inflate(R.layout.fragment_daily, container, false);
        adapter = new TimelineAdapter();
        setListAdapter(adapter);

        adapter.addItem("12","AM"," ");
        adapter.addItem("1","AM"," ");
        adapter.addItem("2","AM"," ");
        adapter.addItem("3","AM"," ");
        adapter.addItem("4","AM"," ");
        adapter.addItem("5","AM"," ");
        adapter.addItem("6","AM"," ");
        adapter.addItem("7","AM"," ");
        adapter.addItem("8","AM"," ");
        adapter.addItem("9","AM"," ");
        adapter.addItem("10","AM"," ");
        adapter.addItem("11","AM"," ");
        adapter.addItem("12","PM"," ");
        adapter.addItem("1","PM"," ");
        adapter.addItem("2","PM"," ");
        adapter.addItem("3","PM"," ");
        adapter.addItem("4","PM"," ");
        adapter.addItem("5","PM"," ");
        adapter.addItem("6","PM"," ");
        adapter.addItem("7","PM"," ");
        adapter.addItem("8","PM"," ");
        adapter.addItem("9","PM"," ");
        adapter.addItem("10","PM"," ");
        adapter.addItem("11","PM"," ");

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /*//일정 클릭 이벤트 처리 -> 일정상세페이지로 이동
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {

    }*/
}