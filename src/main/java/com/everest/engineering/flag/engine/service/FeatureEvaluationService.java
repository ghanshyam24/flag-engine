package com.everest.engineering.flag.engine.service;


import java.util.Optional;

import com.everest.engineering.flag.engine.entity.Feature;
import com.everest.engineering.flag.engine.entity.GroupOverride;
import com.everest.engineering.flag.engine.entity.RegionOverride;
import com.everest.engineering.flag.engine.entity.UserOverride;
import com.everest.engineering.flag.engine.exceptions.FeatureNotFoundException;
import com.everest.engineering.flag.engine.repo.FeatureRepository;
import com.everest.engineering.flag.engine.repo.GroupOverrideRepository;
import com.everest.engineering.flag.engine.repo.RegionOverrideRepository;
import com.everest.engineering.flag.engine.repo.UserOverrideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeatureEvaluationService {

    private final FeatureRepository featureRepository;
    private final UserOverrideRepository userOverrideRepository;
    private final GroupOverrideRepository groupOverrideRepository;
    private final RegionOverrideRepository regionOverrideRepository;

    public boolean isFeatureEnabled(String featureName, String userId, String groupName, String region) {

        Feature feature = featureRepository.findByName(featureName)
                .orElseThrow(() -> new FeatureNotFoundException(featureName));

        // 1️⃣ User override
        if (userId != null) {
            Optional<UserOverride> userOverride =
                    userOverrideRepository.findByFeatureAndUserId(feature, userId);
            if (userOverride.isPresent()) return userOverride.get().isEnabled();
        }

        // 2️⃣ Group override
        if (groupName != null) {
            Optional<GroupOverride> groupOverride =
                    groupOverrideRepository.findByFeatureAndGroupName(feature, groupName);
            if (groupOverride.isPresent()) return groupOverride.get().isEnabled();
        }

        // 3️⃣ Region override
        if (region != null) {
            Optional<RegionOverride> regionOverride =
                    regionOverrideRepository.findByFeatureAndRegion(feature, region);
            if (regionOverride.isPresent()) return regionOverride.get().isEnabled();
        }

        // 4️⃣ Global default
        return feature.isDefaultEnabled();
    }
}
