package application.service;

import application.utils.PasswordUtils;
import application.repository.PasswordRepository;
import application.pojos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Clemencio Morales Lucas.
 */

@Service
public class PasswordService {

    private static final int NAME_MINIMUM_LENGTH = 6;
    private static final int PASSWORD_MINIMUM_LENGTH = 6;
    private static final String OK_MESSAGE = "OK";
    private static final String INVALID_LENGTH_MESSAGE = "Invalid user/password length. Minimum length is 6 chars";
    public static final String WELCOME_MESSAGE = "Welcome back, ";
    private static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials/invalid action.";
    private static final String USER_ALREADY_CREATED = "Can't create user (already created before)";

    @Autowired
    private PasswordRepository passwordRepository;

    public String createNewUserAndPassword(final String name, final String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(isUserRegistered(name)) {
            return USER_ALREADY_CREATED;
        }
        if (isValidInput(name, password)) {
            final String salt = PasswordUtils.getSalt();
            final String hashedPassword = PasswordUtils.getEncryptedPassword(password, salt);
            final String fullyEncriptedAndHashedPassword = PasswordUtils.getEncryptedPassword(hashedPassword + salt, salt);

            passwordRepository.createNewUserAndPassword(new User(name, salt, fullyEncriptedAndHashedPassword));
            return OK_MESSAGE;
        } else {
            return INVALID_LENGTH_MESSAGE;
        }
    }

    private boolean isValidInput(final String name, final String password) {
        return name.length() >= NAME_MINIMUM_LENGTH || (password.length() >= PASSWORD_MINIMUM_LENGTH);
    }

    public String loginUser(final String name, final String password) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        final User user = passwordRepository.readUser(name);
        if (!isUserRegistered(name)) {
            return WRONG_CREDENTIALS_MESSAGE;
        }
        final String encryptedPassword = PasswordUtils.getEncryptedPassword(password, user.getSalt());
        final String fullyEncryptedPassword = PasswordUtils.getEncryptedPassword(encryptedPassword + user.getSalt(),
                user.getSalt());

        return validCredentials(name, user, fullyEncryptedPassword) ? WELCOME_MESSAGE + name : WRONG_CREDENTIALS_MESSAGE;
    }

    private boolean validCredentials(final String name, final User user, final String fullyHashedPassword) {
        return user.getName().equals(name) && user.getPassword().equals(fullyHashedPassword);
    }

    private User readUser(final String username) {
        final User user = passwordRepository.readUser(username);
        return user.getName() == null ? null: user;
    }

    private boolean isUserRegistered(final String username) {
        return !(this.readUser(username) == null);
    }
}
