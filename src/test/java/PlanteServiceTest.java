import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.epf.Persistance.Effet;
import com.epf.Persistance.Plante;
import com.epf.Persistance.PlanteDAOImp;
import com.epf.Service.PlanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class PlanteServiceTest {

    @Mock
    private PlanteDAOImp planteDAO;

    @InjectMocks
    private PlanteService planteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        Plante plante = new Plante(1, "Plante1", 100, 10.5, 20, 50, 0.5, Effet.NORMAL, "tournesol.png");

        planteService.create(plante);

        verify(planteDAO, times(1)).create(plante);
    }

    @Test
    void testUpdate() {
        Plante plante = new Plante(1, "Plante1", 100, 10.5, 20, 50, 0.5, Effet.NORMAL, "tournesol.png");

        planteService.update(plante);

        verify(planteDAO, times(1)).update(plante);
    }

    @Test
    void testFindById() {
        Plante plante = new Plante(1, "Plante1", 100, 10.5, 20, 50, 0.5, Effet.NORMAL, "tournesol.png");
        when(planteDAO.findById(1)).thenReturn(Optional.of(plante));

        Optional<Plante> result = planteService.findById(1);

        verify(planteDAO, times(1)).findById(1);
        assertTrue(result.isPresent());
        assertEquals(plante, result.get());
    }

    @Test
    void testFindAll() {
        Plante plante1 = new Plante(1, "Plante1", 100, 10.5, 20, 50, 0.5, Effet.NORMAL, "tournesol.png");
        Plante plante2 = new Plante(2, "Plante2", 200, 20.5, 30, 60, 1.0, Effet.NORMAL, "rose.png");
        List<Plante> plantes = List.of(plante1, plante2);
        when(planteDAO.findAll()).thenReturn(plantes);

        List<Plante> result = planteService.findAll();

        verify(planteDAO, times(1)).findAll();
        assertEquals(2, result.size());
        assertTrue(result.contains(plante1));
        assertTrue(result.contains(plante2));
    }



}
