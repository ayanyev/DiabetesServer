package org.coursera.capstone.T1DTeens;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.coursera.capstone.T1DTeens.config.AuthorizationServerConfiguration;
import org.coursera.capstone.T1DTeens.config.ResourceServerConfiguration;
import org.coursera.capstone.T1DTeens.config.WebSecurityConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import java.io.File;

@SpringBootApplication
@EnableWebMvc
@EnableJpaRepositories("org.coursera.capstone.T1DTeens.repositories")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@Import({ResourceServerConfiguration.class,
        AuthorizationServerConfiguration.class,
        WebSecurityConfiguration.class})

public class T1DTeensAppApplication extends RepositoryRestMvcConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(T1DTeensAppApplication.class, args);
    }


    /*    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
                                                                Environment env) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("org.coursera.capstone.T1DTeens.entities");

        Properties jpaProperties = new Properties();

        //Configures the used database dialect. This allows Hibernate to create SQL
        //that is optimized for the used database.
        jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));

        //Specifies the action that is invoked to the database when the Hibernate
        //SessionFactory is created or closed.
        jpaProperties.put("hibernate.hbm2ddl.auto",
                env.getRequiredProperty("hibernate.hbm2ddl.auto")
        );

        //Configures the naming strategy that is used when Hibernate creates
        //new database objects and schema elements
        jpaProperties.put("hibernate.ejb.naming_strategy",
                env.getRequiredProperty("hibernate.ejb.naming_strategy")
        );

        //If the value of this property is true, Hibernate writes all SQL
        //statements to the console.
        jpaProperties.put("hibernate.show_sql",
                env.getRequiredProperty("hibernate.show_sql")
        );

        //If the value of this property is true, Hibernate will format the SQL
        //that is written to the console.
        jpaProperties.put("hibernate.format_sql",
                env.getRequiredProperty("hibernate.format_sql")
        );

        jpaProperties.put("hibernate.event.merge.entity_copy_observer",
                env.getRequiredProperty("hibernate.event.merge.entity_copy_observer")
        );

        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }*/

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);

        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    EmbeddedServletContainerCustomizer containerCustomizer(
            @Value("${keystore.file:src/main/resources/private/keystore.jks}") String keystoreFile,
            @Value("${keystore.pass:changeit}") final String keystorePass) throws Exception {

        final String absoluteKeystoreFile = new File(keystoreFile).getAbsolutePath();

        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
                tomcat.addConnectorCustomizers(
                        new TomcatConnectorCustomizer() {
                            @Override
                            public void customize(Connector connector) {
                                connector.setPort(8443);
                                connector.setSecure(true);
                                connector.setScheme("https");

                                Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
                                proto.setSSLEnabled(true);
                                proto.setKeystoreFile(absoluteKeystoreFile);
                                proto.setKeystorePass(keystorePass);
                                proto.setKeystoreType("JKS");
                                proto.setKeyAlias("tomcat");
                            }
                        });

            }
        };
    }

}
