/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is.task.manager.server;

/**
 *
 * @author dmlr7
 */
// FTP Server
import is.task.manager.client.Client;
import is.task.manager.client.DataProject;
import is.task.manager.client.SingleData;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FTPServer extends Thread {

    public DBM dbm;

    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;
    DataProject dp;

    FTPServer(Socket soc) {
        dp = new DataProject();
        dp=dp.load();
        try {
            
            ClientSoc = soc;
            din = new DataInputStream(ClientSoc.getInputStream());
            dout = new DataOutputStream(ClientSoc.getOutputStream());
            System.out.println("FTP Client Connected ...");
            start();

        } catch (Exception ex) {
        }
    }

    void SendFile() throws Exception {
        dp.load();
        String filename = din.readUTF();
        File f = new File(filename);
        if (!f.exists()) {
            dout.writeUTF("File Not Found");
            return;
        } else {
            dout.writeUTF("READY");
            FileInputStream fin = new FileInputStream(f);
            int ch;
            do {
                ch = fin.read();
                dout.writeUTF(String.valueOf(ch));
            } while (ch != -1);
            fin.close();
            dout.writeUTF("File Receive Successfully");
        }
        try {
            FileInputStream fin = new FileInputStream(f);
            ObjectInputStream objin = new ObjectInputStream(fin);
            SingleData sd = (SingleData) (objin.readObject());
            dbm.insertProject(sd);
            dp.getProjects().add(sd);
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        dp.save();
    }

    void ReceiveFile() throws Exception {
        String filename = din.readUTF();
        if (filename.compareTo("File not found") == 0) {
            return;
        }
        File f = new File(filename);
        String option;

        if (f.exists()) {
            dout.writeUTF("File Already Exists");
            option = din.readUTF();
        } else {
            dout.writeUTF("SendFile");
            option = "Y";
        }

        if (option.compareTo("Y") == 0) {
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

            dout.writeUTF("File Send Successfully");
        } else {
            return;
        }

    }
    
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for Command ...");
                String Command = din.readUTF();
                if (Command.compareTo("GET") == 0) {
                    System.out.println("\tGET Command Received ...");
                    SendFile();
                    continue;
                } else if (Command.compareTo("SEND") == 0) {
                    System.out.println("\tSEND Command Receiced ...");
                    ReceiveFile();
                    continue;
                } else if (Command.compareTo("DISCONNECT") == 0) {
                    System.out.println("\tDisconnect Command Received ...");
                    System.exit(1);
                }
            } catch (Exception ex) {
            }
        }
    }
}
