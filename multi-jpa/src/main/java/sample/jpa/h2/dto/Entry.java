/**
 * @(#)Entry.java		2016/02/07
 *
 * Copyright (c) 2016 BrainPad, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * BrainPad, Inc. ("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with BrainPad, Inc.
 */
package sample.jpa.h2.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="entry")
public class Entry {

    private @Id @GeneratedValue @Column(name="id") Long id;

    private @Column(name="title") String title;

    private @Column(name="body") String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String t) {
        this.title = t;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String b) {
        this.body = b;
    }
}
