package cs.toronto.edu.secxbrl.config;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
/**
 *
 * @author ekzhu
 */
public class ExistDbConfig {

    /**
     * @return the existdbUsername
     */
    public String getExistdbUsername() {
        return existdbUsername;
    }

    /**
     * @return the existdbPassword
     */
    public String getExistdbPassword() {
        return existdbPassword;
    }

    /**
     * @return the existdbHost
     */
    public String getExistdbHost() {
        return existdbHost;
    }

    /**
     * @return the existdbPort
     */
    public String getExistdbPort() {
        return existdbPort;
    }

    /**
     * @return the existdbDatabase
     */
    public String getExistdbDatabase() {
        return existdbDatabase;
    }

    /**
     * @return the existdbParentPath
     */
    public String getExistdbParentPath() {
        return existdbParentPath;
    }

    /**
     * @return the existdbCollection
     */
    public String getExistdbCollection() {
        return existdbCollection;
    }

    /**
     * @return the cacheDirectory
     */
    public String getCacheDirectory() {
        return cacheDirectory;
    }
    
    private Configuration config;
    
    private final String existdbHost;
    private final String existdbPort;
    private final String existdbDatabase;
    private final String existdbParentPath;
    private final String existdbCollection;
    private final String existdbUsername;
    private final String existdbPassword;
    private final String cacheDirectory;
    
    
    public ExistDbConfig(){
        try {
            config = new PropertiesConfiguration("secxbrl.properties");
        } catch (ConfigurationException ex) {
            Logger.getLogger(ExistDbConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        existdbHost = config.getString("exist-db.host");
        existdbPort = config.getString("exist-db.port");
        existdbDatabase = config.getString("exist-db.database");
        existdbUsername = config.getString("exist-db.username");
        existdbPassword = config.getString("exist-db.password");
        existdbParentPath = config.getString("exist-db.parentpath");
        existdbCollection = config.getString("exist-db.collection");
        cacheDirectory = config.getString("cache-directory");
    }
}
