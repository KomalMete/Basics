package com.omsoftware.demo_4.serviceImpl;

import com.omsoftware.demo_4.models.Users;
import com.omsoftware.demo_4.models.request.UserRequest;
import com.omsoftware.demo_4.repository.UserRepository;
import com.omsoftware.demo_4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Object addUser(UserRequest userRequest) {
        Users users = new Users();
        users.setName(userRequest.getName());
        users.setEmail(userRequest.getEmail());
        //users.setPassword(userRequest.getPassword());

        //Bcrypt Password
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String bcryptPassword = bcrypt.encode(userRequest.getPassword());
        users.setPassword(bcryptPassword);
      return  userRepository.save(users);
    }
}
