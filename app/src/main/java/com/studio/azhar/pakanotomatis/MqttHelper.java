package com.studio.azhar.pakanotomatis;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MqttHelper {

    //SharedPreferences sharedpreferences;
  //  public static final String myprefIP = "myip";
    public static final String Ip = "nameKey";
    public MqttAndroidClient mqttAndroidClient;


    // empat baris ini adalah topik2 MQTT yg dikirmkan ke server
    public static final String clientId = "android";
    final String subcriptionTopic = "IOT/PAKAN/STATUS"; //only for information pakan
    final String subcriptionTopic2 = "IOT/SETTING/STATUS";//only for information setting successs
    final String subcriptionTopic3 = "IOT/DOING/STATUS";//only for DOING ACTION machine1, 2, 3 successs

    final String username = "hbsqnuqo";//ini username server untuk konek ke MQTT broker
    final String password = "XqKZn1s52FiK";//ini password server untuk konek ke MQTT broker

    public MqttHelper(final Context context){


       // String ipHis = "tcp://m10.cloudmqtt.com:10611";//ini adalah URL mqtt

        String ipHis = "tcp://broker.hivemq.com:1883";//ini adalah URL mqtt

       // String ipHis = "tcp://192.168.1.4";//ini adalah URL mqtt


        //dibawah ini merupakan fungsi untuk mengambil pesan dari MQTT
        mqttAndroidClient = new MqttAndroidClient(context, ipHis, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.w("Mqtt",message.toString() );
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
        connect();//memanggil koneksi ke MQTT

    }


    public void setCallback(MqttCallbackExtended callback){
        mqttAndroidClient.setCallback(callback);
    }

    private void connect(){
        //dibawah ini merupakan fungsi - fungsi untuk mengkoneksikan ke MQTT server
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
//        mqttConnectOptions.setUserName(username);
//        mqttConnectOptions.setPassword(password.toCharArray());

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subcribeToTopic();//fungsi untuk memanggil topic ke 1
                    subcribeToTopic2();//fungsi untuk memanggil topic ke 2
                    subcribeToTopic3();//fungsi untuk memanggil topic ke 3
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + Ip + exception.toString());
                }
            });
        }catch (MqttException ex){
            ex.printStackTrace();
        }
    }

    //ini subcribe topic ke 1
    private void subcribeToTopic(){
        try {
            mqttAndroidClient.subscribe(subcriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Subcribed topic1!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Subcribed fail" + exception);
                }
            });
        }catch (MqttException ex){
            System.err.println("Exception whilst subcribing");
            ex.printStackTrace();
        }
    }

    //ini subcribe topic ke 2
    private void subcribeToTopic2(){
        try {

            mqttAndroidClient.subscribe(subcriptionTopic2, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("Mqtt", "Subcribed topic2");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        }catch (MqttException ex){
            System.err.println("Exception whilst subcribing");
            ex.printStackTrace();
        }
    }

    //ini subcribe topic ke 3
    private void subcribeToTopic3(){
        try{
            mqttAndroidClient.subscribe(subcriptionTopic3, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("Mqtt", "Subcribed topic3");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        }catch (MqttException ex){
            System.err.println("Exception whilst subcribing");
            ex.printStackTrace();
        }
    }
}
