/**
 * @(#)EntryRepoTest.java		2016/02/07
 *
 * Copyright (c) 2016 BrainPad, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * BrainPad, Inc. ("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with BrainPad, Inc.
 */
package sample.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration; 
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;

import sample.jpa.h2.dto.Entry;
import sample.jpa.h2.repo.EntryRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { EConfig.class, EntryConfig.class })
/*
@SqlGroup({
        @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:h2.sql")
    })
*/
public class EntryRepoTest {

    @Autowired private EntryRepo repo;

    @Test
    public void testEnt() {
        repo.findAll().forEach(e -> {
                print(e);
            });
    }

    void print(Entry u) {
        System.out.println("title=" + u.getTitle() + ", body=" + u.getBody());
    }


}

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class , DataSourceTransactionManagerAutoConfiguration.class })
class EConfig { }
