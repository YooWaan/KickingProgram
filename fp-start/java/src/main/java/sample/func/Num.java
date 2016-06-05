/**
 * @(#)Num.java		2016/06/05
 *
 * Copyright (c) 2016 YooWaan. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * BrainPad, Inc. ("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with YooWaan.
 */
package sample.func;

import java.util.function.Consumer;

/*
 * Num
 *
 * @author YooWaan
 */
public class Num {

    private Number number;
    private Consumer<Number> accepter;

    public Num(Number n) {
        this(n, (x) -> { /* noop */});
    }

    public Num(Number num, Consumer<Number> f) {
        this.number = num;
        this.accepter =f;
    }


    public Number x() {return this.number;}

    public Number add(Number x) {
        if (x instanceof Integer) {
            return set(Integer.valueOf(number.intValue() + x.intValue()));
        }
        if (x instanceof Long) {
            return set(Long.valueOf(number.longValue() + x.longValue()));
        }
        if (x instanceof Double) {
            return set(Double.valueOf(number.doubleValue() + x.doubleValue()));
        }
        return number;
    }

    public <X extends Number> Number mul(X x) {
        if (x instanceof Integer) {
            return set(Integer.valueOf(number.intValue() * x.intValue()));
        }
        if (x instanceof Long) {
            return set(Long.valueOf(number.longValue() * x.longValue()));
        }
        if (x instanceof Double) {
            return set(Double.valueOf(number.doubleValue() * x.doubleValue()));
        }
        return number;
    }

    private Number set(Number n) {
        this.number = n;
        return this.number;
    }

}
