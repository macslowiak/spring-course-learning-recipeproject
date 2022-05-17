package guru.springframework.domain;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

    Category category;

    @Before
    public void setUp(){
        category = new Category();
    }

    @Test
    public void getId() {

        Long idValue = 4L;

        category.setId(idValue);

        assertEquals(idValue, category.getId());

    }

    @Test
    public void getDescription() {
    }

    @Test
    public void getRecipes() {
    }
}