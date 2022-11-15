package de.viadee.bpm.camunda.service;

import model.Vertrag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static java.time.LocalDate.now;
import static org.apache.commons.lang3.StringUtils.leftPad;
import static org.apache.commons.lang3.StringUtils.substring;

@Service
public class VertragService {

  private final VertragRepository repository;

  public VertragService() {
    repository = new VertragRepository();
  }

  public Vertrag getVertragById(final String id) {
    return repository.findById(id);
  }

  private static class VertragRepository {

    Vertrag findById(final String id) {
      if (StringUtils.contains(id, "42")) {
        throw new VertragNotFound(id);
      }
      var vertrag = new Vertrag();
      vertrag.setId(id);
      var pnr = leftPad(substring(String.valueOf(Objects.hash(id)), -5), 5, '0');
      vertrag.setPolicyHolder("PNR" + pnr);
      vertrag.setBeginn(now().minusMonths(8));
      vertrag.setTarif("TF_1911");
      return vertrag;
    }
  }

  private static class VertragNotFound extends RuntimeException {
    public VertragNotFound(final String id) {
      super("Vertrag nicht gefunden, Id=" + id);
    }
  }

}
