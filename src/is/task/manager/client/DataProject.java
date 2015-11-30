/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is.task.manager.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dmlr7
 */
public class DataProject implements Serializable{
    private ArrayList<SingleData> projects;

    /**
     * @return the projects
     */
    public ArrayList<SingleData> getProjects() {
        return projects;
    }

    /**
     * @param projects the projects to set
     */
    public void setProjects(ArrayList<SingleData> projects) {
        this.projects = projects;
    }
    public void save(){
        try {
            FileOutputStream fos = new FileOutputStream("~/DataProject.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (Exception ex) {
            Logger.getLogger(DataProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public DataProject load() {
        
        try {
            FileInputStream fos = new FileInputStream("~/DataProject.dat");
            ObjectInputStream oos = new ObjectInputStream(fos);
            return (DataProject)oos.readObject();
        } catch (Exception ex) {
            Logger.getLogger(DataProject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
}