package com.rodolfozamora.webservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "role_tb")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
}
