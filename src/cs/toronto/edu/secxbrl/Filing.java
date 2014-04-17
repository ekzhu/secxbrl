/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cs.toronto.edu.secxbrl;

import java.net.URI;

/**
 *
 * @author ekzhu
 */
public class Filing {
    public final URI uri;
    public final String formType;
    public final String companyName;
    public final String cik;
    public final String filingDate;
    
    public Filing(URI uri, String formType, String filingDate, String cik, String companyName) {
        this.uri = uri;
        this.companyName = companyName;
        this.cik = cik;
        this.filingDate = filingDate;
        this.formType = formType;
    }
    
    @Override
    public String toString() {
        return uri.toString()+","+formType+","+filingDate+","+cik+","+companyName;
    }
}
