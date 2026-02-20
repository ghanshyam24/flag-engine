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
@Table(name = "user_overrides", uniqueConstraints = @UniqueConstraint(columnNames = {"feature_id", "userId"}))
@Getter
@Setter
@NoArgsConstructor
public class UserOverride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private boolean enabled;
}
