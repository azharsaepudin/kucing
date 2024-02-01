package com.studio.azhar.pakanotomatis;

public class Constants {

    //ini merupakan URL server MQTT
    //public static final String MQTT_BROKER_URL = "tcp://m10.cloudmqtt.com:10611";

    public static final String MQTT_BROKER_URL = "tcp://broker.hivemq.com:1883";

   // public static final String MQTT_BROKER_URL = "tcp://192.168.4.1:1883";

    //ini adalah topik yg dikirimkan ke MQTT untuk diterima oleh NodeMCU
    public static final String PUBLISH_TOPIC = "IOT";

    //client ini ini untuk identifikasi ke server MQTT, sehingga setiap device mempunyai Client ID yg berbeda
    public static final String CLIENT_ID = "androidClient";
}

//NOTE: paho MQTT client just for publish message, all setting broker here