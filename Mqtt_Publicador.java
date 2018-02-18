package ko;

import java.io.FileInputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.io.*;



public class Mqtt_Publicador {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String topic = "Proyecto/videos/fragmentos/";
		String content = "Message from MqttPublishSample";
		int qos = 2;
		String broker = "tcp://localhost:1883";
		String clientId = "SRMvideo";
		MemoryPersistence persistence = new MemoryPersistence();

		File file = new File("video.mp3");
		byte[] video = new byte[(int) file.length()];
		FileInputStream fichero = null;
		try {
			MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			System.out.println("Connecting to broker: " + broker);
			sampleClient.connect(connOpts);
			System.out.println("Connected");
			System.out.println("Publishing message: " + content);

			fichero = new FileInputStream(file);
			fichero.read(video);
			fichero.close();

			MqttMessage message = new MqttMessage(video);
			message.setQos(qos);

			sampleClient.publish(topic, message);
			System.out.println("Message published");

			sampleClient.disconnect();
			System.out.println("Disconnected");

			System.exit(0);

		} catch (MqttException me) {
			
			me.printStackTrace();
		}

	}
	
	
	
	
	

}
