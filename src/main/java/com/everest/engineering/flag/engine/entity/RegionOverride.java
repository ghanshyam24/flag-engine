package com.everest.engineering.flag.engine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "region_overrides", uniqueConstraints = @UniqueConstraint(columnNames = {"feature_id", "region"}))
@Getter
@Setter
@NoArgsConstructor
public class RegionOverride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private boolean enabled;
}
