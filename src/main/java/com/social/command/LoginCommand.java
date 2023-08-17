package com.social.command;

import lombok.Data;

@Data
public class LoginCommand {
    private String login;
    private String password;
}