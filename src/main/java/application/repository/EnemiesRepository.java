package application.repository;

import org.springframework.stereotype.Repository;

/**
 * Created by Clemencio Morales Lucas.
 */

@Repository
public class EnemiesRepository {

    private final static String ENEMIES_JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJBbGZyZWQgSi4gUGVubnl3b3" +
            "J0aCIsImVuZW1pZXMiOiJUaGUgSm9rZXIsIEJhbmUsIENhdHdvbWFuLCBEZWF0aFN0cm9rZSwgS2lsbGVyY3JvYywgVHdvLUZhY2Uif" +
            "Q.JD4K9FH_HBbQQp2u6B2BWE0amt87bbHBM67Qhs8mO2E";

    public String getEnemies() {
        return EnemiesRepository.ENEMIES_JWT_TOKEN;
    }
}
