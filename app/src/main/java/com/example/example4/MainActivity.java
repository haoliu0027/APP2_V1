package com.example.example4;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import android.app.Activity;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.lang.Math;
import android.widget.Toast;

/**
 * Smart Phone Sensing Example 4. Wifi received signal strength.
 */
public class MainActivity extends Activity {

    /**
     * The wifi manager.
     */
    private WifiManager wifiManager;
    /**
     * The button.
     */
    private Button activation;
    private TextView cella, cellb, cellc, celld;
    private int data[][] = {{-48, -62, -63, 1}, {-48,-66,-40, 1},{-48, -72, -33, 1},{-49, -72, -33, 1}, {-50, -71, -34, 1}, {-47, -71, -34, 1},
                            {-46, -60, -43, 1}, {-44,-65,-45, 1},{-46, -66, -58, 1},{-46, -66, -58, 1}, {-43, -63, -39, 1}, {-44, -63, -39, 1},
                            {-50, -56, -62, 2}, {-47,-56,-48, 2},{-36, -56, -48, 2},{-37, -56, -45, 2}, {-38, -56, -45, 2}, {-39, -65, -50, 2},
                            {-36, -65, -50, 2}, {-41,-66,-45, 2},{-48, -66, -45, 2},{-46, -65, -49, 2}, {-43, -65, -49, 2}, {-42, -65, -43, 2},
                            {-48, -65, -43, 3}, {-49,-58,-62, 3},{-50, -58, -62, 3},{-48, -61, -52, 3}, {-48, -61, -52, 3}, {-49, -56, -58, 3},
                            {-51, -56, -58, 3}, {-54,-53,-54, 3},{-54, -53, -54, 3},{-53, -67, -50, 3}, {-52, -67, -50, 3}, {-52, -65, -52, 3},
                            {-60, -65, -52, 4}, {-57,-60,-60, 4},{-54, -60, -60, 4},{-54, -56, -56, 4}, {-55, -56, -56, 4}, {-51, -48, -53, 4},
                            {-49, -48, -53, 4}, {-53,-53,-50, 4},{-58, -53, -50, 4},{-55, -54, -62, 4}, {-53, -53, -58, 4}, {-54, -53, -58, 4}};
    int[][] distance = new int[2][48];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the button
        Button activation = (Button) findViewById(R.id.activation);
        cella = (TextView) findViewById(R.id.cell1);
        cellb = (TextView) findViewById(R.id.cell2);
        cellc = (TextView) findViewById(R.id.cell3);
        celld = (TextView) findViewById(R.id.cell4);
        for (int j = 0; j < 48; j++) {
            distance[0][j] = j;
            distance[1][j] = 0;
        }
        activation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set wifi manager.
                wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                // Start a wifi scan.
                wifiManager.startScan();
                // Store results in a list.
                List<ScanResult> scanResults = wifiManager.getScanResults();
                for (ScanResult scanResult : scanResults) {
                    if(scanResult.BSSID.equals("90:5c:44:af:de:82")) {
                        System.out.println("go the network 1");
                        int new_val = scanResult.level;
                        for (int i = 0; i < 48; i++){
                            System.out.println("new_val1: "+ new_val);
                            System.out.println("data: "+ data[i][0]);
                            distance[1][i] += Math.pow((new_val-data[i][0]),2);
                            System.out.println("pow1: "+ Math.pow((new_val-data[i][0]),2));
                        }
                    }else if(scanResult.BSSID.equals("90:5c:44:af:de:45")){
                        System.out.println("go the network 2");
                        int new_val2 = scanResult.level;
                        for (int i = 0; i < 48; i++) {
                            System.out.println("new_val2: "+ new_val2);
                            System.out.println("data: "+ data[i][1]);
                            distance[1][i] += Math.pow((new_val2-data[i][1]),2);
                            System.out.println("pow2: "+ Math.pow((new_val2-data[i][1]),2));
                        }
                    }else if(scanResult.BSSID.equals("20:e8:82:f0:6a:f4")){
                        System.out.println("go the network 3");
                        int new_val3 = scanResult.level;
                        for (int i = 0; i < 48; i++) {
                            System.out.println("new_val3: "+ new_val3);
                            System.out.println("data: "+ data[i][2]);
                            distance[1][i] += Math.pow((new_val3-data[i][2]),2);
                            System.out.println("pow3: "+ Math.pow((new_val3-data[i][2]),2));
//                            System.out.print("distance: " + distance[1][i]);
                        }
                    }
                }
//                for (int i = 0; i < 48; i++){
//                    System.out.println("a "+distance[1][i]);
//                }
                for (int i = 0; i < 48; i++){
                    for (int j = 0; j < 47; j++){
                        if(distance[1][j+1] < distance[1][j]){
                            int temp = distance[1][j+1];
                            System.out.print(distance[1][j+1]);
                            distance[1][j+1] = distance[1][j];
                            distance[1][j] = temp;

                            int temp_index = distance[0][j+1];
                            distance[0][j+1] = distance[0][j];
                            distance[0][j] = temp_index;

                        }
                    }
                }
                System.out.println("distance: ");
                for (int i = 0; i < 48; i++){
                    System.out.print(" "+distance[1][i]);
                    System.out.println(" "+ distance[0][i]);
                }
                int k = 0;
                int num_r1 = 0;
                int num_r2 = 0;
                int num_r3 = 0;
                int num_r4 = 0;
                int index = 0;
                while (k < 3){
                    index = distance[0][k];
                    System.out.println();
                    System.out.println("index" + index);
                    if(data[index][3] == 1) num_r1++;
                    else if(data[index][3]==2) num_r2++;
                    else if(data[index][3]==3) num_r3++;
                    else num_r4++;
                    k++;
                }
                System.out.println("num_r1: " + num_r1);
                System.out.println("num_r2: " + num_r2);
                System.out.println("num_r3: " + num_r3);
                System.out.println("num_r4: " + num_r4);
                if(num_r1 > num_r2 && num_r1 > num_r3 && num_r1 > num_r4){
                    cella.setTextColor(Color.RED);
                    cellb.setTextColor(Color.BLACK);
                    cellc.setTextColor(Color.BLACK);
                    celld.setTextColor(Color.BLACK);
                }else if(num_r2 > num_r1 && num_r2 > num_r3 && num_r2 > num_r4){
                    cella.setTextColor(Color.BLACK);
                    cellb.setTextColor(Color.RED);
                    cellc.setTextColor(Color.BLACK);
                    celld.setTextColor(Color.BLACK);
                }else if(num_r3 > num_r1 && num_r3 > num_r2 && num_r3 > num_r2){
                    cella.setTextColor(Color.BLACK);
                    cellb.setTextColor(Color.BLACK);
                    cellc.setTextColor(Color.RED);
                    celld.setTextColor(Color.BLACK);
                }else{
                    cella.setTextColor(Color.BLACK);
                    cellb.setTextColor(Color.BLACK);
                    cellc.setTextColor(Color.BLACK);
                    celld.setTextColor(Color.RED);
                }

            }
        });
    }
}