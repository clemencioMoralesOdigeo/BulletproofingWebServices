package application.pojos;

import java.io.Serializable;

/**
 * Created by Clemencio Morales Lucas.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 10934921145154135L;

    private String name;
    private String salt;
    private String password;

    public User(final String name, final String salt, final String password) {
        this.setName(name);
        this.setSalt(salt);
        this.setPassword(password);
    }

    public User() {}

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(final String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
