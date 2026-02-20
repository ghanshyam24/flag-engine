package com.everest.engineering.flag.engine.repo;

import java.util.Optional;

import com.everest.engineering.flag.engine.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    Optional<Feature> findByName(String name);
}
