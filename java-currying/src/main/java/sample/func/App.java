/**
 * @(#)App.java		2016/02/09
 *
 * Copyright (c) 2016 BrainPad, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * BrainPad, Inc. ("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with BrainPad, Inc.
 */
package sample.func;

public class App {

    public static void main(String[] args) {
        System.out.println("AAA");

        F3<String,String,String,String> f3 = (a,b,c) -> a+b+c;

        System.out
            .println("SS="
                     + f3.apply("1", "2", "3"));
    }

}
