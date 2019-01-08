package com.example.muhem.dovizkuru;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = findViewById(R.id.guncelle);
        tv1 = findViewById(R.id.dolar);
        tv2 = findViewById(R.id.euro);
        tv3 = findViewById(R.id.pound);
        tv4 = findViewById(R.id.yen);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackGround().execute("https://api.exchangeratesapi.io/latest?base=TRY");
            }
        });
    }

    class BackGround extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            params[0] = "https://api.exchangeratesapi.io/latest?base=TRY";
            HttpURLConnection connection;
            BufferedReader br;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));

                String dosya = "";
                String satir;
                while ((satir = br.readLine()) != null) {
                    dosya += satir;
                }
                br.close();
                return dosya;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "hata";
        }
        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jo = jsonObject.getJSONObject("rates");
                tv1.setText("Dolar = " + jo.getString("USD"));
                tv2.setText("Euro = " + jo.getString("EUR"));
                tv3.setText("Pound = " + jo.getString("GBP"));
                tv4.setText("Yen = " + jo.getString("JPY"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
