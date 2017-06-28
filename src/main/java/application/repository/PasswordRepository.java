package application.repository;

import application.pojos.User;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import org.slf4j.Logger;

/**
 * Created by Clemencio Morales Lucas.
 */

@Repository
public class PasswordRepository {

    private static final String DATA_DIRNAME = "data";
    private static final String DATA_FOLDER = DATA_DIRNAME + "/";
    private static final String DAT_EXTENSION = ".dat";
    private static final String CURRENT_DIRECTORY = ".";
    private static final String USER = "User: ";
    private static final String CANNOT_BE_CREATED = " cannot be created.";
    private static final String DOES_NOT_EXIST = " does not exist.";
    private static String PATH;

    private Logger logger = LoggerFactory.getLogger(PasswordRepository.class);

    static {
        final String rawPath = new File(CURRENT_DIRECTORY).getAbsoluteFile().getPath();
        PasswordRepository.PATH = rawPath.substring(0, rawPath.length()-1);
    }

    private static void assembleRepositoryDirectoryIfNotExists() {
        File file = new File(DATA_DIRNAME);
        if(!file.exists()) {
            file.mkdir();
        }
    }

    public void createNewUserAndPassword(final User user) {
        PasswordRepository.assembleRepositoryDirectoryIfNotExists();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PasswordRepository.PATH + DATA_FOLDER +
                    user.getName() + DAT_EXTENSION);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
        } catch (IOException e) {
            logger.info(USER + user.getName() + CANNOT_BE_CREATED);
        }
    }

    public User readUser(final String username) {
        PasswordRepository.assembleRepositoryDirectoryIfNotExists();
        User user = new User();
        try {
            FileInputStream fin = new FileInputStream(PasswordRepository.PATH + DATA_FOLDER + username + DAT_EXTENSION);
            ObjectInputStream ois = new ObjectInputStream(fin);
            user = (User) ois.readObject();
            ois.close();
        }
        catch (Exception e) {
            logger.info(USER + username + DOES_NOT_EXIST);
        }
        return user;
    }
}
