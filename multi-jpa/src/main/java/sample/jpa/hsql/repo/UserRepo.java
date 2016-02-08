/**
 * @(#)UserRepo.java		2016/02/07
 */
package sample.jpa.hsql.repo;

import org.springframework.data.repository.CrudRepository;

import sample.jpa.hsql.dto.User;

public interface UserRepo extends CrudRepository<User,Long> { }
