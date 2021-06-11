package com.example.myassignment4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import java.sql.Timestamp;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    myDB db;
    MainDao mainDao;
    TextView accx,accy,accz,laccx,laccy,laccz,temp,light,lat,longi,proximity,avgaccx,avgaccy,avgaccz,avgtemp,mot,mx,my,mz;
    ToggleButton switch1,switch2,switch3,switch4,switch5,switch6;
    Button b1,b2;
    SensorManager sensorManager;
    Sensor accelerometerSensor = null;
    Sensor linearAcceSensor = null;
    Sensor temperatureSensor = null;
    Sensor lightSensor = null;
    LocationManager locationManager;
    Sensor proximitySensor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= myDB.getInstance(getApplicationContext());
        mainDao = db.mainDao();
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);
        switch4 = findViewById(R.id.switch4);
        switch5 = findViewById(R.id.switch5);
        switch6 = findViewById(R.id.switch6);
        b1 = findViewById(R.id.avgAcc);
        b2 = findViewById(R.id.avgTemp);
        accx = findViewById(R.id.accX);
        accy = findViewById(R.id.accY);
        accz = findViewById(R.id.accZ);
        mx = findViewById(R.id.mox);
        my = findViewById(R.id.moy);
        mz = findViewById(R.id.moz);
        mot = findViewById(R.id.mo);
        avgaccx = findViewById(R.id.avgaccX);
        avgaccy = findViewById(R.id.avgaccY);
        avgaccz = findViewById(R.id.avgaccZ);
        laccx = findViewById(R.id.laccX);
        laccy = findViewById(R.id.laccY);
        laccz = findViewById(R.id.laccZ);
        temp = findViewById(R.id.temp);
        avgtemp = findViewById(R.id.avgTempValue);
        light = findViewById(R.id.light);
        lat = findViewById(R.id.lat);
        longi = findViewById(R.id.longi);
        proximity = findViewById(R.id.proximity);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        linearAcceSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findAvgAcc(v);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findAvgTemp(v);
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        sensorManager.registerListener(accSensor, accelerometerSensor, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(laccSensor, linearAcceSensor, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(tSensor, temperatureSensor, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(lSensor, lightSensor, sensorManager.SENSOR_DELAY_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 2, gpsSensor);
        sensorManager.registerListener(pSensor, proximitySensor, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(accSensor);
        sensorManager.unregisterListener(laccSensor);
        sensorManager.unregisterListener(tSensor);
        sensorManager.unregisterListener(lSensor);
        locationManager.removeUpdates(gpsSensor);
        sensorManager.unregisterListener(pSensor);
    }


    SensorEventListener accSensor = new SensorEventListener() {
        @SuppressLint("ResourceType")
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (switch1.isChecked()){
                accx.setText(String.valueOf((event.values[0])));
                accy.setText(String.valueOf((event.values[1])));
                accz.setText(String.valueOf((event.values[2])));
                AccT o = new AccT();
                o.timeStamp = event.timestamp;
                o.X =  String.valueOf(event.values[0]);
                o.Y =  String.valueOf(event.values[1]);
                o.Z =  String.valueOf(event.values[2]);
                mainDao.insertAccT(o);
            System.out.println(event.sensor.getName()+" X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+event.values[2]);}

                float x = event.values[0];
               float y = event.values[1];
               float z = event.values[2];
               double a = Math.sqrt(Math.pow(x,2) + Math.pow(y,2)+ Math.pow(z,2)- SensorManager.GRAVITY_EARTH);
            double ax = Math.sqrt(Math.pow(x,2)- SensorManager.GRAVITY_EARTH);
            double ay = Math.sqrt( Math.pow(y,2)- SensorManager.GRAVITY_EARTH);
            double az = Math.sqrt( Math.pow(z,2)- SensorManager.GRAVITY_EARTH);
            if(a>10.0f){
                   System.out.println("motion!!!!!");
                   mot.setText("MOTION");
                   mot.setTextColor(Color.RED);
               }
            else{
                mot.setText("STATIONARY");
                mot.setTextColor(Color.BLUE);
            }
            if(ax>4.0f){
                System.out.println("motion!!!!!");
                mx.setText("MOTION : X");
                mx.setTextColor(Color.RED);
            }
            else{
                mx.setText("STATIONARY : X");
                mx.setTextColor(Color.GREEN);
            }
            if(ay>10.0f){
                System.out.println("motion!!!!!");
                my.setText("MOTION : Y");
                my.setTextColor(Color.RED);
            }
            else{
                my.setText("STATIONARY : Y");
                my.setTextColor(Color.CYAN);
            }
            if(az>4.0f){
                System.out.println("motion!!!!!");
                mz.setText("MOTION : Z");
                mz.setTextColor(Color.RED);
            }
            else{
                mz.setText("STATIONARY : Z");
                mz.setTextColor(Color.YELLOW);
            }
            }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

    };

    public void findAvgAcc(View v){
        Double totalx=0.0,totaly=0.0,totalz=0.0;
        int i;
        Timestamp ts = new Timestamp(System.currentTimeMillis()-(60*60*1000));
        long t = ts.getTime();
        List<AccT> data = mainDao.getAccT(t);
        for(i=0;i<data.size();i++){
            totalx += Double.parseDouble(data.get(i).X);
            totaly += Double.parseDouble(data.get(i).Y);
            totalz += Double.parseDouble(data.get(i).Z);
        }
        avgaccx.setText(String.valueOf((totalx/data.size())));
        avgaccy.setText(String.valueOf((totaly/data.size())));
        avgaccz.setText(String.valueOf((totalz/data.size())));
    }

    SensorEventListener laccSensor = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (switch2.isChecked()){
                laccx.setText(String.valueOf((event.values[0])));
                laccy.setText(String.valueOf((event.values[1])));
                laccz.setText(String.valueOf((event.values[2])));
                LaccT o = new LaccT();
                o.timeStamp = event.timestamp;
                o.lX =  String.valueOf(event.values[0]);
                o.lY =  String.valueOf(event.values[1]);
                o.lZ =  String.valueOf(event.values[2]);
                mainDao.insertLaccT(o);
            System.out.println(event.sensor.getName()+" X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+event.values[2]);}

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    SensorEventListener tSensor = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (switch3.isChecked()){
                temp.setText(String.valueOf(event.values[0]));
                TT o = new TT();
                o.timeStamp = event.timestamp;
                o.TempValue =  String.valueOf(event.values[0]);
                mainDao.insertTT(o);
            System.out.println(event.sensor.getName()+" "+event.values[0]);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void findAvgTemp(View v){
        Double total=0.0;
        int i;
        Timestamp ts = new Timestamp(System.currentTimeMillis()-(60*60*1000));
        long t = ts.getTime();
        List<TT> data = mainDao.getTT(t);
        for(i=0;i<data.size();i++){
            total += Double.parseDouble(data.get(i).TempValue);
        }
        avgtemp.setText(String.valueOf((total/data.size())));
    }

    SensorEventListener lSensor = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (switch4.isChecked()){
                light.setText(String.valueOf(event.values[0]));
                LT o = new LT();
                o.timeStamp = event.timestamp;
                o.LightValue =  String.valueOf(event.values[0]);
                mainDao.insertLT(o);
            System.out.println(event.sensor.getName()+" "+event.values[0]);}
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    LocationListener gpsSensor = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            if (switch5.isChecked()){
                lat.setText(String.valueOf(location.getLatitude()));
                longi.setText(String.valueOf(location.getLongitude()));
                GPST o = new GPST();
                o.lat =  String.valueOf(location.getLatitude());
                o.lng =  String.valueOf(location.getLongitude());
                mainDao.insertGPST(o);
            System.out.println("GPS: lat "+location.getLatitude()+" long: "+location.getLongitude());}
        }
    };

    SensorEventListener pSensor = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (switch6.isChecked()){
                proximity.setText(String.valueOf(event.values[0]));
                PT o = new PT();
                o.timeStamp = event.timestamp;
                o.ProximityValue =  String.valueOf(event.values[0]);
                mainDao.insertPT(o);
            System.out.println(event.sensor.getName()+" "+event.values[0]);}
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

}