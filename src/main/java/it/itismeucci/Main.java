package it.itismeucci;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket s = new Socket("127.0.0.1", 3000);
        System.out.println("Connessione effettuata. Digita ESCI per uscire");
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        String send;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Inserisci la nota da memorizzare o digita LISTA per visualizzare le note salvate.");
            send = scanner.nextLine();
            switch (send) {
                case "LISTA":
                    String receive;
                    out.writeBytes("?" + "\n");
                    do {
                        receive = in.readLine();
                        System.out.println("-" + receive);
                    } while (!receive.equals("@"));
                    break;
                case "ESCI":
                    out.writeBytes("!" + "\n");
                    System.out.println("Comunicazione terminata");
                    break;
                default:
                    out.writeBytes(send + "\n");
                    if (in.readLine().equals("OK")) {
                        System.out.println("Nota salvata");
                    }
                    break;
            }
        } while (!send.equals("ESCI"));
        s.close();
    }
}