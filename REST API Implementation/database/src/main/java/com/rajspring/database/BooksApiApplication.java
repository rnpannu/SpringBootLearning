package com.rajspring.database;

import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import javax.sql.DataSource;

//@SpringBootApplication
//@Log
//public class DatabaseApplication implements CommandLineRunner {
//
//	private final DataSource dataSource;
//
//	public DatabaseApplication(final DataSource dataSource) {
//		this.dataSource = dataSource;
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(DatabaseApplication.class, args);
//	}
//
//	@Override
//	public void run(final String... args) {
//		//log.info("Datasource: " + dataSource.toString());
//		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
//		restTemplate.execute("select 1");
//	}
//
//}

@SpringBootApplication
@Log
public class BooksApiApplication {

	// private final DataSource dataSource;

	// public DatabaseApplication(final DataSource dataSource) {
	// 	this.dataSource = dataSource;
	// }

	public static void main(String[] args) {
		SpringApplication.run(BooksApiApplication.class, args);
	}

}