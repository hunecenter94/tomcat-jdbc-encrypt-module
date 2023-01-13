package com.github.tomcatEncryptModule;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.Context;
import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.DataSourceFactory;
import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.XADataSource;

public class EncryptedDataSourceFactory extends DataSourceFactory {

    private Encryptor encryptor = null;

    public EncryptedDataSourceFactory() {
        try {
            encryptor = new Encryptor(); // If you've used your own secret key, pass it in...
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DataSource createDataSource(Properties properties, Context context, boolean XA) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, SQLException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        // Here we decrypt our password.
        PoolConfiguration poolProperties = EncryptedDataSourceFactory.parsePoolProperties(properties);
        poolProperties.setUrl(encryptor.decrypt(poolProperties.getUrl()));
        poolProperties.setUsername(encryptor.decrypt(poolProperties.getUsername()));
        poolProperties.setPassword(encryptor.decrypt(poolProperties.getPassword()));
        /** 지속 적인 connection 유지를 위해 설정 추가 **/
        poolProperties.setValidationQuery("SELECT 1"); //Oracle SELECT 1 FROM DUAL; 그외 SELECT 1 
        poolProperties.setTestWhileIdle(true);
        poolProperties.setTimeBetweenEvictionRunsMillis(720000);//2시간 단위로 SELECT 1 검증
                
        // The rest of the code is copied from Tomcat's DataSourceFactory.
        if (poolProperties.getDataSourceJNDI() != null && poolProperties.getDataSource() == null) {
            performJNDILookup(context, poolProperties);
        }
        org.apache.tomcat.jdbc.pool.DataSource dataSource = XA ? new XADataSource(poolProperties)
                : new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
        dataSource.createPool();
        return dataSource;
    }

}
