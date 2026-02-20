package com.everest.engineering.flag.engine.repo;

import com.everest.engineering.flag.engine.entity.Feature;
import com.everest.engineering.flag.engine.entity.GroupOverride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupOverrideRepository extends JpaRepository<GroupOverride, Long> {
    Optional<GroupOverride> findByFeatureAndGroupName(Feature feature, String groupName);
}
