package cs.toronto.edu.secxbrl.sec;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author ekzhu
 */
public class MonthlyFeedDirectoryReader {
    
    private final String monthlyArchiveUrl;
    private final String feedFilingNameRegex;
    
    public MonthlyFeedDirectoryReader(String monthlyArchiveUrl, 
            String feedFilingNameRegex) {
        this.feedFilingNameRegex = feedFilingNameRegex;
        if (! monthlyArchiveUrl.endsWith("/")) {
            this.monthlyArchiveUrl += monthlyArchiveUrl + "/";
        } else {
            this.monthlyArchiveUrl = monthlyArchiveUrl;
        }
    }
    
    public List<URI> getMontlyFilingFeeds() throws IOException, URISyntaxException {
        List<URI> feeds = new ArrayList<>();
        Document doc = Jsoup.connect(monthlyArchiveUrl).get();
        Elements nodes = doc.select("a");
        for (int i=0; i<nodes.size(); i++) {
            String link = nodes.get(i).attr("href");
            if (link.matches(feedFilingNameRegex)) {
                feeds.add(new URI(monthlyArchiveUrl+link));
            }
        }
        return feeds;
    }

}
