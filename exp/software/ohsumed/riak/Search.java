
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.response.*;
import org.apache.solr.client.solrj.impl.*;
import org.apache.solr.common.*;

public class Search {
	public static void main (String[] argv) throws IOException , org.apache.solr.client.solrj.SolrServerException {

		CommonsHttpSolrServer server = new CommonsHttpSolrServer("http://172.18.40.178:8098/solr/meddata/");
		server.setParser(new XMLResponseParser());

		SolrQuery query = new SolrQuery();

		Scanner scan = new Scanner(new File("../queries.txt"));

                String queryString;

                while (scan.hasNextLine()) {
                        queryString = scan.nextLine().trim();

			query.setQuery(queryString.replaceAll("(?:with|of|and|the|a)", "") + "&wt=json");
			query.add("wt","json");

			QueryResponse rsp = server.query( query );

			SolrDocumentList docs = rsp.getResults();

		        for (int i = 0; i < docs.size(); i++) {
			        SolrDocument info = docs.get(i);
			        System.out.println (queryString + " " + info.get("id").toString());
		        }
	        }
        }
}
