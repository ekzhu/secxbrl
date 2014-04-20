secxbrl
=======

This tool helps you download the meta data (URL, form type, date, CIK, etc.) of XBRL filings from SEC. It can also be used as a data retrieval library for XBRL processing. To convert XBRL to RDF, see [xbrl2rdf](https://github.com/ekzhu/xbrl2rdf).

To compile (need Ant):

1. Check out the source code.
2. Run `bash build.sh`
3. The compiled jar is in folder `dist`

To run, type `java -jar secxbrl.jar -h` to see help messages.