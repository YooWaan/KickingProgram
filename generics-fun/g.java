
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.Collectors;


class ValueObject {
	protected Object value;
	ValueObject(Object o) { this.value = o; }
	Object value() { return this.value; }
	public String toString() { return this.value == null ? "null" : this.value.toString(); }
}

class ValueStr extends ValueObject {
	ValueStr(Object o) { super(o); }
	ValueStr(String s) { super(s); }
	String strValue() { return this.toString(); }
}

class ValueInt extends ValueStr {
	ValueInt(Object o) { super(o); }
	ValueInt(String s) { this(Integer.parseInt(s)); }
	ValueInt(Integer i) {super(i);}

	Integer intValue() { return this.value == null ? Integer.MIN_VALUE : (Integer)this.value ; }
	String hex() { return String.format("0x%08x", this.intValue()); }
}


public class g {

    public static void main(String[] args) {

		ValueObject vo = new ValueObject(Boolean.TRUE);
		ValueStr vs = new ValueStr("Hello");
		ValueInt vi = new ValueInt(Integer.valueOf(123));

		ArrayList<? extends Object> ary = new ArrayList<ValueObject>();

		List<ValueObject> lo = (List<ValueObject>)ary;
		lo.add(vo);
		lo.add(vs);
		lo.add(vi);

		List<? super ValueObject> vexs = (List<Object>)ary.clone();
		vexs.add(vi);
		vexs.add(vs);
		vexs.add(vo);

		List<? extends ValueObject> es = (List<? extends ValueObject>)ary.clone();
		//es.add(vi); es.add(vs); es.add(vo);

		List<String> results = Arrays.asList(
			g.join(lo.stream().map(v -> v.toString())),
			// 
			g.join(vexs.stream().map(v -> v/* can not use .value()*/.toString())),
			// covarient
			g.join(es.stream().map(v -> v.value().toString()))
		);

		IntStream.range(0, results.size())
			.mapToObj(i -> String.format("[%d] %s", i, results.get(i)))
			.forEach(System.out::println);
	}

	static String join(Stream<String> ss) {
		List<String> data = ss.collect(Collectors.toList());
		return String.format("cnt[%d] %s", data.size(), String.join(",", data));
	}
}
