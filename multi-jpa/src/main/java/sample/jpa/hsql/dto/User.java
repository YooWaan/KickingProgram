/**
 * @(#)User.java		2016/02/07
 */
package sample.jpa.hsql.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="user")
public class User {

    private @Id @GeneratedValue @Column(name="id") Long id;

    private @Column(name="name") String name;

    public User() {
        this(null, null);
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String n) {
        this.name = n;
    }

}
