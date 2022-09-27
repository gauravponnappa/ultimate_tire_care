package com.example.tyrecare;


import static androidx.core.math.MathUtils.clamp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class alignment_activity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";
    private static int i=0;
    private SensorManager sensorManager;
    Sensor accelerometer;
    TextView xangle,yangle,zangle,warn,camber,textView4;
    Button test,flbtn,frbtn,rlbtn,rrbtn,savebtn,again;
    CardView card;


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d(TAG, "changed values");

    }

    /*private static final boolean ADAPTIVE_ACCEL_FILTER = true;
    float lastAccel[] = new float[3];
    float accelFilter[] = new float[3];*/

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String UID= mAuth.getUid();
    String selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alignment);

        getSupportActionBar().hide();






        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(alignment_activity.this, accelerometer, SensorManager.SENSOR_DELAY_UI, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);

        flbtn = findViewById(R.id.flbtn);
        frbtn = findViewById(R.id.frbtn);
        rlbtn = findViewById(R.id.rlbtn);
        rrbtn = findViewById(R.id.rrbtn);
        card = findViewById(R.id.card);
        savebtn = findViewById(R.id.savebtn);
        again=findViewById(R.id.testagain);
        textView4=findViewById(R.id.textView4);

        xangle = findViewById(R.id.xangle);
        yangle = findViewById(R.id.yangle);
        zangle = findViewById(R.id.zangle);
        warn = findViewById(R.id.warn);
        camber = findViewById(R.id.camber);
        test = findViewById(R.id.test);

        flbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected=new String("fl");
               dissapear();
            }
        });

        frbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected=new String("fr");
                dissapear();

            }
        });

        rlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected=new String("rl");
                dissapear();

            }
        });

        rrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected=new String("rr");
                dissapear();

            }
        });

        savebtn.setEnabled(false);

        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartActivity();
            }
        });


    }

    public void restartActivity(){
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }

    private void dissapear() {
        card.setVisibility(View.VISIBLE);
        flbtn.setVisibility(View.GONE);
        frbtn.setVisibility(View.GONE);
        rlbtn.setVisibility(View.GONE);
        rrbtn.setVisibility(View.GONE);
        camber.setVisibility(View.VISIBLE);
        textView4.setVisibility(View.GONE);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float xround = (sensorEvent.values[0]);
        float yround = (sensorEvent.values[1]);
        float zround = (sensorEvent.values[2]);

        onFilteredAccelerometerChanged(xround,yround,zround);



    }

      /*  public void onAccelerometerChanged(float accelX, float accelY, float accelZ) {
        // high pass filter
        float updateFreq = 300; // match this to your update speed
        float cutOffFreq = 10.1f;
        float RC = 10.0f / cutOffFreq;
        float dt = 10.0f / updateFreq;
        float filterConstant = RC / (dt + RC);
        double alpha = filterConstant;
        float kAccelerometerMinStep = 0.33f;
        float kAccelerometerNoiseAttenuation = 10.0f;

        if(ADAPTIVE_ACCEL_FILTER)
        {
            double d = clamp(Math.abs(norm(accelFilter[0], accelFilter[1], accelFilter[2]) - norm(accelX, accelY, accelZ)) / kAccelerometerMinStep - 1.0f, 0.0f, 1.0f);
            alpha = d * filterConstant / kAccelerometerNoiseAttenuation + (1.0f - d) * filterConstant;
        }

        accelFilter[0] = (float) (alpha * (accelFilter[0] + accelX - lastAccel[0]));
        accelFilter[1] = (float) (alpha * (accelFilter[1] + accelY - lastAccel[1]));
        accelFilter[2] = (float) (alpha * (accelFilter[2] + accelZ - lastAccel[2]));

        lastAccel[0] = accelX;
        lastAccel[1] = accelY;
        lastAccel[2] = accelZ;
    }*/
     /*double norm(double x, double y, double z)
    {
        return Math.sqrt(x * x + y * y + z * z);
    }

    double clamp(double v, double min, double max)
    {
        if(v > max)
            return max;
        else if(v < min)
            return min;
        else
            return v;
    }*/


    private void onFilteredAccelerometerChanged(float v, float v1, float v2) {

        float xfiltered=v*9;
        float yfiltered=v1*9;
        float zfiltered=v2*9;

        xangle.setText("xvalue = "+xfiltered);
        yangle.setText("yvalue = "+yfiltered);
        zangle.setText("zvalue = "+zfiltered);

        if (((Math.round(zfiltered)<80))){
            warn.setVisibility(View.INVISIBLE);
            test.setEnabled(true);
            test.setBackgroundColor(test.getContext().getResources().getColor(android.R.color.black));
            if (Math.round(yfiltered)>80){}
            else {
                //disable
                warn.setVisibility(View.VISIBLE);
                test.setEnabled(false);
                test.setBackgroundColor(test.getContext().getResources().getColor(android.R.color.system_accent2_500));
            }


        }else {
            warn.setVisibility(View.VISIBLE);
            test.setEnabled(false);
            test.setBackgroundColor(test.getContext().getResources().getColor(android.R.color.system_accent2_500));

        }
        arrayfilter(zfiltered);

    }

    float camberfiltered=0;
    double newcamb=0;

    private void arrayfilter(float z){
        float zz=z;
        int sample=5;

        float a[]=new float[sample];



        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for(i=0;i<sample;i++){
                    a[i] = zz;

                }


                        for(i=0;i<a.length;i++) {//length is the property of array
                               camberfiltered = camberfiltered + a[i];
                            }

                                        camberfiltered=camberfiltered/sample;
                                        System.out.println(camberfiltered);
                                        newcamb=((Math.round(camberfiltered*10.0))/10.0)*-1;
                                        camber.setText("Camber:\n"+newcamb);
                                        savebtn.setEnabled(true);
                                        test.setVisibility(View.INVISIBLE);
                                        test.setBackgroundColor(test.getContext().getResources().getColor(android.R.color.holo_green_dark));

            }



        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dbpath="users/"+UID+"/"+selected;

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference(dbpath);
                reference.setValue(newcamb);
                Toast.makeText(alignment_activity.this, "Data uploaded successfully",Toast.LENGTH_LONG).show();

            }
        });


    }

}