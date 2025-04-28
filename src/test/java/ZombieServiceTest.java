import com.epf.Persistance.Zombie;
import com.epf.Persistance.ZombieDAOImp;
import com.epf.Service.ZombieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ZombieServiceTest {
    @Mock
    private ZombieDAOImp zombieDAO;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private ZombieService zombieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        zombieService = new ZombieService(zombieDAO, jdbcTemplate);
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), any())).thenReturn(1);
    }

    @Test
    void testCreate() {
        Zombie zombie = new Zombie(1, "Zombie1", 100, 1.5, 10, 0.5, "zombie.png", 1);
        zombieService.create(zombie);
        verify(zombieDAO, times(1)).create(zombie);
    }

    @Test
    void testUpdate() {
        Zombie zombie = new Zombie(2, "Zombie2", 80, 4, 2, 0.5, "zombie2.png", 1);
        zombieService.update(zombie);
        verify(zombieDAO, times(1)).update(zombie);
    }

    @Test
    void testDelete() {
        Zombie zombie = new Zombie(3, "Zombie3", 70, 3, 2, 0.5, "zombie3.png", 2);
        zombieService.delete(zombie);
        verify(zombieDAO, times(1)).delete(zombie);
    }

    @Test
    void testFindById() {
        Zombie zombie = new Zombie(4, "Zombie4", 60, 2, 1, 1, "zombie4.png", 2);
        when(zombieDAO.findById(4)).thenReturn(Optional.of(zombie));

        Optional<Zombie> result = zombieService.findById(4);

        assertTrue(result.isPresent());
        assertEquals(zombie, result.get());
        verify(zombieDAO, times(1)).findById(4);
    }

    @Test
    void testFindAll() {
        List<Zombie> zombies = List.of(
            new Zombie(1, "Zombie1", 100, 5, 1, 1, "zombie.png", 1),
            new Zombie(2, "Zombie2", 80, 4, 2, 1, "zombie2.png", 1)
        );
        when(zombieDAO.findAll()).thenReturn(zombies);

        List<Zombie> result = zombieService.findAll();

        assertEquals(zombies, result);
        verify(zombieDAO, times(1)).findAll();
    }

    @Test
    void testFindByMapId() {
        int mapId = 3;
        List<Zombie> zombies = List.of(
            new Zombie(5, "ZombieMap", 90, 6, 1, 0.5, "zombie_map.png", mapId)
        );
        when(zombieDAO.findByMapId(mapId)).thenReturn(zombies);

        List<Zombie> result = zombieService.findByMapId(mapId);

        assertEquals(zombies, result);
        verify(zombieDAO, times(1)).findByMapId(mapId);
    }
}
