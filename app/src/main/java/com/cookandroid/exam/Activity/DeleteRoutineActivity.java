package com.cookandroid.exam.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cookandroid.exam.Fragment.RoutineFragment;
import com.cookandroid.exam.Model.RetrofitClient;
import com.cookandroid.exam.Model.RoutineService;
import com.cookandroid.exam.R;
import com.cookandroid.exam.Util.Routine;
import com.cookandroid.exam.Util.RoutineData;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteRoutineActivity extends Activity {

    private List<RoutineData> list = new ArrayList<>();
    private int deleteID;
    private RoutineData routineData;
    TextView deleteRoutineName;
    Button canclebtn, deletebtn;

    private RoutineService routineService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activiey_delete_routine);

        routineService = RetrofitClient.getClient().create(RoutineService.class);

        //UI객체 생성
        deleteRoutineName = (TextView) findViewById(R.id.routine_delete_name);
        canclebtn = (Button) findViewById(R.id.routine_delete_cancle);
        deletebtn = (Button) findViewById(R.id.routine_delete_delete);

        //데이터 가져오기
        Intent intent = getIntent();
        if (intent != null) {
            deleteID = intent.getIntExtra("deleteID", 0);
            System.out.println(deleteID);
        }
        System.out.println("deleteActivity deleteId = " + deleteID);

        canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                deleteRoutine();
                finish();
            }
        });

    }

    private void deleteRoutine() {
        Call<Routine> call = routineService.deleteRoutine(deleteID);
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
}
