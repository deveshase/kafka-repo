package app.kafka.elastic;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

public class ElasticSearchConsumer {

    static Logger logger = LoggerFactory.getLogger(ElasticSearchConsumer.class.getName());

    public static void main(String[] args) {

        InputStream twitterProperties = ElasticSearchConsumer.class.getClassLoader()
                .getResourceAsStream("elastic-search.properties");

        Properties prop = new Properties();
        if (twitterProperties == null) {
            logger.error("Sorry, unable to find elastic-search.properties");
            return;
        }

        //load a properties file from class path, inside static method
        try {
            prop.load(twitterProperties);

        } catch (IOException e) {
            e.printStackTrace();
        }


        URI connUri = URI.create(prop.getProperty("elastic_URL"));
        String[] auth = connUri.getUserInfo().split(":");

        CredentialsProvider cp = new BasicCredentialsProvider();
        cp.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(auth[0], auth[1]));

        RestHighLevelClient rhlc = new RestHighLevelClient(
                RestClient.builder(new HttpHost(connUri.getHost(), connUri.getPort(), connUri.getScheme()))
                        .setHttpClientConfigCallback(
                                httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(cp)
                                        .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())));

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse resp  = rhlc.search(searchRequest, RequestOptions.DEFAULT);
            // Show that the query worked
            logger.info(resp.toString());
        } catch (Exception ex) {
            // Log the exception
            logger.error(ex.toString());
        }

        // Need to close the client so the thread will exit
        try {
            rhlc.close();
        } catch (Exception ex) {
            logger.error("exception:"+ex.getStackTrace());
        }

    }
}
