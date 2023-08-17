package com.social.mapper;

import com.social.domain.entities.User;
import com.social.dto.UserLoginDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.social.security.UserDetailsImpl;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "role", source = "user.role.name")
    @Mapping(target = "accessToken", source = "token")
    UserLoginDTO loginToDTO(User user, String token);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "enabled", defaultValue = "true")
    @Mapping(target = "locked", defaultValue = "false")
    UserDetailsImpl mapToUserDetails(User user);
}