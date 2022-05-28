package es.edudediegolucas.petition.ws.repository.petition;

import lombok.NonNull;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Repository
public class PetitionRepository {

  private static final Map<String, PetitionEntity> mapPetitions = new HashMap<>();

  @PostConstruct
  public void init() {
    IntStream.range(0, 1000)
      .forEach(ignored -> {
        final var id = PetitionEntity.generatePetitionEntityId();
        mapPetitions.put(id, buildPetitionEntity(id));
      });
  }

  public PetitionEntity getPetitionById(@NonNull String id) {
    return mapPetitions.get(id);
  }

  public List<PetitionEntity> getAllPetitions() {
    return new ArrayList<>(mapPetitions.values());
  }

  public String insertPetition() {
    final var id = PetitionEntity.generatePetitionEntityId();
    mapPetitions.put(id, buildPetitionEntity(id));
    return id;
  }

  private PetitionEntity buildPetitionEntity(String id) {
    final var creationTime = pickRandomDate(LocalDateTime.now());
    return PetitionEntity.builder()
      .id(id)
      .status(PetitionEntity.PetitionStatus.values()[ThreadLocalRandom.current().nextInt(0, 5)])
      .creationTime(creationTime)
      .signatureTime(pickRandomDate(creationTime))
      .name(RandomStringUtils.randomAlphanumeric(6))
      .nif(RandomStringUtils.randomAlphanumeric(8))
      .data(PetitionEntity.Data.builder()
        .signature(Base64.getEncoder().encodeToString(RandomUtils.nextBytes(64)))
        .type(PetitionEntity.Data.Type.values()[ThreadLocalRandom.current().nextInt(0, 4)])
        .build())
      .build();
  }

  private LocalDateTime pickRandomDate(LocalDateTime max) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(ThreadLocalRandom
      .current()
      .nextLong(LocalDateTime.now().minusYears(5).toInstant(ZoneOffset.UTC).toEpochMilli(),
        max.toInstant(ZoneOffset.UTC).toEpochMilli())), ZoneId.systemDefault());
  }

}
