package edu.unipampa.poo.management.bars.and.nightclubs.Infra.Config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {
    private final String CONFIG_FILE = "config.properties";
    public Path _dbClient;
    public Path _dbProduct;
    public Path _dbConsumption;
    
    public Configuration Load() throws FileNotFoundException, IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(CONFIG_FILE));

        var basePath = System.getProperty("user.dir") + props.getProperty("db.path");
       
        _dbClient = Paths.get(basePath + props.getProperty("db.client"));
        _dbProduct = Paths.get(basePath + props.getProperty("db.product"));
        _dbConsumption = Paths.get(basePath + props.getProperty("db.consumption"));

        return this;
    }
}