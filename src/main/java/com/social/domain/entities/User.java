package com.social.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@SequenceGenerator(name = "SQ_USER_ID_GENERATOR", sequenceName = "SQ_USER_ID", allocationSize = 1)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_USER_ID_GENERATOR")
    @Column(name = "ID", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "USERNAME", nullable = false, length = 32)
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @NonNull
    @Column(name = "EMAIL", nullable = false, length = 64)
    private String email;

    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    private Role role;
}