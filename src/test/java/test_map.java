import com.epf.Persistance.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class test_map {
    @Test
    public void testMapCreation() {
        Map map = new Map(5, 10, "map.png");

        assertEquals(5, map.getLigne());
        assertEquals(10, map.getColonne());
        assertEquals("map.png", map.getCheminImage());
    }

    @Test
    public void testMapSetters() {
        Map map = new Map(2, 3, null);

        map.setLigne(7);
        map.setColonne(8);
        map.setCheminImage("new_map.png");

        assertEquals(7, map.getLigne());
        assertEquals(8, map.getColonne());
        assertEquals("new_map.png", map.getCheminImage());
    }

}

