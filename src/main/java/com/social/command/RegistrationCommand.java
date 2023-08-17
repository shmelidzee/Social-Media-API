package com.social.command;

import lombok.Data;

@Data
public class RegistrationCommand {
    private String username;
    private String email;
    private String password;
}