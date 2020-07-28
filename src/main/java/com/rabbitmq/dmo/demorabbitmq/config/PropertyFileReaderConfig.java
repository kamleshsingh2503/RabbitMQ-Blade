package com.rabbitmq.dmo.demorabbitmq.config;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import com.blade.Blade;
import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Order;
import com.blade.loader.BladeLoader;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Bean
@Order(1)
public class PropertyFileReaderConfig implements BladeLoader {

    private Map<Integer, String> exchangeMap;

    private Map<Integer, String> conf;

    private Map<String, String> connectionConf;

    private DriverManagerDataSource dmSource;

    private DataSource dataSource;

    @Override
    public void load(Blade blade) {
        // TODO Auto-generated method stub
        Map<Integer, String> exMap = new TreeMap<Integer, String>();
        int i = 1;
        exMap.put(i, blade.environment().getOrNull("app1.exchange.name"));
        i++;
        exMap.put(i, blade.environment().getOrNull("app2.exchange.name"));
        i++;
        exMap.put(i, blade.environment().getOrNull("app1.routing.key"));
        i++;
        exMap.put(i, blade.environment().getOrNull("app2.routing.key"));
        i++;
        exMap.put(i, blade.environment().getOrNull("app1.queue.name"));
        i++;
        exMap.put(i, blade.environment().getOrNull("app2.queue.name"));
        exchangeMap = exMap;

        Map<Integer, String> configuration = new TreeMap<>();
        int j = 1;

        configuration.put(j, blade.environment().getOrNull("spring.rabbitmq.host"));
        j++;
        configuration.put(j, blade.environment().getOrNull("spring.rabbitmq.port"));
        j++;
        configuration.put(j, blade.environment().getOrNull("spring.rabbitmq.username"));
        j++;
        configuration.put(j, blade.environment().getOrNull("spring.rabbitmq.password"));
        conf = configuration;
        Map<String, String> connConf = new TreeMap<>();

        connConf.put("jdbcUrl", blade.environment().getOrNull("spring.datasource.url"));
        connConf.put("jdbcUser", blade.environment().getOrNull("spring.datasource.username"));
        connConf.put("jdbcPassword", blade.environment().getOrNull("spring.datasource.password"));
        connConf.put("jdbcDriver", blade.environment().getOrNull("spring.datasource.driver-class-name"));


        connectionConf = connConf;

        setDmSource();
    }
    

    public void setDmSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName(this.connectionConf.get(0));
        for(Map.Entry<String, String> entry : this.connectionConf.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        System.out.println(this.connectionConf.get("jdbcDriver"));
        System.out.println(this.connectionConf.get("jdbcUrl"));
        System.out.println(this.connectionConf.get("jdbcUser"));
        System.out.println(this.connectionConf.get("jdbcPassword"));
        dataSource.setDriverClassName(this.connectionConf.get("jdbcDriver"));
        dataSource.setUrl(this.connectionConf.get("jdbcUrl"));
        dataSource.setUsername(this.connectionConf.get("jdbcUser"));
        dataSource.setPassword(this.connectionConf.get("jdbcPassword"));

        this.dmSource = dataSource;
    }
    /**
     * @return Map<String, String> return the exchangeMap
     */
    public Map<Integer, String> getExchangeMap() {
        return exchangeMap;
    }

    /**
     * @param exchangeMap the exchangeMap to set
     */
    public void setExchangeMap(Map<Integer, String> exchangeMap) {
        this.exchangeMap = exchangeMap;
    }

    /**
     * @return Map<Integer, String> return the conf
     */
    public Map<Integer, String> getConf() {
        return conf;
    }

    /**
     * @param conf the conf to set
     */
    public void setConf(Map<Integer, String> conf) {
        this.conf = conf;
    }


    /**
     * @return Map<String, String> return the connectionConf
     */
    public Map<String, String> getConnectionConf() {
        return connectionConf;
    }

    /**
     * @param connectionConf the connectionConf to set
     */
    public void setConnectionConf(Map<String, String> connectionConf) {
        this.connectionConf = connectionConf;
    }


    /**
     * @return DriverManagerDataSource return the dmSource
     */
    public DriverManagerDataSource getDmSource() {
        return dmSource;
    }

    /**
     * @return DataSource return the dataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }

    

}