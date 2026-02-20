package com.everest.engineering.flag.engine.service;

import java.util.Optional;

import com.everest.engineering.flag.engine.entity.Feature;
import com.everest.engineering.flag.engine.entity.GroupOverride;
import com.everest.engineering.flag.engine.entity.RegionOverride;
import com.everest.engineering.flag.engine.entity.UserOverride;
import com.everest.engineering.flag.engine.exceptions.FeatureAlreadyExistsException;
import com.everest.engineering.flag.engine.exceptions.FeatureNotFoundException;
import com.everest.engineering.flag.engine.repo.FeatureRepository;
import com.everest.engineering.flag.engine.repo.GroupOverrideRepository;
import com.everest.engineering.flag.engine.repo.RegionOverrideRepository;
import com.everest.engineering.flag.engine.repo.UserOverrideRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;
    private final UserOverrideRepository userOverrideRepository;
    private final GroupOverrideRepository groupOverrideRepository;
    private final RegionOverrideRepository regionOverrideRepository;

    public Feature createFeature(String name, boolean defaultEnabled, String description) {
        featureRepository.findByName(name)
                .ifPresent(f -> { throw new FeatureAlreadyExistsException(name); });
        Feature feature = new Feature();
        feature.setName(name);
        feature.setDefaultEnabled(defaultEnabled);
        feature.setDescription(description);
        return featureRepository.save(feature);
    }

    public Feature updateGlobalState(String name, boolean enabled) {
        Feature feature = featureRepository.findByName(name)
                .orElseThrow(() -> new FeatureNotFoundException(name));
        feature.setDefaultEnabled(enabled);
        return featureRepository.save(feature);
    }

    public UserOverride addOrUpdateUserOverride(String featureName, String userId, boolean enabled) {
        Feature feature = featureRepository.findByName(featureName)
                .orElseThrow(() -> new FeatureNotFoundException(featureName));
        return userOverrideRepository.findByFeatureAndUserId(feature, userId)
                .map(o -> { o.setEnabled(enabled); return userOverrideRepository.save(o); })
                .orElseGet(() -> {
                    UserOverride o = new UserOverride();
                    o.setFeature(feature);
                    o.setUserId(userId);
                    o.setEnabled(enabled);
                    return userOverrideRepository.save(o);
                });
    }

    public GroupOverride addOrUpdateGroupOverride(String featureName, String groupName, boolean enabled) {
        Feature feature = featureRepository.findByName(featureName)
                .orElseThrow(() -> new FeatureNotFoundException(featureName));
        return groupOverrideRepository.findByFeatureAndGroupName(feature, groupName)
                .map(o -> { o.setEnabled(enabled); return groupOverrideRepository.save(o); })
                .orElseGet(() -> {
                    GroupOverride o = new GroupOverride();
                    o.setFeature(feature);
                    o.setGroupName(groupName);
                    o.setEnabled(enabled);
                    return groupOverrideRepository.save(o);
                });
    }

    public RegionOverride addOrUpdateRegionOverride(String featureName, String region, boolean enabled) {
        Feature feature = featureRepository.findByName(featureName)
                .orElseThrow(() -> new FeatureNotFoundException(featureName));
        return regionOverrideRepository.findByFeatureAndRegion(feature, region)
                .map(o -> { o.setEnabled(enabled); return regionOverrideRepository.save(o); })
                .orElseGet(() -> {
                    RegionOverride o = new RegionOverride();
                    o.setFeature(feature);
                    o.setRegion(region);
                    o.setEnabled(enabled);
                    return regionOverrideRepository.save(o);
                });
    }

    public void removeUserOverride(String featureName, String userId) {
        Feature feature = featureRepository.findByName(featureName)
                .orElseThrow(() -> new FeatureNotFoundException(featureName));
        userOverrideRepository.findByFeatureAndUserId(feature, userId).ifPresent(userOverrideRepository::delete);
    }

    public void removeGroupOverride(String featureName, String groupName) {
        Feature feature = featureRepository.findByName(featureName)
                .orElseThrow(() -> new FeatureNotFoundException(featureName));
        groupOverrideRepository.findByFeatureAndGroupName(feature, groupName).ifPresent(groupOverrideRepository::delete);
    }

    public void removeRegionOverride(String featureName, String region) {
        Feature feature = featureRepository.findByName(featureName)
                .orElseThrow(() -> new FeatureNotFoundException(featureName));
        regionOverrideRepository.findByFeatureAndRegion(feature, region).ifPresent(regionOverrideRepository::delete);
    }

    public List<Feature> listAllFeatures() {
        return featureRepository.findAll();
    }

    public Feature getFeature(String name) {
        return featureRepository.findByName(name).orElseThrow(() -> new FeatureNotFoundException(name));
    }
}
