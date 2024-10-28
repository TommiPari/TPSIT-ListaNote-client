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
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        Scanner scanner = new Scanner(System.in);
        String username;
        String result;
        do {
            System.out.println("Inserire username: ");
            username = scanner.nextLine();
            out.writeBytes(username + "\n");
            result = in.readLine();
            if (result.equals("NO")) {
                System.out.println("Username già inserito");
            }
        } while (result.equals("NO"));
        String send;
        System.out.println("Ciao, " + username + ", hai effettuato la connessione. Digita ESCI per uscire");
        do {
            System.out.println("Inserisci la nota da memorizzare o digita LISTA per visualizzare le note salvate.");
            System.out.println("Inserisci GLOBALE per condividerla con tutti");
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
                    out.writeBytes(username + "\n");
                    break;
                case "GLOBALE":
                    out.writeBytes("+" + "\n");    
                    System.out.println("Inserire la nota: ");
                    String globalNote = scanner.nextLine();
                    out.writeBytes(globalNote + "\n");
                    if (in.readLine().equals("OK")) {
                        System.out.println("Nota salvata");
                    }
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