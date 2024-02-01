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
import android.widget.Spinner;
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


    Button btnPublish,btnPicker1, btnPicker2, btnPicker3, btnPicker4;
    Button btnUpload1, btnUpload2, btnUpload3, btnUpload4;

    MediaPlayer mMediaPlayer;

    private MqttAndroidClient client;
    private String TAG = "MainActivity";
    private PahoMqttClient pahoMqttClient;

    public TimePickerDialog timePickerDialog;
    public TextView tvTimeResult,tvTimeResult1, tvTimeResult2, tvTimeResult3,tvTimeResult4;

    EditText edt_duration1, edt_duration2, edt_duration3, edt_duration4;
    Spinner spinner1,spinner2, spinner3, spinner4;

    String status_set;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //berfungsi untuk mengInstance notofikasi
        notificationManager = NotificationManagerCompat.from(this);

        //fusngi untuk menginstance MQTT
        pahoMqttClient = new PahoMqttClient();

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);

        //inisialiasi view
        //btnPublish = findViewById(R.id.btnPublish);
        btnPicker1 = findViewById(R.id.btnPicker1);
        btnPicker2 = findViewById(R.id.btnPicker2);
        btnPicker3 = findViewById(R.id.btnPicker3);
        btnPicker4 = findViewById(R.id.btnPicker4);

        btnUpload1 = findViewById(R.id.btnUpload1);
        btnUpload2 = findViewById(R.id.btnUpload2);
        btnUpload3 = findViewById(R.id.btnUpload3);
        btnUpload4 = findViewById(R.id.btnUpload4);


        tvTimeResult1 = findViewById(R.id.tvTimeResult1);
        tvTimeResult2 = findViewById(R.id.tvTimeResult2);
        tvTimeResult3 = findViewById(R.id.tvTimeResult3);
        tvTimeResult4 = findViewById(R.id.tvTimeResult4);

        edt_duration1 = findViewById(R.id.edt_duration1);
        edt_duration2 = findViewById(R.id.edt_duration2);
        edt_duration3 = findViewById(R.id.edt_duration3);
        edt_duration4 = findViewById(R.id.edt_duration4);

        //instance mqtt client
       client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);


       //fungsi untuk memanggi fungsu publish mqtt
//       btnPublish.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                publishMQTT();
//            }
//        });


       //untuk menampilkan date piker untuk mensetting jam ke 1
       btnPicker1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showTimeDialog1();

               status_set = "relay1";
           }
       });

        //untuk menampilkan date piker untuk mensetting jam ke 2
        btnPicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog1();

                status_set = "relay2";
            }
        });

        ////untuk menampilkan date piker untuk mensetting jam ke 3
        btnPicker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog1();

                status_set = "relay3";
            }
        });

        //berfungsi untuk mengirim waktu ke server MQTT untuk upload satu per satu
        btnUpload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String settingTime1 = tvTimeResult1.getText().toString().trim();
                String mDuration1 = edt_duration1.getText().toString().trim();
                String mSetTime1 = ("1."+settingTime1+":00." + mDuration1);

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

        //berfungsi untuk mengirim waktu ke server MQTT untuk upload satu per satu
        btnUpload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String settingTime2 = tvTimeResult2.getText().toString().trim();
                String mDuration2 = edt_duration2.getText().toString().trim();
                String mSetTime2 = ("2."+settingTime2+":00." + mDuration2);

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

        //berfungsi untuk mengirim waktu ke server MQTT untuk upload satu per satu
        btnUpload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String settingTime3 = tvTimeResult3.getText().toString().trim();
                String mDuration3 = edt_duration3.getText().toString().trim();
                String mSetTime3 = ("3."+settingTime3+":00." + mDuration3);

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

        startMqtt();//menjalankan MQTT ketika pertama kali aplikasi terbuka

    }

    //untuk mensubscribe ke MQTT dan mengambil pesan dari MQTT server
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
//
                String datSub = message.toString();
                //Pesan ditampilkan dan di kirim ke notifikasi
                sendOnChannel1(datSub);



            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


    }


        //untuk mengeluarkan pesan berupa notifikasi
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


    //berfungsi untuk mengirim waktu sekaligus ke MQTT
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
                pahoMqttClient.publishMessage(client, mSetTime1, 0, Constants.PUBLISH_TOPIC);//waktu ke 1
                pahoMqttClient.publishMessage(client, mSetTime2, 0, Constants.PUBLISH_TOPIC);//waktu ke 2
                pahoMqttClient.publishMessage(client, mSetTime3, 0, Constants.PUBLISH_TOPIC);//waktu ke 3
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    //untuk memunculkan settingan waktu, agar user bisa memilih waktu yang diinginkan, ini waktu ke 1
    public void showTimeDialog1(){
       Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {

                if(status_set.equals("relay1")){
                    tvTimeResult1.setText(String.format("%02d:%02d", hourOfDay, minutes));
                }else if (status_set.equals("relay2")){
                    tvTimeResult2.setText(String.format("%02d:%02d", hourOfDay, minutes));
                }else if(status_set.equals("relay3")){
                    tvTimeResult3.setText(String.format("%02d:%02d", hourOfDay, minutes));
                }


            }
        },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true
        );
        timePickerDialog.show();
    }


    //untuk memunculkan settingan waktu, agar user bisa memilih waktu yang diinginkan, ini waktu ke 2
    public void showTimeDialog2(){
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {

                tvTimeResult2.setText(String.format("%02d%02d", hourOfDay, minutes));

            }
        },
                calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true
        );
        timePickerDialog.show();
    }

    //untuk memunculkan settingan waktu, agar user bisa memilih waktu yang diinginkan, ini waktu ke 3
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