package application.service;

import application.repository.EnemiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Clemencio Morales Lucas.
 */

@Service
public class EnemiesService {

    @Autowired
    private EnemiesRepository enemiesRepository;

    public String getEnemies() {
        return enemiesRepository.getEnemies();
    }
}
