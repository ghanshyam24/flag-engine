package com.everest.engineering.flag.engine.api;

import com.everest.engineering.flag.engine.entity.Feature;
import com.everest.engineering.flag.engine.entity.UserOverride;
import com.everest.engineering.flag.engine.repo.FeatureRepository;
import com.everest.engineering.flag.engine.repo.UserOverrideRepository;
import com.everest.engineering.flag.engine.service.FeatureEvaluationService;
 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FeatureIntegrationTest {

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private UserOverrideRepository userOverrideRepository;

    @Autowired
    private FeatureEvaluationService evaluationService;

    @Test
    void fullFlowEvaluationTest() {
        Feature feature = new Feature();
        feature.setName("dark_mode");
        feature.setDefaultEnabled(false);
        featureRepository.save(feature);

        UserOverride override = new UserOverride();
        override.setFeature(feature);
        override.setUserId("user1");
        override.setEnabled(true);
        userOverrideRepository.save(override);

        boolean result = evaluationService.isFeatureEnabled("dark_mode", "user1", null, null);
        assertTrue(result);
    }
}
