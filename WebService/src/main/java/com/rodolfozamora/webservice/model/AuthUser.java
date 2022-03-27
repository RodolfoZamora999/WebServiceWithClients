package com.rodolfozamora.webservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthUser {
    private  String name;
    private  String password;
}
