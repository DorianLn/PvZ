import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.epf.Persistance.Effet;
import com.epf.Persistance.PlanteDAOImp;
import com.epf.Persistance.Plante;

import com.epf.Persistance.Zombie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.lang.reflect.Field;
import java.util.*;

public class PlanteDAOImptest {

    @Mock
    private JdbcTemplate jdbcTemplate; // Mock du JdbcTemplate

    @InjectMocks
    private PlanteDAOImp planteDao; // DAO à tester

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        planteDao = new PlanteDAOImp(jdbcTemplate);
    }


    @Test
    void testCreate() {
        Plante plante = new Plante(1, "Plante1", 100, 10.5, 20, 50, 0.5, Effet.NORMAL, "tournesol.png");
        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
            .thenAnswer(invocation -> {
                KeyHolder keyHolder = invocation.getArgument(1);
                Map<String, Object> keyMap = new HashMap<>();
                keyMap.put("id_plante", 1);
                ((GeneratedKeyHolder) keyHolder).getKeyList().add(keyMap);
                return 1;
            });

        planteDao.create(plante);
        verify(jdbcTemplate).update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    void testFindById() {
        Plante plante = new Plante(1, "Plante1", 100, Double.valueOf(10.5), 20, 50, Double.valueOf(0.5), Effet.NORMAL, "tournesol.png");
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), eq(1))).thenReturn(plante);

        Optional<Plante> result = planteDao.findById(1);

        assertTrue(result.isPresent());
        assertEquals(plante, result.get());
    }

    @Test
    public void testFindAll() {
        Plante planteMock = new Plante(1, "Plante1", 100, Double.valueOf(10.5), 20, 50, Double.valueOf(0.5), Effet.NORMAL, "tournesol.png");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(planteMock));

        List<Plante> result = planteDao.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Plante1", result.get(0).getNom());
        assertEquals(100, result.get(0).getPointDeVie());
        assertEquals(Effet.NORMAL, result.get(0).getEffet());

        verify(jdbcTemplate).query(anyString(), any(RowMapper.class));
    }

    @Test
    void testUpdate() {
        Plante plante = new Plante(1, "Plante1", 100, 10.5, 20, 50, 0.5, Effet.NORMAL, "tournesol.png");

        when(jdbcTemplate.update(
                anyString(),
                anyString(), anyInt(), anyDouble(), anyInt(),
                anyInt(), anyDouble(), anyString(), anyString(), anyInt()
        )).thenReturn(1);

        planteDao.update(plante);

        verify(jdbcTemplate, times(1)).update(
                anyString(),
                anyString(), anyInt(), anyDouble(), anyInt(),
                anyInt(), anyDouble(), anyString(), anyString(), anyInt()
        );    }

    @Test
    void testDelete() {
        when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(1);
        
        Plante plante = new Plante(1, "Plante1", 100, Double.valueOf(10.5), 20, 50, Double.valueOf(0.5), Effet.NORMAL, "tournesol.png");
        planteDao.delete(plante);

        verify(jdbcTemplate, times(1)).update("DELETE FROM Plante WHERE id_plante = ?", plante.getIdPlante());
    }
}
