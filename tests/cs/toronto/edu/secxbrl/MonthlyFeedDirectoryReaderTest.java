package cs.toronto.edu.secxbrl;

import java.net.URI;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ekzhu
 */
public class MonthlyFeedDirectoryReaderTest {
    
    private final String monthlyArchive = "http://www.sec.gov/Archives/edgar/monthly/";
    private final String feedRegex = "xbrlrss-[0-9]{4}-[0-9]{2}.xml";

    /**
     * Test of getMontlyFilingFeeds method, of class MonthlyFeedDirectoryReader.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetMontlyFilingFeeds() throws Exception {
        MonthlyFeedDirectoryReader instance = new MonthlyFeedDirectoryReader(monthlyArchive);
        List<URI> result = instance.getMontlyFilingFeeds(feedRegex);
        assertTrue(result.size() > 0);
    }
    
}
