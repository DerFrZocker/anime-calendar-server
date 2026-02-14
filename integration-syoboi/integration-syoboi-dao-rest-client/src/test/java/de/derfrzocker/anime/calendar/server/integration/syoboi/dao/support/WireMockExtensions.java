package de.derfrzocker.anime.calendar.server.integration.syoboi.dao.support;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import java.util.Map;

// TODO 2025-03-14: Make this whole think better
public class WireMockExtensions implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        this.wireMockServer = new WireMockServer();
        this.wireMockServer.start();

        this.wireMockServer.stubFor(get(urlEqualTo("/db.php?ChID=7&Command=ChLookup")).willReturn(aResponse().withHeader(
                "Content-Type",
                "text/xml").withBody("""
                                             <?xml version="1.0" encoding="UTF-8"?><ChLookupResponse><Result><Code>200</Code><Message></Message></Result><ChItems><ChItem id="7"><LastUpdate>2024-04-03 22:58:02</LastUpdate><ChID>7</ChID><ChName>テレビ東京</ChName><ChiEPGName>テレビ東京</ChiEPGName><ChURL>https://www.tv-tokyo.co.jp/</ChURL><ChEPGURL>https://www.tv-tokyo.co.jp/timetable/broad_tvtokyo/thisweek/</ChEPGURL><ChComment>http://jk.nicovideo.jp/watch/jk7
                                             </ChComment><ChGID>1</ChGID><ChNumber>7</ChNumber></ChItem></ChItems></ChLookupResponse>
                                             """)));

        return Map.of("quarkus.rest-client.syoboi.url",
                      this.wireMockServer.baseUrl(),
                      "quarkus.rest-client.syoboi.user-agent",
                      "Test");
    }

    @Override
    public void stop() {
        if (this.wireMockServer != null) {
            this.wireMockServer.stop();
        }
    }
}
