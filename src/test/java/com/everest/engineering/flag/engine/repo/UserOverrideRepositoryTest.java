package com.everest.engineering.flag.engine.repo;

import com.everest.engineering.flag.engine.entity.Feature;
import com.everest.engineering.flag.engine.entity.UserOverride;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@DataJpaTest
public class UserOverrideRepositoryTest {

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private UserOverrideRepository userOverrideRepository;

    @Test
    void saveAndFindUserOverride() {
        Feature feature = new Feature();
        feature.setName("dark_mode");
        feature.setDefaultEnabled(false);
        featureRepository.save(feature);

        UserOverride override = new UserOverride();
        override.setFeature(feature);
        override.setUserId("user1");
        override.setEnabled(true);

        userOverrideRepository.save(override);

        Optional<UserOverride> found =
                userOverrideRepository.findByFeatureAndUserId(feature, "user1");

        assertTrue(found.isPresent());
        assertTrue(found.get().isEnabled());
    }
}
