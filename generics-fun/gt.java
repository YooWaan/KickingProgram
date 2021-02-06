
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.DisplayName;


@DisplayName("test::g.class")
public class gt {

    void p(String s) { System.out.println(s);}

    void print(List<?> l) {
        System.out.println(
        l.stream().map(v -> v.toString())
        .reduce("", (t, e) -> t.length() == 0 ? e :t + "," + e )
        );
    }

    @Test
    @Tag("invarient")
    void confirmInvarient() {
		ValueObject vo = new ValueObject(Boolean.TRUE);
		ValueStr vs = new ValueStr("Hello");
        ValueInt vi = new ValueInt(Integer.valueOf(123));
        
        List<ValueInt> list = new ArrayList<>();
        list.add(vi);
        //list.add(vs); //compile error
        //list.add(vo); //compile error
        //List<ValueInt> alist = Arrays.asList(vi, vs, vo); // compile error

        // Covarient?
        List<ValueObject> olst = new ArrayList<>();
        // List<ValueObject> ol = new ArrayList<ValueStr>();
        // type is invarient
        olst.add(vi);
        olst.add(vs);
        olst.add(vo);

        assertEquals(1, list.size());
        assertEquals(3, olst.size());

        p("invarient....");
        print(list);
        print(olst);
    }

    @Test
    @Tag("covarient")
    void confirmCovarient() {
		ValueObject vo = new ValueObject(Boolean.TRUE);
		ValueStr vs = new ValueStr("Hello");
        ValueInt vi = new ValueInt(Integer.valueOf(123));

        // create mutable list
        List<ValueObject> all = new ArrayList<>(Arrays.asList(vo, vs, vi));
        List<ValueObject> intstr = new ArrayList<>(Arrays.asList(vs, vi));
        List<ValueStr> two = new ArrayList<>(Arrays.asList(vs, vi));

        List<? extends ValueStr> list = two;
        //List<? extends ValueStr> list = all,intstr;
        //list.add(vi); // compile error
        //list.add(vs); // compile error
        //list.add(vo); // compile error

        p("covarient....");
        print(list);

        assertEquals(2, list.size());
        // can get item at index
        assertEquals("Hello", list.get(0).strValue());
        assertEquals("123", list.get(1).strValue());

        List<ValueInt> num = Arrays.asList(vi, vi);
        // num can not add ValueStr,Object
        list = num; //List<? extends ValueStr> 
        //list.add(vs); // compile error
        //list.add(vo); // compile error

        print(list);
        assertEquals(2, list.size());
        // can get item at index
        assertEquals("123", list.get(0).strValue());
        assertEquals("123", list.get(1).strValue());
    }

    @Test
    @Tag("contravarient")
    void confirmContravarient() {
        ValueObject vo = new ValueObject(Boolean.TRUE);
		ValueStr vs = new ValueStr("Hello");
        ValueInt vi = new ValueInt(Integer.valueOf(123));
 
        List<Object> all = new ArrayList<>(Arrays.asList(vo, vs, vi));

        List<? super ValueStr> vlst = all;
        //vlst.add(vo); // compile error
        vlst.add(vi);
        vlst.add(vs);

        p("contravarient....");
        print(vlst);

        List<? super Object> list = all;
        // become type unsafe list
        list.add(Integer.valueOf(111));
        list.add(Boolean.FALSE);
        list.add(List.class);

        print(list);
    }

}