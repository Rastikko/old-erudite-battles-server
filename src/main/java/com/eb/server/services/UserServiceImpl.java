package com.eb.server.services;

import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.boostrap.Bootstrap;
import com.eb.server.domain.User;
import com.eb.server.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO findUserDTOById(Long id) {

        return userMapper.userToUserDTO(userRepository.findOne(id));
    }

    @Override
    public User findUserByID(Long id) {

        return userRepository.findOne(id);
    }

    @Override
    public UserDTO createNewUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user.setDeck(Bootstrap.getDefaultDeck());
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
