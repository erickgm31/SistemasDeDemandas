package io.github.cursodsousa.zoodemandas.repository;

import io.github.cursodsousa.zoodemandas.model.Demanda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandaRepository extends JpaRepository<Demanda, String> {
}
