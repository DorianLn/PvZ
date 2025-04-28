import com.epf.Persistance.Zombie;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_zombie {

    @Test
    public void testZombieCreation() {
        Zombie zombie = new Zombie("Zombie Basique", 200, 0.8, 15, 0.5, "zombie.png",1);

        assertEquals("Zombie Basique", zombie.getNom());
        assertEquals(200, zombie.getPointDeVie());
        assertEquals(0.8, zombie.getAttaqueParSeconde());
        assertEquals(15, zombie.getDegatAttaque());
        assertEquals(0.5, zombie.getVitesseDeplacement());
        assertEquals("zombie.png", zombie.getCheminImage());
        assertEquals(1, zombie.getIdMap());
    }

    @Test
    public void testSetters() {
        Zombie zombie = new Zombie("Zombie Rapide", 150, 1.2, 10, 1.0, "zombie_rapide.png",1);

        zombie.setNom("Zombie Ultra Rapide");
        zombie.setPointDeVie(180);
        zombie.setVitesseDeplacement(1.5);
        zombie.setIdMap(3);

        assertEquals("Zombie Ultra Rapide", zombie.getNom());
        assertEquals(180, zombie.getPointDeVie());
        assertEquals(1.5, zombie.getVitesseDeplacement());
        assertEquals(3, zombie.getIdMap());
    }
}
