package com.cookandroid.weatherapi;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class apiTest extends Thread{
    String finall;
    public void func() throws IOException, JSONException{
        String endPoint = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/";
        String serviceKey = "3QkdRZx%2FR%2BOBMH%2B5QoKu7iRbyDkdjaO0nMixw6RktnNL74%2F9rWajdRkCmtRfYxxYrWv8OABBzFaEY5h6WqwJFA%3D%3D";
        String pageNo = "1";
        String numOfRows = "12";
        String baseDate = "20220516";
        String baseTime = "1100";
        String nx = "55";
        String ny = "127";

        String s = endPoint+"getVilageFcst?serviceKey="+serviceKey
                +"&pageNo="+pageNo+"&numOfRows="+numOfRows+"&dataType=JSON"
                +"&base_date="+baseDate+"&base_time="+baseTime+"&nx="+nx+"&ny="+ny;

        URL url = new URL(s);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while((line = bufferedReader.readLine())!=null){
            stringBuilder.append(line);
        }

        bufferedReader.close();
        String result = stringBuilder.toString();
        conn.disconnect();

        JSONObject mainObject = new JSONObject(result);
        JSONArray itemArray = mainObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");

        for(int i=0; i<itemArray.length(); i++){
            JSONObject item = itemArray.getJSONObject(i);
            String category = item.getString("category");
            String value = item.getString("fcstValue");
            System.out.println(category+" "+value);
            finall += category+" "+value+"/";
        }
        System.out.println(finall);
    }
}
