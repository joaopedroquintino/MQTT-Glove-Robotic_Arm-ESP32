package mqtt;

/**
 *
 * @author Joao Pedro Quintino
 */
import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Ouvinte implements IMqttMessageListener {

    public Ouvinte(ClienteMQTT clienteMQTT, String topico, int qos) {
        clienteMQTT.subscribe(qos, this, topico);
    }

    @Override
    public void messageArrived(String topico, MqttMessage mm) throws Exception {
        byte msg[];
        msg = (mm.getPayload());
        int c[] = {0,0,0,0};
        c[0] = mm.getPayload()[0];
        c[1] = mm.getPayload()[1];
        c[2] = mm.getPayload()[2];
        if(msg.length >= 4)
        {
            c[3] = mm.getPayload()[3];
        }else{
            c[3] = 0;
        }
        System.out.println("Mensagem recebida:");
        System.out.println("\tX: " + c[0] +"\tY: "+ c[1] + "\tZ: " + c[2] + "\tGarra: " + c[3]);
        System.out.println("");
        JFMain.setLabel(c);
    }

}
