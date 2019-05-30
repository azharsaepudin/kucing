package com.studio.azhar.pakanotomatis;

import android.content.Context;
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

    public MqttAndroidClient mqttAndroidClient;

    final String serverURI = "tcp://m10.cloudmqtt.com:10611";

    final String clientId = "android";
    final String subscriptionTopic = "IOT/PAKAN/OTOMATIS";

    final String username = "hbsqnuqo";
    final String password = "XqKZn1s52FiK";

    public MqttHelper(final Context context){

       mqttAndroidClient = new MqttAndroidClient(context, serverURI, clientId);
       mqttAndroidClient.setCallback(new MqttCallbackExtended() {
           @Override
           public void connectComplete(boolean reconnect, String serverURI) {

           }

           @Override
           public void connectionLost(Throwable cause) {

           }

           @Override
           public void messageArrived(String topic, MqttMessage message) throws Exception {

           }

           @Override
           public void deliveryComplete(IMqttDeliveryToken token) {

           }
       });
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

        try{
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("MQTT", "failed to connect to server");
                }
            });
        }catch (MqttException ex){
            ex.printStackTrace();
        }
    }

    private void subscribeToTopic(){
        try{
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("mqtt", "subscribed");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("mqtt", "subscribe failed");
                }
            });
        }catch (MqttException ex){
            System.err.println("Exception whilst subscibing");
            ex.printStackTrace();
        }
    }
}
