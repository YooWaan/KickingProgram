
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

interface Var {
    String value();
};

interface Hex extends Var {
    String hex();
}

interface ToCase extends Var {
    String upper();
    String lower();
}

class InValue<T> implements Var {
    protected T value;

    public InValue(T v) {
		this.value = v;
    }

    public String value() {
		return this.value.toString();
	}

	public String toString() { return this.value(); }
}


class VO {
	protected Object obj;

	public VO(Object o) { this.obj = o; }

	public Object value() { return this.obj;}

	public String toString() { return this.obj != null ? this.obj.toString() : null; }
}

class VN extends VO {
	public VN(Integer i) { super(i); }
	public int intValue() { return this.obj != null ? ((Integer)this.obj).intValue(): 0; }
}

class VD extends VO {
	public VD(Double d) { super(d); }
	public double doubleValue() { return this.obj != null ? ((Double)this.obj).doubleValue(): 0.0d; }
}
class VS extends VO {
	public VS(String s) { super(s); }
	public String strValue() { return this.obj != null ? (String)this.obj : null; }
}


class Value<T> implements Var {
    protected T value;

    public Value(T v) {
		this.value = v;
    }

	public T val() { return this.value; }
    public String value() { return this.value.toString(); }
	public String toString() { return this.value(); }
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

/*
@FunctionalInterface
interface eval {
	String do(String a);
}
*/



public class g {

    public static void main(String[] args) {
		Num<Integer> n = new Num<>(Integer.valueOf(100));
		Str<String> s = new Str<>("Check");
		Num<Double> d = new Num<>(Double.valueOf(100));
		Str<StringBuilder> sb = new Str<>(new StringBuilder("helo"));


		VN vn = new VN(Integer.valueOf(11));
		VO vo = new VO(Double.valueOf(10.0d));
		VS vs = new VS("Value<String>");

		List<Var> vars = Arrays.asList(n, s, d, sb);
		List<? super Num<? extends Number>> svars = new ArrayList<>();
		svars.add(n); svars.add(d);  // svars.add(vn); error svars.add(s);

		List<VO> vos = new ArrayList<>();
		vos.add(vs);
		vos.add(vn);
		vos.add(vo);

		List<? extends VO> vexs = vos;


		// cant add s
		List<Num<?>> nums = Arrays.asList(n, d);

		// compile error
		//vars = nums;
		
		// ok
		List<? extends Value<?>> vex = Arrays.asList(n, s, d);

		//svars = vex;

		Predicate<Var> nofilterInvariant = (v) -> true;
		Predicate<? extends Var> nofilterEx = (v) -> true;
		Predicate<? extends Value<Number>> numFilter = (v) -> v.val() instanceof Number;

		List<String> results = Arrays.asList(
			g.<Var>eval(() -> vars, nofilterInvariant, (v) -> v.toString()),
			//g.<? extends Value<Number>>eval(() -> nums, v -> true, (v) -> v.toString()),
			g.<Num<?>>covariant(() -> nums, v -> true, (v) -> v.toString()),
			g.<VO>eval(() -> vos, v -> true, (v) -> v.toString()),
			g.<VO>eval(() -> (List<VO>)vexs, v -> true, (v) -> v.toString())
		);

		IntStream.range(0, results.size())
			.mapToObj(i -> String.format("[%d] %s", i, results.get(i)))
			.forEach(System.out::println);
	}

	static String format(List<String> ss) {
		return String.format("size[%d] %s", ss.size(), String.join(",", ss));
	}

	static <E> String eval(Supplier<List<E>> sup, Predicate<E> p, Function<E,String> fn) {
		Supplier<String> s = () -> {
			List<E> data = sup.get().stream().filter(p).collect(Collectors.toList());
			return String.format("cnt[%d] %s",
				data.size(),
				String.join(",", data.stream().map(fn).collect(Collectors.toList())) 
			);
		};
		return s.get();
	}

	static <E extends Value<? extends Number>> String covariant(Supplier<List<E>> sup, Predicate<E> p, Function<E,String> fn) {
		try {
			Supplier<String> s = () -> {
				List<E> data = sup.get().stream().filter(p).collect(Collectors.toList());
				return String.format("cnt[%d] %s",
					data.size(),
					String.join(",", data.stream().map(fn).collect(Collectors.toList())) 
				);
			};
			return s.get();
		} catch (Exception e) {
			return e.getClass().getName();
		}
	}


	static <E extends Object> boolean all(Object v) { return true; } 
	static <E extends Object> String val(Object v) { return v.toString(); }

	static <V extends Var> boolean allex(V v) { return true; } 
	static <V extends Var> String valex(V v) { return v.value(); }

	static boolean prednum(Num<?> n) { return n instanceof Num<?>; }

	static String numValue(Num<?> n) { return n.value(); }

}
