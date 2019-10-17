/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serial;

/**
 *
 * @author joaoq
 */
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Ouvinte implements IMqttMessageListener {

    public Ouvinte(ClienteMQTT clienteMQTT, String topico, int qos) {
        clienteMQTT.subscribe(qos, this, topico);
    }

    @Override
    public void messageArrived(String topico, MqttMessage mm) throws Exception {
        System.out.println("Mensagem recebida:");
        byte c[]={0,0,0,0};
        c[0] = mm.getPayload()[0];
        c[1] = mm.getPayload()[1];
        c[3] = mm.getPayload()[3];
        String x = new Integer(mm.getPayload()[0]).toString();
        String y = new Integer(mm.getPayload()[1]).toString();
        String z = new Integer(mm.getPayload()[2]).toString();
        String g = new Integer(mm.getPayload()[3]).toString();
        System.out.println("\tX: " + c[0] +"\tY: "+ y + "\tZ: " + z + "\tGarra: " + g);
        System.out.println("");
        String message[] = {x,y,z,g};
        JFMain.setLabel(message, c);
    }

}
