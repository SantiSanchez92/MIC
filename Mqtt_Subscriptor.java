package ko2;


import java.io.File;
import java.io.FileOutputStream;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class Mqtt_Subscriptor implements MqttCallback {

		public static void main(String[] args) {
			String topic = "Proyecto/videos/fragmentos/#";
			int qos = 2;
			String broker = "tcp://localhost:1883";
			String clientId = "Video_SRM";
			MemoryPersistence persistence = new MemoryPersistence();

			try {
				MqttAsyncClient sampleClient = new MqttAsyncClient(broker, clientId, persistence);
				MqttConnectOptions connOpts = new MqttConnectOptions();
				connOpts.setCleanSession(true);
				sampleClient.setCallback(new Mqtt_Subscriptor());
				System.out.println("Connecting to broker: " + broker);
				sampleClient.connect(connOpts);
				System.out.println("Connected");
				Thread.sleep(1000);
				sampleClient.subscribe(topic, qos);
				System.out.println("Subscribed");
			} catch (Exception me) {
				if (me instanceof MqttException) {
					System.out.println("reason " + ((MqttException) me).getReasonCode());
				}
				
				me.printStackTrace();
			}
		}

		public void connectionLost(Throwable arg0) {
			System.err.println("connection lost");

		}

		
		public void messageArrived(String topic, MqttMessage message) throws Exception {
			System.out.println("topic: " + topic);
			System.out.println("message: " + new String(message.getPayload()));

			System.out.println(message.getQos());

			String ruta_final = System.getProperty("user.dir");

			
			File video = new File(ruta_final + "/VideosMQTT");
			video.mkdir();
			
			//C:/Users/Santi/VideosMQTT

			FileOutputStream fileOuputStream = new FileOutputStream(video + "/VideoMQTT");
			fileOuputStream.write(message.getPayload());
			fileOuputStream.close();

		}

		public void deliveryComplete(IMqttDeliveryToken arg0) {
			// TODO Auto-generated method stub
			System.err.println("delivery complete");
			
		}


}

