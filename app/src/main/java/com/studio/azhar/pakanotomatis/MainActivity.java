package com.studio.azhar.pakanotomatis;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import static com.studio.azhar.pakanotomatis.App.CHANNEL_1_ID;

public class MainActivity extends AppCompatActivity {

    MqttHelper mqttHelper;
    private NotificationManagerCompat notificationManager;


    private Button btnPublish,btnPicker1, btnPicker2, btnPicker3;
    private Button btnUpload1, btnUpload2, btnUpload3;

    MediaPlayer mMediaPlayer;

    private MqttAndroidClient client;
    private String TAG = "MainActivity";
    private PahoMqttClient pahoMqttClient;

    public TimePickerDialog timePickerDialog;
    public TextView tvTimeResult,tvTimeResult1, tvTimeResult2, tvTimeResult3 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        pahoMqttClient = new PahoMqttClient();

        btnPublish = findViewById(R.id.btnPublish);
        btnPicker1 = findViewById(R.id.btnPicker1);
        btnPicker2 = findViewById(R.id.btnPicker2);
        btnPicker3 = findViewById(R.id.btnPicker3);

        btnUpload1 = findViewById(R.id.btnUpload1);
        btnUpload2 = findViewById(R.id.btnUpload2);
        btnUpload3 = findViewById(R.id.btnUpload3);


        tvTimeResult1 = findViewById(R.id.tvTimeResult1);
        tvTimeResult2 = findViewById(R.id.tvTimeResult2);
        tvTimeResult3 = findViewById(R.id.tvTimeResult3);

       client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);


       btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishMQTT();
            }
        });


       btnPicker1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showTimeDialog1();
           }
       });

        btnPicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog2();
            }
        });

        btnPicker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog3();
            }
        });

        btnUpload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String settingTime1 = tvTimeResult1.getText().toString().trim();
                String mSetTime1 = ("1."+settingTime1+"");

                if (!mSetTime1.isEmpty()) {
                    try {

                        pahoMqttClient.publishMessage(client, mSetTime1, 0, Constants.PUBLISH_TOPIC);

                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnUpload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String settingTime2 = tvTimeResult2.getText().toString().trim();
                String mSetTime2 = ("2."+settingTime2+"");

                if (!mSetTime2.isEmpty()) {
                    try {

                        pahoMqttClient.publishMessage(client, mSetTime2, 0, Constants.PUBLISH_TOPIC);

                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        btnUpload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String settingTime3 = tvTimeResult3.getText().toString().trim();
                String mSetTime3 = ("3."+settingTime3+"");

                if (!mSetTime3.isEmpty()) {
                    try {

                        pahoMqttClient.publishMessage(client, mSetTime3, 0, Constants.PUBLISH_TOPIC);

                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        startMqtt();

    }

    private void startMqtt(){
        mqttHelper = new MqttHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.w("Debug", message.toString());
//                dataA.setText(message.toString());
                String datSub = message.toString();
                //if (datSub.equals("SEC_A_01_ER")){
                sendOnChannel1(datSub);
                //}


            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


    }

    public void sendOnChannel1(String datSub) {
        mMediaPlayer = MediaPlayer.create(this, R.raw.emergency);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.start();

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Intent broadcastIntent = new Intent(this, NotificationReceiver.class);
        broadcastIntent.putExtra("toastMessage", "Pakan Otomatis");
        PendingIntent actionIntent = PendingIntent.getBroadcast(this,
                0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.gem)
                .setContentTitle("Pakan Otomatis")
                .setContentText(datSub)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                .build();

        notificationManager.notify(1, notification);
    }

    private void publishMQTT() {

        String settingTime1 = tvTimeResult1.getText().toString().trim();
        String settingTime2 = tvTimeResult2.getText().toString().trim();
        String settingTime3 = tvTimeResult3.getText().toString().trim();

        String mSetTime1 = ("1."+settingTime1+"");
        String mSetTime2 = ("2."+settingTime2+"");
        String mSetTime3 = ("3."+settingTime3+"");

       // Log.d("SETIME", mSetTime);

        if ((!mSetTime1.isEmpty()) ||(!mSetTime2.isEmpty()) || (!mSetTime3.isEmpty())) {
            try {
                //ganti topic khusus untuk publish setting
                pahoMqttClient.publishMessage(client, mSetTime1, 0, Constants.PUBLISH_TOPIC);
                pahoMqttClient.publishMessage(client, mSetTime2, 0, Constants.PUBLISH_TOPIC);
                pahoMqttClient.publishMessage(client, mSetTime3, 0, Constants.PUBLISH_TOPIC);
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void showTimeDialog1(){
       Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {

                tvTimeResult1.setText(String.format("%02d:%02d", hourOfDay, minutes));

            }
        },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true
        );
        timePickerDialog.show();
    }

    public void showTimeDialog2(){
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {

                tvTimeResult2.setText(String.format("%02d:%02d", hourOfDay, minutes));

            }
        },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true
        );
        timePickerDialog.show();
    }

    public void showTimeDialog3() {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {

                tvTimeResult3.setText(String.format("%02d:%02d", hourOfDay, minutes));

            }
        },
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
        );
        timePickerDialog.show();
    }
}