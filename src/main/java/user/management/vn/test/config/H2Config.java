package user.management.vn.test.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

/**
 * @summary config for h2 database
 * @author Thehap Rok
 */
@Profile("test")
@Configuration
public class H2Config {
	
	/**
	 * @summary config embedded datasource
	 * @author Thehap Rok
	 * @return DataSource
	 */
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2).addScript("db/test/table.sql")
				.addScript("db/test/data.sql").build();
		return db;
	}
}
