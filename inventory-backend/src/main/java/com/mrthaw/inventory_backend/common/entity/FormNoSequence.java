package com.mrthaw.inventory_backend.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "form_no_sequence",
    uniqueConstraints = @UniqueConstraint(columnNames = {"form_type", "date"}))
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormNoSequence extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer formType;

    private LocalDate date;

    private String prefix;

    private Long lastSequence;
}
