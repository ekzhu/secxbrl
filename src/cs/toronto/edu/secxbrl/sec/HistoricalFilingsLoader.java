package cs.toronto.edu.secxbrl.sec;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.xbrlapi.loader.Loader;
import org.xbrlapi.utilities.XBRLException;

/**
 *
 * @author ekzhu
 */
public class HistoricalFilingsLoader {

    private final MonthlyFeedDirectoryReader monthlyFeedDirectoryReader;
    private final Loader loader;
    
    public HistoricalFilingsLoader(MonthlyFeedDirectoryReader monthlyFeedDirectoryReader,
            Loader loader) {
        this.monthlyFeedDirectoryReader = monthlyFeedDirectoryReader;
        this.loader = loader;
    }
    
    private List<URI> getFilingUris(URI feed) {
        FeedLoader feedLoader = new FeedLoader(feed);
        return feedLoader.getFilingUris();
    }
    
    private List<URI> getFilingUris(URI feed, String companyCik) {
        FeedLoader feedLoader = new FeedLoader(feed);
        return feedLoader.getFilingUris(companyCik);
    }
    
    public void load() throws IOException, URISyntaxException, XBRLException {
        List<URI> feeds = monthlyFeedDirectoryReader.getMontlyFilingFeeds();
        for (URI feed : feeds) {
            List<URI> filings = getFilingUris(feed);
            loader.stashURIs(filings);
        }
        loader.discover();
    }
    
    public void load(String companyCik) throws IOException, URISyntaxException, XBRLException {
        List<URI> feeds = monthlyFeedDirectoryReader.getMontlyFilingFeeds();
        for (URI feed : feeds) {
            List<URI> filings = getFilingUris(feed, companyCik);
            loader.stashURIs(filings);
        }
        loader.discover();
    }

}
