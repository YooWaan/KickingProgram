package sample.func;

import java.util.function.Function;
import java.util.function.BiFunction;


interface F1<T, R> extends Function<T, R> {

    default R gen(T t) {
        return apply(t);
    }

}

interface F2<T, U, R> extends BiFunction<T, U, R> {

   default F1<U, R> gen(T p1) {
       return p2 -> apply(p1, p2);
   }

   default F1<T, F1<U, R>> curring() {
       return p1 -> p2 -> apply(p1, p2);
   }

}

interface F3<T, U, V, R> {

    R apply(T t, U u, V v);

    /** 部分適用 (1項目) */
    default F2<U, V, R> x(T p1) {
        return (p2, p3) -> apply(p1, p2, p3);
    }

    /** 部分適用 (2項目) */
    default F1<V, R> x(T p1, U p2) {
        return p3 -> apply(p1, p2, p3);
    }

    /** カーリー化 */
    default F1<T, F1<U, F1<V, R>>> currying() {
        return p1 -> p2 -> p3 -> apply(p1, p2, p3);
    }

}



