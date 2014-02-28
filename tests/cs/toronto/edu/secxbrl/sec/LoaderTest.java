package cs.toronto.edu.secxbrl.sec;

import cs.toronto.edu.secxbrl.sec.FeedLoader;
import org.junit.Test;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.w3c.dom.Document;
import org.xbrlapi.Context;
import org.xbrlapi.ExtendedLink;
import org.xbrlapi.Fact;
import org.xbrlapi.FootnoteResource;
import org.xbrlapi.Instance;
import org.xbrlapi.Item;
import org.xbrlapi.Resource;
import org.xbrlapi.Unit;
import org.xbrlapi.data.Store;
import org.xbrlapi.loader.Loader;
import org.xbrlapi.utilities.XBRLException;

@RunWith(JUnit4.class)
public class LoaderTest extends SecTestBase {

    private Store store;
    private Loader loader;
    private final String latestFilingsFeed = "http://www.sec.gov/Archives/edgar/usgaap.rss.xml";
    private final String monthlyFilingsFeed = "http://www.sec.gov/Archives/edgar/monthly/xbrlrss-2008-08.xml";
    private final String companyCik = "0000789019"; // Microsoft
    private FeedLoader feedLoader;
    
    public LoaderTest() {
    }
    
    @Before
    public void setUp() {
        try {
            store = createStore();
            loader = createLoader(store, config.getCacheDirectory());
        } catch (XBRLException ex) {
            Logger.getLogger(LoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
        try {
            store.close();
        } catch (XBRLException ex) {
            Logger.getLogger(LoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    @Ignore
    public void LoadLatestFiling() {       
        try {
            feedLoader = new FeedLoader(new URI(latestFilingsFeed));
            List<URI> filingUris = feedLoader.getFilingUris();
            loader.stashURIs(filingUris);
            loader.discover();
        } catch (XBRLException ex) {
            Logger.getLogger(LoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(LoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void LoadCompanyFilings() {
        try {
            feedLoader = new FeedLoader(new URI(monthlyFilingsFeed));
            List<URI> filingUris = feedLoader.getFilingUris(companyCik);
//            loader.stashURIs(filingUris);
//            loader.discover();
            List<Instance> insts = store.getFragmentsFromDocument(filingUris.get(0), "Instance");
            reportInstance(insts.get(0));
        } catch (XBRLException ex) {
            Logger.getLogger(LoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(LoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void reportInstance(Instance instance) throws XBRLException {
        Document doc = instance.getDocumentNode();
//        List<Item> items = instance.getChildItems();
//        System.out.println("Top level items in the instance.");
//        for (Item item: items) {
//            System.out.println(item.getLocalname() + " " + item.getContextId());
//        }
//
//        List<Context> contexts = instance.getContexts();
//        System.out.println("Contexts in the instance.");
//        for (Context context: contexts) {
//            System.out.println("Context ID " + context.getId());
//        }
        
//        List<Fact> facts = instance.getAllFacts();
//        for (Fact fact : facts.subList(0, 2)) {
//            System.out.println(fact.getAllChildren());
//        }
    
//        List<Unit> units = instance.getUnits();
//        System.out.println("Units in the instance.");
//        for (Unit unit: units) {
//            System.out.println("Unit ID " + unit.getId());
//        }
        
//        List<ExtendedLink> links = instance.getFootnoteLinks();
//        System.out.println("Footnote links in the instance.");
//        for (ExtendedLink link: links) {            
//            List<Resource> resources = link.getResources();
//            for (Resource resource: resources) {
//                FootnoteResource fnr = (FootnoteResource) resource;
//                System.out.println("Footnote resource: " + fnr.getDataRootElement().getTextContent());
//            }
//        }
        
    }
    
}