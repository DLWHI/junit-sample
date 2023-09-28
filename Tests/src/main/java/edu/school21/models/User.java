package edu.school21.models;

import edu.school21.exceptions.AlreadyAuthenticatedException;

public class User {
    private long id;
    private String login;
    private String passwd;
    private boolean authenticated;

    public User(String login, String passwd) {
        this.login = login;
        this.passwd = passwd;
    }

    public boolean authenticate(String passwd) {
        if (authenticated) {
            throw new AlreadyAuthenticatedException("User is already authed");
        }
        if (this.passwd.equals(passwd)) {
            authenticated = true;
        }
        return authenticated;
    }
}
