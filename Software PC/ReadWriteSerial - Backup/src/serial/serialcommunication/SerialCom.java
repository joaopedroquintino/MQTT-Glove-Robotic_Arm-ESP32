/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serial.serialcommunication;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

/**
 *
 * @author joaoq
 */
public class SerialCom {

    protected String[] portas;
    protected Enumeration listaDePortas;

    public SerialCom() {
        listaDePortas = CommPortIdentifier.getPortIdentifiers();
    }

    public String[] ObterPortas() {
        return portas;
    }

    protected void ListarPortas() {
        int i = 0;
        portas = new String[10];
        while (listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips
                    = (CommPortIdentifier) listaDePortas.nextElement();
            portas[i] = ips.getName();
            i++;
        }
    }

    public boolean PortaExiste(String COMp) {
        String temp;
        boolean e = false;
        while (listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips = (CommPortIdentifier) listaDePortas.nextElement();
            temp = ips.getName();
            if (temp.equals(COMp) == true) {
                e = true;
            }
        }
        return e;
    }
}
