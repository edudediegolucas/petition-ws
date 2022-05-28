package es.edudediegolucas.petition.ws.repository.petition;

import es.edudediegolucas.petition.ws.Petition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.SerializationUtils;

import javax.xml.datatype.DatatypeFactory;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Base64;

@Value
@Builder
public class PetitionEntity {

  String id;
  Data data;
  PetitionStatus status;
  LocalDateTime creationTime;
  LocalDateTime signatureTime;
  String nif;
  String name;

  @Getter
  @AllArgsConstructor(access = AccessLevel.PACKAGE)
  public enum PetitionStatus {
    CREATED("created"),
    ACCEPTED("accepted"),
    REJECTED("rejected"),
    FINISHED("finished"),
    OBSOLETE("obsolete");

    private final String value;
  }

  @Builder
  @Value
  public static class Data implements Serializable {
    Type type;
    String signature;

    enum Type {
      NOVICE, NORMAL, EXPERT, PROFESSIONAL
    }
  }

  public static String generatePetitionEntityId() {
    return RandomStringUtils.randomAlphanumeric(8).toUpperCase();
  }

  @SneakyThrows
  public static Petition mapToPetition(PetitionEntity petitionEntity) {
    Petition petition = new Petition();
    petition.setId(petitionEntity.getId());
    petition.setData(Base64.getEncoder().encodeToString(SerializationUtils.serialize(petitionEntity.getData())));
    petition.setName(petitionEntity.getName());
    petition.setNif(petitionEntity.getNif());
    petition.setCreationTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(petitionEntity.getCreationTime().toString()));
    petition.setSignatureTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(petitionEntity.getSignatureTime().toString()));
    petition.setStatus(petitionEntity.getStatus().getValue());
    return petition;
  }
}
