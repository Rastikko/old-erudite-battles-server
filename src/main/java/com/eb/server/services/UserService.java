package com.eb.server.services;

import com.eb.server.api.v1.model.UserDTO;

public interface UserService {
    UserDTO findUserById(Long id);
    UserDTO createNewUser(UserDTO user);
}
