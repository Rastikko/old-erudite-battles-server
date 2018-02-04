package com.eb.server.services;

import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.bootstrap.Bootstrap;
import com.eb.server.domain.User;
import com.eb.server.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final Bootstrap bootstrap;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, Bootstrap bootstrap) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.bootstrap = bootstrap;
    }

    @Override
    public UserDTO findUserDTOById(Long id) {

        return userMapper.userToUserDTO(findUserByID(id));
    }

    @Override
    public User findUserByID(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public UserDTO findUserDTOByName(String name) {
        User user = userRepository.findFirstByName(name);
        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO createNewUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user.setDeck(bootstrap.getDefaultDeck());
        return saveAndReturnDTO(user);
    }

    @Override
    public UserDTO updateUser(User user) {
        return saveAndReturnDTO(user);
    }

    private UserDTO saveAndReturnDTO(User user) {
        return userMapper.userToUserDTO(userRepository.save(user));
    }

}
