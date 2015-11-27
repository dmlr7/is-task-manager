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
public final class SingleData {

    private String name;
    private String clientId;
    private String description;
    private String version;
    private String author;
    private String file;

    public SingleData(String name, String clientId, String description, String version,String author) {
        setName(name);
        setClientId(clientId);
        setDescription(description);
        setAuthor(author);
        setVersion(version);
    }

    public SingleData(String name, String clientId, String description,String version, String author, String file) {
        setName(name);
        setClientId(clientId);
        setDescription(description);
        setAuthor(author);
        setFile(file);
        setVersion(version);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

}
