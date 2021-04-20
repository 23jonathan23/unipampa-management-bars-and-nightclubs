package edu.unipampa.poo.management.bars.and.nighclubs.Config;

public class Configuration {
    private final String CONFIG_FILE = "config.properties";
    private String _dbClient;
    private String _dbProduct;
    private String _dbConsumption;
    
    public Configuration Load() {
        Properties props = new Properties();

        props.load(new FileInputStream(CONFIG_FILE));

        var basePath = System.getProperty("user.dir") + props.getProperty("db.path");
       
        _dbClient = Paths.get(basePath + props.getProperty("db.client"));
        _dbProduct = Paths.get(basePath + props.getProperty("db.product"));
        _dbConsumption = Paths.get(basePath + props.getProperty("db.consumption"));

        return this;
    }
}