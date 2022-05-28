package es.edudediegolucas.petition.ws.client;

import es.edudediegolucas.petition.ws.GetAllPetitionRequest;
import es.edudediegolucas.petition.ws.GetAllPetitionResponse;
import es.edudediegolucas.petition.ws.GetPetitionRequest;
import es.edudediegolucas.petition.ws.GetPetitionResponse;
import es.edudediegolucas.petition.ws.SavePetitionRequest;
import es.edudediegolucas.petition.ws.SavePetitionResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class PetitionClient extends WebServiceGatewaySupport {

  public GetPetitionResponse getPetition(String id) {
    GetPetitionRequest getPetitionRequest = new GetPetitionRequest();
    getPetitionRequest.setId(id);
    return (GetPetitionResponse) getWebServiceTemplate().marshalSendAndReceive(getPetitionRequest);
  }

  public GetAllPetitionResponse getAllPetitions() {
    return (GetAllPetitionResponse) getWebServiceTemplate().marshalSendAndReceive(new GetAllPetitionRequest());
  }

  public SavePetitionResponse savePetition() {
    return (SavePetitionResponse) getWebServiceTemplate().marshalSendAndReceive(new SavePetitionRequest());
  }
}
