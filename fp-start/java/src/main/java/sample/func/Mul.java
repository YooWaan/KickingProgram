/**
 * @(#)Mul.java		2016/06/05
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

/*
 * Mul
 *
 * @author YooWaan
 */
public class Mul {

    public Num calc(Num n1, Num n2) {
        return new Num(n1.mul(n2.x()));
    }

}
