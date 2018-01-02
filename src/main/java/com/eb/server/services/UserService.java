package com.eb.server.services;

import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.domain.User;

public interface UserService {
    UserDTO findUserDTOById(Long id);
    User findUserByID(Long id);
    UserDTO createNewUser(UserDTO user);
    UserDTO updateUser(User user);
}
