
import com.epf.Persistance.Map;
import com.epf.Persistance.MapDAOImp;
import com.epf.Service.MapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MapServiceTest {

    @Mock
    private MapDAOImp mapDAO;

    @InjectMocks
    private MapService mapService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // initialise les mocks avant chaque test
    }

    @Test
    void testCreate() {
        Map map = new Map(1, 2, 3, "chemin1.png");
        mapService.create(map);
        verify(mapDAO).create(map);
    }

    @Test
    void testUpdate() {
        Map map = new Map(2, 1, 3, "chemin2.png");
        mapService.update(map);
        verify(mapDAO).update(map);
    }

    @Test
    void testDelete() {
        Map map = new Map(1, 2, 3, "chemin1.png");
        mapService.delete(map);
        verify(mapDAO).delete(map);
    }

    @Test
    void testFindById() {
        Map map = new Map(4, 2, 3, "chemin4.png");
        when(mapDAO.findById(4)).thenReturn(Optional.of(map));

        Optional<Map> result = mapService.findById(4);

        assertTrue(result.isPresent());
        assertEquals(map, result.get());
        verify(mapDAO).findById(4);
    }

    @Test
    void testFindAll() {
        List<Map> maps = List.of(
                new Map(1, 2, 3, "chemin1.png"),
                new Map(2, 2, 3, "chemin2.png")
        );
        when(mapDAO.findAll()).thenReturn(maps);

        List<Map> result = mapService.findAll();

        assertEquals(maps, result);
        verify(mapDAO).findAll();
    }
}
