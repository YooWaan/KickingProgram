
import java.util.List;
import java.util.Arrays;

interface Var {
    String value();
};

interface Hex {
    String hex();
}

interface ToCase {
    String upper();
    String lower();
}

class Value<T> implements Var {
    protected T value;

    public Value(T v) {
	this.value = v;
    }

    public String value() {
	return this.value.toString();
    }
};


class Num <N extends Number> extends Value<N> implements Hex {

    public Num(N v) {
	super(v);
    }
    
    public String hex() {
	return String.format("0x%08x", this.value.intValue());
    }
}

class Str <S extends CharSequence> extends Value<S> implements ToCase {

    public Str(S s) { super(s); }
    
    public String upper() { return this.value.toString().toUpperCase(); }
    public String lower() { return this.value.toString().toLowerCase(); }
}



public class g {

    public static void main(String[] args) {
	Num<Integer> n = new Num<>(Integer.valueOf(100));
	Str<String> s = new Str<>("Check");
	Num<Double> d = new Num<>(Double.valueOf(100));

	System.out.println(n.value() + "\n" + n.hex());
	System.out.println(d.value() + "\n" + d.hex());
	System.out.println(s.value() + "\n" + s.upper());


	List<Var> vars = Arrays.asList(n, s, d);
	print(vars);
	List<? super Var> svars = Arrays.asList(n, s, d);
	print(svars);

	// cant add s
    	List<? extends Num<?>> nums = Arrays.asList(n, d);
	print(nums);

	// compile error
	//vars = nums;
	
	// ok
    	List<? extends Value<?>> vex = Arrays.asList(n, s, d);
	print(vex);

	svars = vex;
	print(vars);

    }

    static void print(List<? extends Var> vs) {
	char s = ' ';
	for (Var v : vs) {
	    System.out.print(s + v.value());
	    s = ',';
	}
	System.out.println("");
    }

    static void print(List<? extends Var> vs) {
	char s = ' ';
	for (Var v : vs) {
	    System.out.print(s + v.value());
	    s = ',';
	}
	System.out.println("");
    }

}
