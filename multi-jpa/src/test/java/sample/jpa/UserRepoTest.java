/**
 * @(#)UserRepoTest.java		2016/02/07
 */
package sample.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import sample.jpa.hsql.dto.User;
import sample.jpa.hsql.repo.UserRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Config.class, UserConfig.class,  })
@SqlGroup({
        @Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:hsql.sql")
    })
public class UserRepoTest {

    @Autowired private UserRepo repo;

    @Test
    public void testUsr() {

        repo.findAll().forEach(e -> {
                print(e);
            });

        User u = new User(1L, "test");
        print(u);
        repo.save(u);
    }

    void print(User u) {
        System.out.println("name=" + u.getName() + ", id=" + u.getId());
    }
}

@SpringBootApplication
class Config {

}
