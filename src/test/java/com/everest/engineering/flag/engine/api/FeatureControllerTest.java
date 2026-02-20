package com.everest.engineering.flag.engine.api;

import com.everest.engineering.flag.engine.entity.Feature;
import com.everest.engineering.flag.engine.entity.GroupOverride;
import com.everest.engineering.flag.engine.entity.RegionOverride;
import com.everest.engineering.flag.engine.entity.UserOverride;
import com.everest.engineering.flag.engine.service.FeatureEvaluationService;
import com.everest.engineering.flag.engine.service.FeatureService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeatureController.class)
class FeatureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeatureService featureService;

    @MockBean
    private FeatureEvaluationService evaluationService;

    @Autowired
    private ObjectMapper objectMapper;

    // ===============================
    // CREATE FEATURE
    // ===============================
    @Test
    void shouldCreateFeature() throws Exception {
        Feature feature = new Feature();
        feature.setId(1L);
        feature.setName("test-feature");
        feature.setDefaultEnabled(true);
        feature.setDescription("Test feature");

        when(featureService.createFeature("test-feature", true, "Test feature"))
                .thenReturn(feature);

        mockMvc.perform(post("/features")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(feature)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test-feature"))
                .andExpect(jsonPath("$.defaultEnabled").value(true));
    }

    // ===============================
    // UPDATE DEFAULT
    // ===============================
    @Test
    void shouldUpdateDefaultState() throws Exception {
        Feature feature = new Feature();
        feature.setId(1L);
        feature.setName("test-feature");
        feature.setDefaultEnabled(false);

        when(featureService.updateGlobalState("test-feature", false))
                .thenReturn(feature);

        mockMvc.perform(put("/features/test-feature/default")
                .param("enabled", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.defaultEnabled").value(false));
    }

    // ===============================
    // USER OVERRIDE
    // ===============================
    @Test
    void shouldUpdateUserOverride() throws Exception {
        Feature feature = new Feature();
        feature.setId(1L);
        feature.setName("test-feature");

        UserOverride override = new UserOverride();
        override.setId(10L);
        override.setFeature(feature);
        override.setUserId("user1");
        override.setEnabled(true);

        when(featureService.addOrUpdateUserOverride("test-feature", "user1", true))
                .thenReturn(override);

        mockMvc.perform(put("/features/test-feature/users/user1")
                .param("enabled", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("user1"))
                .andExpect(jsonPath("$.enabled").value(true))
                .andExpect(jsonPath("$.feature.name").value("test-feature"));
    }

    @Test
    void shouldRemoveUserOverride() throws Exception {
        doNothing().when(featureService)
                .removeUserOverride("test-feature", "user1");

        mockMvc.perform(delete("/features/test-feature/users/user1"))
                .andExpect(status().isOk());

        verify(featureService).removeUserOverride("test-feature", "user1");
    }

    // ===============================
    // GROUP OVERRIDE
    // ===============================
    @Test
    void shouldUpdateGroupOverride() throws Exception {
        Feature feature = new Feature();
        feature.setId(1L);
        feature.setName("test-feature");

        GroupOverride override = new GroupOverride();
        override.setId(20L);
        override.setFeature(feature);
        override.setGroupName("admin");
        override.setEnabled(false);

        when(featureService.addOrUpdateGroupOverride("test-feature", "admin", false))
                .thenReturn(override);

        mockMvc.perform(put("/features/test-feature/groups/admin")
                .param("enabled", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupName").value("admin"))
                .andExpect(jsonPath("$.enabled").value(false))
                .andExpect(jsonPath("$.feature.name").value("test-feature"));
    }

    @Test
    void shouldRemoveGroupOverride() throws Exception {
        doNothing().when(featureService)
                .removeGroupOverride("test-feature", "admin");

        mockMvc.perform(delete("/features/test-feature/groups/admin"))
                .andExpect(status().isOk());

        verify(featureService).removeGroupOverride("test-feature", "admin");
    }

    // ===============================
    // REGION OVERRIDE
    // ===============================
    @Test
    void shouldUpdateRegionOverride() throws Exception {
        Feature feature = new Feature();
        feature.setId(1L);
        feature.setName("test-feature");

        RegionOverride override = new RegionOverride();
        override.setId(30L);
        override.setFeature(feature);
        override.setRegion("US");
        override.setEnabled(true);

        when(featureService.addOrUpdateRegionOverride("test-feature", "US", true))
                .thenReturn(override);

        mockMvc.perform(put("/features/test-feature/regions/US")
                .param("enabled", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.region").value("US"))
                .andExpect(jsonPath("$.enabled").value(true))
                .andExpect(jsonPath("$.feature.name").value("test-feature"));
    }

    @Test
    void shouldRemoveRegionOverride() throws Exception {
        doNothing().when(featureService)
                .removeRegionOverride("test-feature", "US");

        mockMvc.perform(delete("/features/test-feature/regions/US"))
                .andExpect(status().isOk());

        verify(featureService).removeRegionOverride("test-feature", "US");
    }

    // ===============================
    // EVALUATE FEATURE
    // ===============================
    @Test
    void shouldEvaluateFeature() throws Exception {
        when(evaluationService.isFeatureEnabled("test-feature",
                "user1", "admin", "US"))
                .thenReturn(true);

        mockMvc.perform(get("/features/test-feature/evaluate")
                .param("userId", "user1")
                .param("groupName", "admin")
                .param("region", "US"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // ===============================
    // LIST FEATURES
    // ===============================
    @Test
    void shouldListAllFeatures() throws Exception {
        Feature feature = new Feature();
        feature.setId(1L);
        feature.setName("test-feature");

        when(featureService.listAllFeatures())
                .thenReturn(List.of(feature));

        mockMvc.perform(get("/features"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test-feature"));
    }

    // ===============================
    // GET FEATURE BY NAME
    // ===============================
    @Test
    void shouldGetFeatureByName() throws Exception {
        Feature feature = new Feature();
        feature.setId(1L);
        feature.setName("test-feature");

        when(featureService.getFeature("test-feature"))
                .thenReturn(feature);

        mockMvc.perform(get("/features/test-feature"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test-feature"));
    }
}