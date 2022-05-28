package es.edudediegolucas.petition.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetitionWs {

  public static void main(String[] args) {
    //http://localhost:8080/petition-ws/petition.wsdl
    SpringApplication.run(PetitionWs.class, args);
  }
}
