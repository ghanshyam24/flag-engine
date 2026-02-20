package com.everest.engineering.flag.engine.service;

import com.everest.engineering.flag.engine.entity.Feature;
import com.everest.engineering.flag.engine.entity.UserOverride;
import com.everest.engineering.flag.engine.exceptions.FeatureNotFoundException;
import com.everest.engineering.flag.engine.repo.FeatureRepository;
import com.everest.engineering.flag.engine.repo.GroupOverrideRepository;
import com.everest.engineering.flag.engine.repo.RegionOverrideRepository;
import com.everest.engineering.flag.engine.repo.UserOverrideRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FeatureEvaluationServiceTest {

    @Mock
    private FeatureRepository featureRepository;

    @Mock
    private UserOverrideRepository userOverrideRepository;

    @Mock
    private GroupOverrideRepository groupOverrideRepository;

    @Mock
    private RegionOverrideRepository regionOverrideRepository;

    @InjectMocks
    private FeatureEvaluationService evaluationService;

    private Feature feature;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        feature = new Feature();
        feature.setName("dark_mode");
        feature.setDefaultEnabled(false);
    }

    @Test
    void shouldReturnUserOverrideIfExists() {
        when(featureRepository.findByName("dark_mode")).thenReturn(Optional.of(feature));
        UserOverride userOverride = new UserOverride();
        userOverride.setEnabled(true);
        when(userOverrideRepository.findByFeatureAndUserId(feature, "user1"))
                .thenReturn(Optional.of(userOverride));

        boolean result = evaluationService.isFeatureEnabled("dark_mode", "user1", null, null);
        assertTrue(result);
    }

    @Test
    void shouldReturnDefaultIfNoOverrides() {
        when(featureRepository.findByName("dark_mode")).thenReturn(Optional.of(feature));
        when(userOverrideRepository.findByFeatureAndUserId(feature, "user1")).thenReturn(Optional.empty());
        when(groupOverrideRepository.findByFeatureAndGroupName(feature, "beta")).thenReturn(Optional.empty());
        when(regionOverrideRepository.findByFeatureAndRegion(feature, "us-east")).thenReturn(Optional.empty());

        boolean result = evaluationService.isFeatureEnabled("dark_mode", "user1", "beta", "us-east");
        assertFalse(result);
    }

    @Test
    void shouldThrowIfFeatureNotFound() {
        when(featureRepository.findByName("unknown")).thenReturn(Optional.empty());
        assertThrows(FeatureNotFoundException.class,
                () -> evaluationService.isFeatureEnabled("unknown", null, null, null));
    }
}
