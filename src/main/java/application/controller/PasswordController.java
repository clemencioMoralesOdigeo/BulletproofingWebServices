package application.controller;

import application.service.EnemiesService;
import application.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Clemencio Morales Lucas.
 */

@RestController
public class PasswordController implements ErrorController {

    private static final String ERROR_PATH = "/error";
    private static final String BAD_REQUEST_MESSAGE = "Bad Request ¯\\_(ツ)_/¯";
    private static final String SCARY_MESSAGE = "You better get away, boy (ง ͠° ͟ل͜ ͡°)ง";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String BATMAN_NAME = "batman";
    private static final String AMPERSAND_SYMBOL = "&";
    private static final String EQUALS_SYMBOL = "=";
    private static final String UTF_8 = "UTF-8";
    private static final String LOGOUT_MESSAGE = "Bye!";

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private EnemiesService enemiesService;

    private boolean batmanPresent = false;

    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String createNewUserAndPassword(@RequestBody final String body) {
        try {
            Map<String, String> queryStringMap = splitQuery(body);
            final String name = queryStringMap.get(USER);
            final String password = queryStringMap.get(PASSWORD);
            return passwordService.createNewUserAndPassword(name, password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            return ERROR_PATH;
        }
    }

    @RequestMapping(value = "user/login", method = RequestMethod.POST)
    public String loginUser(@RequestBody final String body) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, String> queryStringMap = splitQuery(body);
        final String name = queryStringMap.get(USER);
        final String password = queryStringMap.get(PASSWORD);
        final String result = passwordService.loginUser(name, password);
        if (result.contains(PasswordService.WELCOME_MESSAGE + BATMAN_NAME)) {
            this.batmanPresent = true;
        }
        return result;
    }

    @RequestMapping(value = "getEnemies", method = RequestMethod.GET)
    public String getEnemies() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (batmanPresent) {
            return enemiesService.getEnemies();
        } else {
            return SCARY_MESSAGE;
        }
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        this.batmanPresent = false;
        return LOGOUT_MESSAGE;
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping("/error")
    public String error() {
        return BAD_REQUEST_MESSAGE;
    }

    private static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<>();
        String[] pairs = query.split(AMPERSAND_SYMBOL);
        for (String pair : pairs) {
            int idx = pair.indexOf(EQUALS_SYMBOL);
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), UTF_8),
                    URLDecoder.decode(pair.substring(idx + 1), UTF_8));
        }
        return query_pairs;
    }
}
