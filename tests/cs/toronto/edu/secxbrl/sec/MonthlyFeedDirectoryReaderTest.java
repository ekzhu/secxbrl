package cs.toronto.edu.secxbrl.sec;

import cs.toronto.edu.secxbrl.sec.MonthlyFeedDirectoryReader;
import java.net.URI;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ekzhu
 */
public class MonthlyFeedDirectoryReaderTest extends SecTestBase {
    
    private final String monthlyArchive = "http://www.sec.gov/Archives/edgar/monthly/";
    private final String feedRegex = "xbrlrss-[0-9]{4}-[0-9]{2}.xml";
    
    public MonthlyFeedDirectoryReaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMontlyFilingFeeds method, of class MonthlyFeedDirectoryReader.
     */
    @Test
    public void testGetMontlyFilingFeeds() throws Exception {
        System.out.println("getMontlyFilingFeeds");
        MonthlyFeedDirectoryReader instance = new MonthlyFeedDirectoryReader(monthlyArchive, feedRegex);
        List<URI> result = instance.getMontlyFilingFeeds();
        assertTrue(result.size() > 0);
    }
    
}
