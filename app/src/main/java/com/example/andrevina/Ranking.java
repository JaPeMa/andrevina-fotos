package com.example.andrevina;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Ranking extends AppCompatActivity {

    List<Record> records;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        recyclerView = findViewById(R.id.view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        records = new ArrayList<>();
        chargeRecords();
        LinearLayout layout = findViewById(R.id.rankingList);
//        for (Record record : records) {
//            layout.addView(new Button(this), new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT) );
//        }

        AdapterRecords adapterRecords = new AdapterRecords(records);
        recyclerView.setAdapter(adapterRecords);
    }

    public void chargeRecords() {

        records.add(new Record("Jaime", 4, Uri.parse("android.resource://"+BuildConfig.APPLICATION_ID+"/" + R.drawable.img1)));
        records.add(new Record("Usón", 3, Uri.parse("android.resource://"+BuildConfig.APPLICATION_ID+"/" + R.drawable.img2)));
        records.add(MainActivity.record);

        /*try{
            BufferedReader br = new BufferedReader(new FileReader(new File("data" + File.separator +"persistance.txt")));
            String linia;
            Toast.makeText(getApplicationContext(), "Empieza", Toast.LENGTH_SHORT).show();
            while((linia = br.readLine())!=null){
                Toast.makeText(getApplicationContext(), "img1", Toast.LENGTH_SHORT).show();
                records.add(new Record(linia.split(";")[img1], Integer.parseInt(linia.split(";")[0]), Uri.parse(linia.split(";")[img2])));
            }
            br.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            //Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }*/
    }

}
