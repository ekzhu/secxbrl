/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cs.toronto.edu.secxbrl;

import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.xbrlapi.grabber.SECGrabber;
import org.xbrlapi.grabber.SecGrabberImpl;

/**
 *
 * @author ekzhu
 */
public class FilingLoader {
    
    private final MonthlyFeedDirectoryReader monthlyFeedDirectoryReader;
    private final String feedNamePrefix = "xbrlrss-";
    private final String feedMonthRegex = "[0-9]{4}-[0-9]{2}";
    private final String feedNameExt = ".xml";
    
    public FilingLoader(MonthlyFeedDirectoryReader feedDirectoryReader) {
        monthlyFeedDirectoryReader = feedDirectoryReader;
    }
    
    public List<Filing> getFilings(String yearMonth, String companyCik, String formType) 
            throws IOException, URISyntaxException {
        List<URI> feedUris = getFeedUris(yearMonth);
        List<Filing> filings = new ArrayList<Filing>();
        for (URI feedUri : feedUris) {
            SECGrabber grabber = new SecGrabberImpl(feedUri);
            List<URI> uris = grabber.getResources();
            // Apply the filters
            for (URI filingUri : uris) {
                boolean add = true;
                String cik = grabber.getCIK(filingUri);
                String type = grabber.getFormType(filingUri);
                String companyName = grabber.getEntityName(filingUri);
                String date = grabber.getFilingDate(filingUri);
                if (companyCik != null) {
                    add = add & cik.equals(companyCik);
                }
                if (formType != null) {
                    add = add & type.equals(formType);
                }
                if (add) {
                    Filing filing = new Filing(filingUri, type, date, cik, companyName);
                    filings.add(filing);
                }
            }
        }
        return filings;
    }
    
    public void getFilings(String yearMonth, String companyCik, String formType, Writer writer) 
            throws IOException, URISyntaxException {
        List<URI> feedUris = getFeedUris(yearMonth);
        for (URI feedUri : feedUris) {
            SECGrabber grabber = new SecGrabberImpl(feedUri);
            List<URI> uris = grabber.getResources();
            // Apply the filters
            for (URI filingUri : uris) {
                boolean add = true;
                String cik = grabber.getCIK(filingUri);
                String type = grabber.getFormType(filingUri);
                String companyName = grabber.getEntityName(filingUri);
                String date = grabber.getFilingDate(filingUri);
                if (companyCik != null) {
                    add = add & cik.equals(companyCik);
                }
                if (formType != null) {
                    add = add & type.equals(formType);
                }
                if (add) {
                    Filing filing = new Filing(filingUri, type, date, cik, companyName);
                    writer.write(filing.uri.toString() + "\t" +
                        filing.formType + "\t" + 
                        filing.filingDate + "\t" + 
                        filing.cik + "\t" + 
                        filing.companyName);
                    writer.write("\n");
                }
            }
        }
    }
    
    private List<URI> getFeedUris(String yearMonth) throws IOException, URISyntaxException {
        if (yearMonth == null) {
            return monthlyFeedDirectoryReader
                    .getMontlyFilingFeeds(feedNamePrefix+feedMonthRegex+feedNameExt);
        }
        if (!yearMonth.matches(feedMonthRegex)) {
            throw new IllegalArgumentException(
                    "Incorrect string format. The month string format should be yyyy-mm.");
        }
        return monthlyFeedDirectoryReader
                .getMontlyFilingFeeds(feedNamePrefix+yearMonth+feedNameExt);
    }
    
}
