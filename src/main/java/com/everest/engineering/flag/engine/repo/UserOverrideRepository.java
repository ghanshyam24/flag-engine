package com.everest.engineering.flag.engine.repo;

import com.everest.engineering.flag.engine.entity.Feature;
import com.everest.engineering.flag.engine.entity.UserOverride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserOverrideRepository extends JpaRepository<UserOverride, Long> {
    Optional<UserOverride> findByFeatureAndUserId(Feature feature, String userId);
}
