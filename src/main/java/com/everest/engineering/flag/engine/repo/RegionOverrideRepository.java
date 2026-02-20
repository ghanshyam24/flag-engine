package com.everest.engineering.flag.engine.repo;

import java.util.Optional;

import com.everest.engineering.flag.engine.entity.Feature;
import com.everest.engineering.flag.engine.entity.RegionOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionOverrideRepository extends JpaRepository<RegionOverride, Long> {
    Optional<RegionOverride> findByFeatureAndRegion(Feature feature, String region);
}
