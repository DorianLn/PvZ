import com.epf.Persistance.Map;
import com.epf.Persistance.MapDAOImp;
import com.epf.Persistance.Zombie;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.PreparedStatementCreator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MapDAOImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MapDAOImp mapDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Map map = new Map(0, 2, 3, "chemin/image.png");
        ArgumentCaptor<KeyHolder> keyHolderCaptor = ArgumentCaptor.forClass(KeyHolder.class);

        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class))).thenAnswer(invocation -> {
            KeyHolder keyHolder = invocation.getArgument(1);
            java.lang.reflect.Field keyField = GeneratedKeyHolder.class.getDeclaredField("keyList");
            keyField.setAccessible(true);
            keyField.set(keyHolder, Arrays.asList(java.util.Collections.singletonMap("id_map", 1)));
            return 1;
        });

        Map result = mapDAO.create(map);

        assertNotNull(result);
        assertEquals(1, result.getIdMap());
        verify(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    void testFindById() {
        Map map = new Map(1, 2, 3, "chemin/image.png");
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(1))).thenReturn(map);

        Optional<Map> result = mapDAO.findById(1);

        assertTrue(result.isPresent());
        assertEquals(map, result.get());
    }

    @Test
    void testFindAll() {
        List<Map> maps = Arrays.asList(
                new Map(1, 2, 3, "chemin1.png"),
                new Map(2, 4, 5, "chemin2.png")
        );
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(maps);

        List<Map> result = mapDAO.findAll();

        assertEquals(2, result.size());
        assertEquals(maps, result);
    }

    @Test
    void testUpdate() {
        Map map = new Map(1, 2, 3, "nouveau_chemin.png");
        when(jdbcTemplate.update(
            anyString(),
            anyInt(),
            anyInt(),
            anyString(),
            anyInt()
        )).thenReturn(1);

        Map result = mapDAO.update(map);

        assertNotNull(result);
        assertEquals(map, result);
        verify(jdbcTemplate).update(anyString(), eq(2), eq(3), eq("nouveau_chemin.png"), eq(1));
    }

    @Test
    void testDelete() {
        Map map = new Map(1, 2, 3, "chemin.png");
        when(jdbcTemplate.update(anyString(), eq(1))).thenReturn(1);
        mapDAO.delete(map);
        verify(jdbcTemplate).update(anyString(), eq(1));
    }

    @Test
    void testFindZombiesByMap() {
        List<Zombie> zombies = Arrays.asList(
            new Zombie(1, "Zombie1", 100, 1.5, 10, 0.5, "zombie1.png", 1),
            new Zombie(2, "Zombie2", 120, 1.0, 15, 0.4, "zombie2.png", 1)
        );
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), eq(1))).thenReturn(zombies);
        List<Zombie> result = mapDAO.findZombiesByMap(1);

        assertEquals(2, result.size());
        assertEquals(zombies, result);
    }
}
