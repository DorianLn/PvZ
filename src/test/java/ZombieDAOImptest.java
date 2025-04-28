import com.epf.Persistance.ZombieDAOImp;
import com.epf.Persistance.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ZombieDAOImptest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ZombieDAOImp zombieDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Zombie zombie = new Zombie(1, "ZombieTest", 100, 1.5, 10, 0.5, "chemin/image.png", 1);

        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
            .thenAnswer(invocation -> {
                KeyHolder keyHolder = invocation.getArgument(1);
                Map<String, Object> keyMap = new HashMap<>();
                keyMap.put("id_zombie", 1);
                ((GeneratedKeyHolder) keyHolder).getKeyList().add(keyMap);
                return 1;
            });

        zombieDAO.create(zombie);

        verify(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    void testFindById() {
        Zombie zombie = new Zombie(1, "ZombieTest", 100, 1.5, 10, 0.5, "chemin/image.png", 1);
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(1))).thenReturn(zombie);

        Optional<Zombie> result = zombieDAO.findById(1);

        assertTrue(result.isPresent());
        assertEquals(zombie, result.get());
    }

    @Test
    void testFindAll() {
        List<Zombie> zombies = Arrays.asList(
                new Zombie(1, "Zombie1", 100, 1.5, 10, 0.5, "chemin1.png", 1),
                new Zombie(2, "Zombie2", 120, 1.0, 15, 0.4, "chemin2.png", 2)
        );
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(zombies);

        List<Zombie> result = zombieDAO.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void testGetByMapId() {
        List<Zombie> zombies = Arrays.asList(
                new Zombie(1, "Zombie1", 100, 1.5, 10, 0.5, "chemin1.png", 1),
                new Zombie(2, "Zombie2", 120, 1.0, 15, 0.4, "chemin2.png", 1)
        );
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1))).thenReturn(zombies);

        List<Zombie> result = zombieDAO.findByMapId(1);

        assertEquals(2, result.size());
    }

    @Test
    void testUpdate() {
        Zombie zombie = new Zombie(1, "ZombieTest", 150, 2.0, 20, 0.6, "nouveau_chemin.png", 2);
        

        when(jdbcTemplate.update(
            anyString(),
            anyString(), anyInt(), anyDouble(), anyInt(),
            anyDouble(), anyString(), anyInt(), anyInt()
        )).thenReturn(1);

        zombieDAO.update(zombie);

        verify(jdbcTemplate, times(1)).update(
            eq("UPDATE Zombie SET nom = ?, point_de_vie = ?, attaque_par_seconde = ?, degat_attaque = ?, vitesse_de_deplacement = ?, chemin_image = ?, id_map = ? WHERE id_zombie = ?"),
            eq("ZombieTest"), eq(150), eq(2.0), eq(20),
            eq(0.6), eq("nouveau_chemin.png"), eq(2), eq(1)
        );
    }

    @Test
    void testDelete() {
        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);
        Zombie zombie = new Zombie(1, "ZombieTest", 150, 2.0, 20, 0.6, "nouveau_chemin.png", 2);
        zombieDAO.delete(zombie);

        verify(jdbcTemplate, times(1)).update("DELETE FROM Zombie WHERE id_zombie = ?", zombie.getIdZombie());
    }
}
