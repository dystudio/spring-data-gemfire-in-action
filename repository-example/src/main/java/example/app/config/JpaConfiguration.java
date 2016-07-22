package example.app.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import example.app.model.Contact;

/**
 * Spring {@link Configuration} class used to configure and bootstrap a {@link DataSource} along with configuring
 * a JPA {@link javax.persistence.EntityManager}
 *
 * @author John Blum
 * @see javax.persistence.EntityManagerFactory
 * @see javax.sql.DataSource
 * @see org.springframework.context.annotation.Bean
 * @see org.springframework.context.annotation.Configuration
 * @see org.springframework.context.annotation.Profile
 * @see org.springframework.data.jpa.repository.config.EnableJpaRepositories
 * @see org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
 * @see org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
 * @see org.springframework.orm.jpa.JpaTransactionManager
 * @see org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
 * @since 1.0.0
 */
@Configuration
@EnableJpaRepositories(basePackages = "example.app.repo.jpa")
@EnableTransactionManagement
@Profile({ "embedded", "local", "test" })
@SuppressWarnings("unused")
public class JpaConfiguration {

	@Bean
	public DataSource hsqlDataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

		jpaVendorAdapter.setGenerateDdl(true);
		jpaVendorAdapter.setShowSql(true);

		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactory.setPackagesToScan(Contact.class.getPackage().getName());

		return entityManagerFactory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
}