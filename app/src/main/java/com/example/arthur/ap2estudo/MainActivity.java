package com.example.arthur.ap2estudo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

import downloadlib.DownloadFactory;
import downloadlib.DownloadMng;



public class MainActivity extends AppCompatActivity {


    // EXAMPLE ACTIVITY TO SHOW LIBRARY USAGE.
    String link2 = "https://static.gamespot.com/uploads/scale_medium/1197/11970954/3181241-ig-lozbreathofthewildrelease-20170112.jpg";
    String link = "https://freemusicarchive.org/music/download/f18f1ef116794e17a787d807515b060e8591d1e2";


    DownloadFactory dfactory = new DownloadFactory();
    DownloadMng audiomanager = dfactory.makeurlobject(link,"audio");
    DownloadMng imagemanager = dfactory.makeurlobject(link2,"image");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.searchbtn);
        EditText urlBox = findViewById(R.id.UrlBox);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(audiomanager!=null) {
                    audiomanager.downloadStartThread(link);

                }
                if(imagemanager!=null){
                    imagemanager.downloadStartThread(link2);
                }



            }
        });
    }

}
