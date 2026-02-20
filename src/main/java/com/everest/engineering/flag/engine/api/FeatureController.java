package com.everest.engineering.flag.engine.api;

import com.everest.engineering.flag.engine.entity.Feature;
import com.everest.engineering.flag.engine.entity.GroupOverride;
import com.everest.engineering.flag.engine.entity.RegionOverride;
import com.everest.engineering.flag.engine.entity.UserOverride;
import com.everest.engineering.flag.engine.service.FeatureEvaluationService;
import com.everest.engineering.flag.engine.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/features")
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;
    private final FeatureEvaluationService evaluationService;

    @PostMapping
    public Feature createFeature(@RequestBody Feature feature) {
        return featureService.createFeature(feature.getName(),
                feature.isDefaultEnabled(), feature.getDescription());
    }

    @PutMapping("/{name}/default")
    public Feature updateDefault(@PathVariable String name,
                                 @RequestParam boolean enabled) {
        return featureService.updateGlobalState(name, enabled);
    }

    @PutMapping("/{name}/users/{userId}")
    public UserOverride updateUserOverride(@PathVariable String name,
                                           @PathVariable String userId,
                                           @RequestParam boolean enabled) {
        return featureService.addOrUpdateUserOverride(name, userId, enabled);
    }

    @PutMapping("/{name}/groups/{groupName}")
    public GroupOverride updateGroupOverride(@PathVariable String name,
                                             @PathVariable String groupName,
                                             @RequestParam boolean enabled) {
        return featureService.addOrUpdateGroupOverride(name, groupName, enabled);
    }

    @PutMapping("/{name}/regions/{region}")
    public RegionOverride updateRegionOverride(@PathVariable String name,
                                               @PathVariable String region,
                                               @RequestParam boolean enabled) {
        return featureService.addOrUpdateRegionOverride(name, region, enabled);
    }

    @DeleteMapping("/{name}/users/{userId}")
    public void removeUserOverride(@PathVariable String name,
                                   @PathVariable String userId) {
        featureService.removeUserOverride(name, userId);
    }

    @DeleteMapping("/{name}/groups/{groupName}")
    public void removeGroupOverride(@PathVariable String name,
                                    @PathVariable String groupName) {
        featureService.removeGroupOverride(name, groupName);
    }

    @DeleteMapping("/{name}/regions/{region}")
    public void removeRegionOverride(@PathVariable String name,
                                     @PathVariable String region) {
        featureService.removeRegionOverride(name, region);
    }

    @GetMapping("/{name}/evaluate")
    public boolean evaluate(@PathVariable String name,
                            @RequestParam(required = false) String userId,
                            @RequestParam(required = false) String groupName,
                            @RequestParam(required = false) String region) {
        return evaluationService.isFeatureEnabled(name, userId, groupName, region);
    }

    @GetMapping
    public List<Feature> listFeatures() {
        return featureService.listAllFeatures();
    }

    @GetMapping("/{name}")
    public Feature getFeature(@PathVariable String name) {
        return featureService.getFeature(name);
    }
}
