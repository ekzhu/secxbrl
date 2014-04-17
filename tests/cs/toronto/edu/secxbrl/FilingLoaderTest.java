
package cs.toronto.edu.secxbrl;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author ekzhu
 */
public class FilingLoaderTest {
    
    private FilingLoader filingLoader;
    private final String monthlyArchive = "http://www.sec.gov/Archives/edgar/monthly/";
    
    @Before
    public void setup() {
        MonthlyFeedDirectoryReader feedDirectoryReader 
                = new MonthlyFeedDirectoryReader(monthlyArchive);
        filingLoader = new FilingLoader(feedDirectoryReader);
    }
    
    @Test
    public void getFilingUrisTest() throws IOException, URISyntaxException {
        List<Filing> filings = filingLoader.getFilings("2014-04", null, null);
        verifyResult(filings);
    }
    
    @Test
    public void getFilingsWriteOutputTest() throws FileNotFoundException, 
            UnsupportedEncodingException, IOException, URISyntaxException {
        Writer writer = new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream("output.txt"), "utf-8"));
        filingLoader.getFilings("2014-04", null, null, writer);
    }
    
    @Test
    public void getFilingUrisWithCIKTest() throws IOException, URISyntaxException {
        // Get Facebook filings
        List<Filing> filings = filingLoader.getFilings("2014-01", "0001326801", null);
        verifyResult(filings);
        for (Filing filing : filings) {
            Assert.assertTrue(filing.cik.equals("0001326801"));
        }
    }
    
    @Test
    public void getFilingUrisWithFormTypeTest() throws IOException, URISyntaxException {
        // Get 10-K filings
        List<Filing> filings = filingLoader.getFilings("2014-01", null, "10-K");
        verifyResult(filings);
        for (Filing filing : filings) {
            Assert.assertTrue(filing.formType.equals("10-K"));
        }
    }
    
    @Test
    public void getFilingUrisWithCIKandFormTypeTest() throws IOException, URISyntaxException {
        // Get Facebook 10-K filings
        List<Filing> filings = filingLoader.getFilings("2014-01", "0001326801", "10-K");
        verifyResult(filings);
        for (Filing filing : filings) {
            Assert.assertTrue(filing.cik.equals("0001326801"));
            Assert.assertTrue(filing.formType.equals("10-K"));
        }
    }
    
    private void verifyResult(List<Filing> filings) {
        Assert.assertFalse(filings.isEmpty());
        for (Filing filing : filings) {
            System.out.println(filing.uri.toString() + ", " +
                    filing.formType + ", " + 
                    filing.filingDate + ", " + 
                    filing.cik + ", " + 
                    filing.companyName);
        }
    }
}
