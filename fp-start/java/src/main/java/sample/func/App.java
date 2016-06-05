/**
 * @(#)App.java		2016/02/09
 */
package sample.func;

import java.util.function.BiFunction;

public class App {

    public static Num n(Integer i) {return new Num(i);}

    public static void main(String[] args) {

        Num ten = n(10);
        Add ad = new Add();
        Mul ml = new Mul();

        BiFunction<Num,Num,Num> add = ad::calc;
        BiFunction<Num,Num,Num> mul = ml::calc;


        // partitial
        BiFunction<Num,Num,Num> pt1 = (k,j) -> mul.apply(add.apply(k,ten), j);

        System.out.println("pt1=" + pt1.apply(n(5), n(2)));

        F3<String,String,String,String> f3 = (a,b,c) -> a+b+c;

        System.out
            .println("SS="
                     + f3.apply("1", "2", "3"));
    }

}
