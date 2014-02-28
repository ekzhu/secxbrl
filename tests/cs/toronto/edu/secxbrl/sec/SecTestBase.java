package cs.toronto.edu.secxbrl.sec;

import cs.toronto.edu.secxbrl.config.ExistDbConfig;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.xbrlapi.cache.CacheImpl;
import org.xbrlapi.data.Store;
import org.xbrlapi.data.exist.StoreImpl;
import org.xbrlapi.loader.Loader;
import org.xbrlapi.loader.LoaderImpl;
import org.xbrlapi.sax.EntityResolver;
import org.xbrlapi.sax.EntityResolverImpl;
import org.xbrlapi.utilities.XBRLException;
import org.xbrlapi.xlink.XLinkProcessor;
import org.xbrlapi.xlink.XLinkProcessorImpl;
import org.xbrlapi.xlink.handler.XBRLCustomLinkRecogniserImpl;
import org.xbrlapi.xlink.handler.XBRLXLinkHandlerImpl;

/**
 *
 * @author ekzhu
 */
abstract public class SecTestBase {
    
    protected static ExistDbConfig config;
    
    @BeforeClass
    public static void setUpClass() {
        config = new ExistDbConfig();
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    protected Store createStore() throws XBRLException {
        return new StoreImpl(config.getExistdbHost(), 
                config.getExistdbPort(), config.getExistdbDatabase(), 
                config.getExistdbUsername(), config.getExistdbPassword(), 
                config.getExistdbParentPath(), config.getExistdbCollection());
    }
    
    protected Loader createLoader(Store store, String cache) throws XBRLException {
        XBRLXLinkHandlerImpl xlinkHandler = new XBRLXLinkHandlerImpl();
        XBRLCustomLinkRecogniserImpl clr = new XBRLCustomLinkRecogniserImpl();
        XLinkProcessor xlinkProcessor = new XLinkProcessorImpl(xlinkHandler, clr);

        File cacheFile = new File(cache);

        // Rivet errors in the SEC XBRL data require these URI remappings to prevent discovery process from breaking.
        HashMap<URI, URI> map = new HashMap<URI, URI>();
        try {
            map.put(new URI("http://www.xbrl.org/2003/linkbase/xbrl-instance-2003-12-31.xsd"), new URI("http://www.xbrl.org/2003/xbrl-instance-2003-12-31.xsd"));
            map.put(new URI("http://www.xbrl.org/2003/instance/xbrl-instance-2003-12-31.xsd"), new URI("http://www.xbrl.org/2003/xbrl-instance-2003-12-31.xsd"));
            map.put(new URI("http://www.xbrl.org/2003/linkbase/xbrl-linkbase-2003-12-31.xsd"), new URI("http://www.xbrl.org/2003/xbrl-linkbase-2003-12-31.xsd"));
            map.put(new URI("http://www.xbrl.org/2003/instance/xbrl-linkbase-2003-12-31.xsd"), new URI("http://www.xbrl.org/2003/xbrl-linkbase-2003-12-31.xsd"));
            map.put(new URI("http://www.xbrl.org/2003/instance/xl-2003-12-31.xsd"), new URI("http://www.xbrl.org/2003/xl-2003-12-31.xsd"));
            map.put(new URI("http://www.xbrl.org/2003/linkbase/xl-2003-12-31.xsd"), new URI("http://www.xbrl.org/2003/xl-2003-12-31.xsd"));
            map.put(new URI("http://www.xbrl.org/2003/instance/xlink-2003-12-31.xsd"), new URI("http://www.xbrl.org/2003/xlink-2003-12-31.xsd"));
            map.put(new URI("http://www.xbrl.org/2003/linkbase/xlink-2003-12-31.xsd"), new URI("http://www.xbrl.org/2003/xlink-2003-12-31.xsd"));
        } catch (URISyntaxException e) {
            throw new XBRLException("URI syntax exception", e);
        }
        EntityResolver entityResolver = new EntityResolverImpl(cacheFile, map);

        Loader myLoader = new LoaderImpl(store, xlinkProcessor, entityResolver);
        myLoader.setCache(new CacheImpl(cacheFile));
        myLoader.setEntityResolver(entityResolver);
        xlinkHandler.setLoader(myLoader);
        return myLoader;
    }
}
