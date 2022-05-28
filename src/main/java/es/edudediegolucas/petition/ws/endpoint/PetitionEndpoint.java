package es.edudediegolucas.petition.ws.endpoint;

import es.edudediegolucas.petition.ws.GetAllPetitionRequest;
import es.edudediegolucas.petition.ws.GetAllPetitionResponse;
import es.edudediegolucas.petition.ws.GetPetitionRequest;
import es.edudediegolucas.petition.ws.GetPetitionResponse;
import es.edudediegolucas.petition.ws.SavePetitionRequest;
import es.edudediegolucas.petition.ws.SavePetitionResponse;
import es.edudediegolucas.petition.ws.repository.petition.PetitionEntity;
import es.edudediegolucas.petition.ws.repository.petition.PetitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@RequiredArgsConstructor
@Endpoint
public class PetitionEndpoint {

  private final PetitionRepository petitionRepository;
  private static final String NAMESPACE_URI = "http://edudediegolucas.es/petition/ws";

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPetitionRequest")
  @ResponsePayload
  public GetPetitionResponse getPetition(@RequestPayload GetPetitionRequest getPetitionRequest) {
    log.info("getPetition called with id {}", getPetitionRequest.getId());
    GetPetitionResponse getPetitionResponse = new GetPetitionResponse();
    getPetitionResponse.setPetition(PetitionEntity.mapToPetition(petitionRepository.getPetitionById(getPetitionRequest.getId())));
    return getPetitionResponse;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPetitionRequest")
  @ResponsePayload
  public GetAllPetitionResponse getAllPetitions(@RequestPayload GetAllPetitionRequest getAllPetitionRequest) {
    log.info("getAllPetitions called");
    GetAllPetitionResponse getAllPetitionResponse = new GetAllPetitionResponse();
    petitionRepository.getAllPetitions().stream()
      .map(PetitionEntity::mapToPetition)
      .forEach(petition -> getAllPetitionResponse.getPetition().add(petition));
    return getAllPetitionResponse;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "savePetitionRequest")
  @ResponsePayload
  public SavePetitionResponse savePetition(@RequestPayload SavePetitionRequest savePetitionRequest) {
    log.info("savePetition called");
    SavePetitionResponse savePetitionResponse = new SavePetitionResponse();
    savePetitionResponse.setId(petitionRepository.insertPetition());
    return savePetitionResponse;
  }

}
