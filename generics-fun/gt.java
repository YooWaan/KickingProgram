

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


@DisplayName("test::g.class")
public class gt {

    @Test
    void check() {

        System.out.println("test example");
        assertEquals(3, Integer.valueOf(3));

        ValueInt i = new ValueInt(100);
        assertEquals("100", i.toString());
    }


    void confirmInvarient() {
    }


    void confirmCovarient() {
    }

    void confirmContravarient() {
    }

}