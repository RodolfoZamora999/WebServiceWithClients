package com.rodolfozamora.webservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_tb")
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    @Column(name = "image_profile")
    private String imageProfile;


    @ManyToOne
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    @JsonIgnore
    private Role role;
}
