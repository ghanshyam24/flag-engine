package com.everest.engineering.flag.engine.repo;

import java.util.Optional;

import com.everest.engineering.flag.engine.entity.Feature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class FeatureRepositoryTest {

    @Autowired
    private FeatureRepository featureRepository;

    @Test
    void saveAndFindFeature() {
        Feature feature = new Feature();
        feature.setName("dark_mode");
        feature.setDefaultEnabled(true);
        feature.setDescription("Enable dark mode UI");

        featureRepository.save(feature);

        Optional<Feature> found = featureRepository.findByName("dark_mode");
        assertTrue(found.isPresent());
        assertEquals("dark_mode", found.get().getName());
        assertTrue(found.get().isDefaultEnabled());
    }
}
