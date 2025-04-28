import com.epf.Persistance.Effet;
import com.epf.Persistance.Plante;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_plante {

    @Test
    public void testPlanteCreation() {
        Plante plante = new Plante("Tournesol", 100, 0.5, 0, 50, 1.5, Effet.NORMAL, "tournesol.png");

        assertEquals("Tournesol", plante.getNom());
        assertEquals(100, plante.getPointDeVie());
        assertEquals(0.5, plante.getAttaqueParSeconde());
        assertEquals(0, plante.getDegatAttaque());
        assertEquals(50, plante.getCout());
        assertEquals(1.5, plante.getSoleilParSeconde());
        assertEquals(Effet.NORMAL, plante.getEffet());
        assertEquals("tournesol.png", plante.getCheminImage());
    }

    @Test
    public void testSetters() {
        Plante plante = new Plante("PistoPois", 120, 1.0, 20, 100, 0, Effet.SLOW_LOW, "pistopois.png");

        plante.setNom("Super PistoPois");
        plante.setPointDeVie(150);
        plante.setAttaqueParSeconde(1.2);
        plante.setEffet(Effet.SLOW_MEDIUM);

        assertEquals("Super PistoPois", plante.getNom());
        assertEquals(150, plante.getPointDeVie());
        assertEquals(1.2, plante.getAttaqueParSeconde());
        assertEquals(Effet.SLOW_MEDIUM, plante.getEffet());
    }
}
