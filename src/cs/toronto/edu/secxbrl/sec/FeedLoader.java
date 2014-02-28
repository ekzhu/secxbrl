/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cs.toronto.edu.secxbrl.sec;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.xbrlapi.grabber.SECGrabber;
import org.xbrlapi.grabber.SecGrabberImpl;

/**
 *
 * @author ekzhu
 */
public class FeedLoader {
    
    private final SECGrabber grabber;
    private final List<URI> allFilings;
    
    public FeedLoader(URI feedUri) {
        this.grabber = new SecGrabberImpl(feedUri);
        allFilings = grabber.getResources();
    }
    
    public List<URI> getFilingUris() {
        return allFilings;
    }
    
    public List<URI> getFilingUris(int maxNumOfFilings) {
        if (allFilings.size() > maxNumOfFilings) {
            return allFilings.subList(0, maxNumOfFilings-1);
        }
        return allFilings;
    }
    
    public List<URI> getFilingUris(String companyCik) {
        List<URI> companyFilings = new ArrayList<>();
        for (URI filing : allFilings) {
            if (grabber.getCIK(filing).compareTo(companyCik) == 0) {
                companyFilings.add(filing);
            }
        }
        return companyFilings;
    }
    
}
