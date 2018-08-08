package com.example.shivamgandhi.basicapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/**
 * This is example of OkHttp
 */


public class SecondActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();
    private String url= "https://reqres.in/api/users/2";
    ArrayList<String> arryList = new ArrayList<>();
    String reply;
    final String accessToken = "Bearer KA.eyJ2ZXJzaW9uIjoyLCJpZCI6Ik9CYTRiSC9jVEZlb3NwV0JMQnNZcnc9PSIsImV4cGlyZXNfYXQiOjE1MzYyOTY2MTcsInBpcGVsaW5lX2tleV9pZCI6Ik1RPT0iLCJwaXBlbGluZV9pZCI6MX0.iNemyUjxlv6oi1KILRO74CrhaNhMSObII6AqfNx4sjI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        getData();

    }

    public void getData() {



       // String URL = "https://api.darksky.net/forecast/ff41689fc242d7783a79fab7ae586b2b/45.4215,-75.6972?exclude=currently,flags,minutely,hourly,flags&units=si";
          String URL = "https://api.uber.com/v1.2/estimates/price?start_latitude=37.7752315&start_longitude=-122.418075&end_latitude=37.7752415&end_longitude=-122.518075";

        Request request = new Request.Builder().url(URL).build();

        client = new OkHttpClient.Builder()
                .addInterceptor(new BasicAuth(accessToken))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Fail::","Request Successful??");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("onResponse","successfully!!!!!!!!!!! "+response.isSuccessful());
                if(response.isSuccessful())
                {
                    reply = response.body().string();
                    Log.d("data:: uber",reply);

                    //getJSONdata();
                }


            }
        });

    }





    void getJSONdata(){
        SecondActivity.this.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                JSONObject str = null;
                try {
                    str = new JSONObject(reply);
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                JSONObject sys  = null;

                try
                {
                    sys = str.getJSONObject("daily");
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                JSONArray array_daily_weather_data = null;
                try
                {
                    array_daily_weather_data = sys.getJSONArray("data");
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                getAverageData(array_daily_weather_data);
            }
        });

    }

    void getAverageData(JSONArray array_daily_weather_data) {

        for (int i = 0; i < array_daily_weather_data.length();i++)
        {
            try {

                JSONObject temprature = array_daily_weather_data.getJSONObject(i);

                String Max = temprature.getString("temperatureHigh");
                String Min= temprature.getString("temperatureLow");

                double avergae_min = Double.parseDouble(Min);
                double avergae_max = Double.parseDouble(Max);

                double avr_temp = (avergae_max + avergae_min)/2;

                Log.d("Average Temprature::", ">>>>>>> Avergae <<<<<" + avr_temp);

                arryList.add(String.valueOf(avr_temp));
            }
            catch (JSONException e)
            {
                Log.d("JSONException",">>>>>> error <<<<<<<");
                e.printStackTrace();
            }
        }

    }
}
