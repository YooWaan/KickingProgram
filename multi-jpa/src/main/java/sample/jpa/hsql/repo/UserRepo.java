/**
 * @(#)UserRepo.java		2016/02/07
 *
 * Copyright (c) 2016 BrainPad, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * BrainPad, Inc. ("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with BrainPad, Inc.
 */
package sample.jpa.hsql.repo;

import org.springframework.data.repository.CrudRepository;

import sample.jpa.hsql.dto.User;

public interface UserRepo extends CrudRepository<User,Long> { }
