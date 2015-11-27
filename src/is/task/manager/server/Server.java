/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is.task.manager.server;

import java.net.ServerSocket;

/**
 *
 * @author dmlr7
 */
public class Server {
    public static void main(String args[]) throws Exception {
        DBM dbm = new DBM();
        dbm.createConnection();
        ServerSocket soc = new ServerSocket(5217);
        System.out.println("FTP Server Started on Port Number 5217");
        while (true) {
            System.out.println("Waiting for Connection ...");
            FTPServer t = new FTPServer(soc.accept());
            t.dbm=dbm;
        }
    }
}
