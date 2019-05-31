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

  public static final String clientId = "android";
    final String subcriptionTopic = "IOT/PAKAN/STATUS";

    final String username = "hbsqnuqo";
    final String password = "XqKZn1s52FiK";

    public MqttHelper(final Context context){


//        sharedpreferences = context.getSharedPreferences(myprefIP, Context.MODE_PRIVATE);
//        String ip = sharedpreferences.getString(Ip,"");
//        //String ipHis= "tcp://"+ip+":1883";//ganti 1883
        String ipHis = "tcp://m10.cloudmqtt.com:10611";

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
        connect();

    }


    public void setCallback(MqttCallbackExtended callback){
        mqttAndroidClient.setCallback(callback);
    }

    private void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subcribeToTopic();
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

    private void subcribeToTopic(){
        try {
            mqttAndroidClient.subscribe(subcriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Subcribed!");
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
}
