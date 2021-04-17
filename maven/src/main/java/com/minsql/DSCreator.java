package com.minsql;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class DSCreator {
    private static BasicDataSource basicDS;
    static {
        try {
            basicDS = new BasicDataSource();
            Properties properties = new Properties();
            // Loading properties file from classpath
            InputStream inputStream = DSCreator.class.getClassLoader().getResourceAsStream("db.properties");
            if (inputStream == null) {
                throw new IOException("File not found");
            }
            properties.load(inputStream);
            basicDS.setDriverClassName(properties.getProperty("DB.DRIVER_CLASS"));
            basicDS.setUrl(properties.getProperty("DB.DB_URL"));
            basicDS.setUsername(properties.getProperty("DB.DB_USER"));
            basicDS.setPassword(properties.getProperty("DB.DB_PASSWORD"));
            // The initial number of connections that are created when the pool is started.
            basicDS.setInitialSize(Integer.parseInt(properties.getProperty("DB.INITIAL_POOL_SIZE")));
            // The maximum number of active connections that can be allocated from this pool
            // at the same time
            basicDS.setMaxTotal(Integer.parseInt(properties.getProperty("DB.MAX_POOL_SIZE")));
            // basicDS.setLifo(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return basicDS;
    }
}
