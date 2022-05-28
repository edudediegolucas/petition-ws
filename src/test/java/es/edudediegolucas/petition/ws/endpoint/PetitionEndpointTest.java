package es.edudediegolucas.petition.ws.endpoint;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import es.edudediegolucas.petition.ws.client.PetitionClient;
import es.edudediegolucas.petition.ws.config.PetitionClientConfig;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = PetitionClientConfig.class)
class PetitionEndpointTest {

  @Autowired
  private PetitionClient petitionClient;
  private final WireMockServer wireMockServer = new WireMockServer(8081);

  @BeforeEach
  public void before() {
    wireMockServer.start();
    WireMock.configureFor("localhost", 8081);
  }

  @AfterEach
  public void after() {
    wireMockServer.stop();
  }

  @Test
  void testGetPetition() {
    WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/petition-ws"))
      .willReturn(WireMock.aResponse().withStatus(HttpStatus.SC_OK).withBodyFile("getPetitionResponse.xml")));
    var getPetitionResponse = petitionClient.getPetition("7JOLO6IC");
    Assertions.assertEquals("7JOLO6IC", getPetitionResponse.getPetition().getId());
  }

  @Test
  void testGetAllPetitions() {
    WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/petition-ws"))
        .willReturn(WireMock.aResponse().withStatus(HttpStatus.SC_OK).withBodyFile("getAllPetitionsResponse.xml")));
    var getAllPetitionResponse = petitionClient.getAllPetitions();
    Assertions.assertEquals(4, getAllPetitionResponse.getPetition().size());
  }

  @Test
  void testSavePetition() {
    WireMock.stubFor(WireMock.post(WireMock.urlEqualTo("/petition-ws"))
      .willReturn(WireMock.aResponse().withStatus(HttpStatus.SC_OK).withBodyFile("savePetitionResponse.xml")));
    var savePetitionResponse = petitionClient.savePetition();
    Assertions.assertEquals("7JOLO6IC", savePetitionResponse.getId());
  }

}