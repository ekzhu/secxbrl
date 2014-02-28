/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cs.toronto.edu.secxbrl.sec;

import cs.toronto.edu.secxbrl.sec.MonthlyFeedDirectoryReader;
import cs.toronto.edu.secxbrl.sec.HistoricalFilingsLoader;
import static cs.toronto.edu.secxbrl.sec.SecTestBase.config;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.xbrlapi.data.Store;
import org.xbrlapi.loader.Loader;
import org.xbrlapi.utilities.XBRLException;

/**
 *
 * @author ekzhu
 */
public class HistoricalFilingsLoaderTest extends SecTestBase {
   
    private Store store;
    private Loader loader;
    private final String monthlyArchive = "http://www.sec.gov/Archives/edgar/monthly/";
    private final String companyCik = "0000789019"; // Microsoft
    private final String feedRegex = "xbrlrss-[0-9]{4}-[0-9]{2}.xml";
    
    private MonthlyFeedDirectoryReader directoryReader;
    
    public HistoricalFilingsLoaderTest() {
    }
    
    @Before
    public void setUp() {
        try {
            directoryReader = new MonthlyFeedDirectoryReader(monthlyArchive, feedRegex);
            store = createStore();
            loader = createLoader(store, config.getCacheDirectory());
        } catch (XBRLException ex) {
            Logger.getLogger(HistoricalFilingsLoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
        try {
            store.close();
        } catch (XBRLException ex) {
            Logger.getLogger(HistoricalFilingsLoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of load method, of class HistoricalFilingsLoader.
     */
    @Test
    public void testLoad() throws Exception {
        System.out.println("load all historical Microsoft filings");
        HistoricalFilingsLoader instance = new HistoricalFilingsLoader(directoryReader, loader);
        instance.load(companyCik);
    }
    
}
