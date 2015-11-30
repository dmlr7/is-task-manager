/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is.task.manager.client;

/**
 *
 * @author dmlr7
 */
// FTP Client

import java.net.*;
import java.io.*;
import java.util.*;

class FTPClient {

    public static DataProject getDataProject() throws Exception {
        Socket soc = new Socket("127.0.0.1", 5217);
        FTPClient t = new FTPClient(soc);
        t.dout.writeUTF("GET");
        DataProject dp = (DataProject)t.ReceiveFile("~/DataProject.dat");
        t.dout.writeUTF("DISCONNECT");
        return dp;
    }
    public static void getFile(String fileName) throws Exception {
        Socket soc = new Socket("127.0.0.1", 5217);
        FTPClient t = new FTPClient(soc);
        t.dout.writeUTF("GET");
        t.ReceiveFile(fileName);
        t.dout.writeUTF("DISCONNECT");
    }
    public static void sendFile(String fileName) throws Exception {
        Socket soc = new Socket("127.0.0.1", 5217);
        FTPClient t = new FTPClient(soc);
        t.dout.writeUTF("SEND");
        t.SendFile(fileName);
        t.dout.writeUTF("DISCONNECT");
    }
    
    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;
    BufferedReader br;

    FTPClient(Socket soc) {
        try {
            ClientSoc = soc;
            din = new DataInputStream(ClientSoc.getInputStream());
            dout = new DataOutputStream(ClientSoc.getOutputStream());
            br = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception ex) {
        }
    }

    void SendFile(String filename) throws Exception {
        
        File f = new File(filename);
        if (!f.exists()) {
            System.out.println("File not Exists...");
            dout.writeUTF("File not found");
            return;
        }

        dout.writeUTF(filename);

        String msgFromServer = din.readUTF();
        if (msgFromServer.compareTo("File Already Exists") == 0) {
            String Option;
            System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
            Option = "Y";
            if (Option == "Y") {
                dout.writeUTF("Y");
            } else {
                dout.writeUTF("N");
                return;
            }
        }

        System.out.println("Sending File ...");
        FileInputStream fin = new FileInputStream(f);
        int ch;
        do {
            ch = fin.read();
            dout.writeUTF(String.valueOf(ch));
        } while (ch != -1);
        fin.close();
        
        System.out.println(din.readUTF());

    }

    Object ReceiveFile(String fileName) throws Exception {
        
        dout.writeUTF(fileName);
        String msgFromServer = din.readUTF();

        if (msgFromServer.compareTo("File Not Found") == 0) {
            System.out.println("File not found on Server ...");
            return null;
        } else if (msgFromServer.compareTo("READY") == 0) {
            System.out.println("Receiving File ...");
            File f = new File(fileName);
            if (f.exists()) {
                String Option;
                System.out.println("File Already Exists. Want to OverWrite (Y/N) ?");
                Option = "Y";
                if (Option == "N") {
                    dout.flush();
                    return null;
                }
            }
            FileOutputStream fout = new FileOutputStream(f);
            int ch;
            String temp;
            do {
                temp = din.readUTF();
                ch = Integer.parseInt(temp);
                if (ch != -1) {
                    fout.write(ch);
                }
            } while (ch != -1);
            fout.close();
            ObjectOutputStream objout = new ObjectOutputStream(fout);
            Object obj = new ObjectInputStream(new FileInputStream(f));
            return obj;

        }
        return null;
        
    }

   
}
