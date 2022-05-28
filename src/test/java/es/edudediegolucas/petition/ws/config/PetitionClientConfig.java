package es.edudediegolucas.petition.ws.config;

import es.edudediegolucas.petition.ws.client.PetitionClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class PetitionClientConfig {

  @Bean
  public Jaxb2Marshaller marshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath("es.edudediegolucas.petition.ws");
    return marshaller;
  }

  @Bean
  public PetitionClient petitionClient(Jaxb2Marshaller marshaller) {
    PetitionClient client = new PetitionClient();
    client.setDefaultUri("http://localhost:8081/petition-ws");
    client.setMarshaller(marshaller);
    client.setUnmarshaller(marshaller);
    return client;
  }
}
