package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    UsersRepository repo;

    public UsersServiceImpl(UsersRepository repo) {
        this.repo = repo;
    }

    boolean authenticate(String login, String password) 
            throws AlreadyAuthenticatedException {
        User usr = repo.findByLogin(login);
        if (usr == null || !usr.authenticate(password)) {
            return false;
        }
        repo.update(usr);
        return true;
    }
}
