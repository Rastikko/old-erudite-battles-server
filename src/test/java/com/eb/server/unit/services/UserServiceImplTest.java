package com.eb.server.unit.services;

import com.eb.server.api.v1.mapper.UserMapper;
import com.eb.server.api.v1.model.UserDTO;
import com.eb.server.domain.User;
import com.eb.server.repositories.UserRepository;
import com.eb.server.services.UserService;
import com.eb.server.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    public static final Long ID = 1L;
    public static final String NAME = "Johan";

    UserService userService;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(UserMapper.INSTANCE, userRepository);
    }

    @Test
    public void getUserById() throws Exception {
        User user = new User();
        user.setId(ID);
        user.setName(NAME);

        when(userRepository.findOne(anyLong())).thenReturn(user);

        UserDTO userDTO = userService.findUserById(ID);

        assertEquals(ID, userDTO.getId());
        assertEquals(NAME, userDTO.getName());
    }

    /*

            //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");
        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1l);
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        //when
        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);
        //then
        assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());

     */
    @Test
    public void createNewUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("Rastikko");

        User savedUser = new User();
        savedUser.setName(userDTO.getName());
        savedUser.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO savedDto = userService.createNewUser(userDTO);
        assertEquals(userDTO.getName(), savedDto.getName());
    }
}
