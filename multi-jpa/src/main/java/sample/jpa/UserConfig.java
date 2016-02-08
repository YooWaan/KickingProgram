/**
 * @(#)UserConfig.java		2016/02/07
 */
package sample.jpa;

import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.orm.jpa.EntityScan;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "userEM",
                       transactionManagerRef = "userTM")
@EntityScan("sample.jpa.hsql.dto")
public class UserConfig {

    //@Autowired private JpaVendorAdapter venderAdapter;

    @Primary
    @Bean(name="userTM")
    public PlatformTransactionManager usreTM() {
        return new JpaTransactionManager(userEM().getObject());
    }

    @Primary
    @Bean(name="userEM")
    public LocalContainerEntityManagerFactoryBean userEM() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(userDataSource());
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setPackagesToScan("sample.jpa.hsql.repo");
		return factoryBean;
    }

    @Primary
    @Bean(name="userDataSource")
    @ConfigurationProperties(prefix="hsql.ds")
    DataSource userDataSource() {

        System.out.println("|||||||||| userDataSource----------");

        //return DataSourceBuilder.create().build();
        return new EmbeddedDatabaseBuilder().//
				setType(EmbeddedDatabaseType.HSQL).//
				setName("hsqldb-sample").//
				build();
    }

}
