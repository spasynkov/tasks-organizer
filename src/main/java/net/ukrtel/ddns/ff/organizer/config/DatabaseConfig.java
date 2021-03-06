package net.ukrtel.ddns.ff.organizer.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableJpaRepositories({"net.ukrtel.ddns.ff.organizer.repositories"})
@PropertySource("/WEB-INF/database.properties")
public class DatabaseConfig {
    private final Environment env;
    private final String domainPackages = "net.ukrtel.ddns.ff.organizer.domain";
    private final String dbType;
    private final String propertiesPrefix;

    @Autowired
    public DatabaseConfig(Environment env) {
        this.env = env;
        this.dbType = this.env.getRequiredProperty("db.type").toUpperCase();
        this.propertiesPrefix = "db." + this.dbType.toLowerCase() + ".";
    }

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getRequiredProperty(propertiesPrefix + "driver"));
        dataSource.setJdbcUrl(env.getRequiredProperty(propertiesPrefix + "url"));
        dataSource.setUser(env.getRequiredProperty(propertiesPrefix + "user"));
        dataSource.setPassword(env.getRequiredProperty(propertiesPrefix + "password"));

        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(20);
        dataSource.setAcquireIncrement(5);
        return dataSource;
    }

    @Bean   // setting JPA vendor as hibernate
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.valueOf(dbType));
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform(env.getRequiredProperty(propertiesPrefix + "dialect"));
        return adapter;
    }

    @Bean   // setting EntityManagerFactory
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {

        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        bean.setJpaProperties(properties);
        bean.setPackagesToScan(domainPackages);
        return bean;
    }

    @Bean   // setting JPA transaction manager
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(entityManagerFactory);
        return manager;
    }

    @Bean   // translating hibernate's exceptions into spring's ones
    public BeanPostProcessor persistenceTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
