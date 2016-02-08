/**
 * @(#)EntryRepo.java		2016/02/07
 */
package sample.jpa.h2.repo;

import org.springframework.data.repository.CrudRepository;

import sample.jpa.h2.dto.Entry;

public interface EntryRepo extends CrudRepository<Entry,Long> { }
