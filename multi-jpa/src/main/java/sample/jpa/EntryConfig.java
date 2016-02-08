/**
 * @(#)EntryConfig.java		2016/02/07
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
import org.springframework.boot.orm.jpa.EntityScan;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entryEM",
                       transactionManagerRef = "entryTM")
@EntityScan("sample.jpa.h2.dto")
public class EntryConfig {

    //@Autowired private JpaVendorAdapter venderAdapter;

    @Bean(name="entryTM")
    public PlatformTransactionManager entryTM() {
        return new JpaTransactionManager(entryEM().getObject());
    }

    @Bean(name="entryEM")
    public LocalContainerEntityManagerFactoryBean entryEM() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(entryDataSource());
		factoryBean.setJpaVendorAdapter(vendorAdapter);
		factoryBean.setPackagesToScan("sample.jpa.h2.repo");
		return factoryBean;
    }

    @Bean(name="entryDataSource")
    @ConfigurationProperties(prefix="h2.ds")
    DataSource entryDataSource() {

        System.out.println("|||||||||| entryDataSource----------");

        //return DataSourceBuilder.create().build();
        return new EmbeddedDatabaseBuilder().//
				setType(EmbeddedDatabaseType.HSQL).//
				setName("h2db-sample").//
				build();
    }

}
