package com.social.command;

import lombok.Data;

@Data
public class LoginCommand {
    private String email;
    private String username;
    private String password;
}