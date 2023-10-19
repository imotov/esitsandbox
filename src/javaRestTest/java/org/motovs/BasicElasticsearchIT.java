package org.motovs;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.elasticsearch.test.rest.ESRestTestCase;
import org.junit.Before;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;

public class BasicElasticsearchIT extends ESRestTestCase {

    private static ElasticsearchClient esClient;

    @Before
    public void init() throws Exception {
        super.initClient();
        if (esClient == null) {
            ElasticsearchTransport transport = new RestClientTransport(
                    client(), new JacksonJsonpMapper());
            esClient = new ElasticsearchClient(transport);
        }
    }

    public void testSimpleSearch() throws IOException {
        // Create a test index
        CreateIndexResponse createIndexResponse = esClient.indices().create(builder -> builder.index("test"));
        assertThat(createIndexResponse.acknowledged(), equalTo(true));

        // Index a document
        esClient.index(builder ->
                builder.index("test").document(new TestDocument("abc", "xyz")).refresh(Refresh.True)
        );

        // Search for the document
        SearchResponse<TestDocument> searchResponse = esClient.search(builder -> builder.index("test"), TestDocument.class);
        assertThat(searchResponse.hits().total().value(), equalTo(1L));
    }
}
