package io.github.sistemademandaszoo.zoodemandas.repository;

import io.github.sistemademandaszoo.zoodemandas.model.Demanda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandaRepository extends JpaRepository<Demanda, String> {
}
