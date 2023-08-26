package com.carp.carpentry.services;

import Dto.UserResponseDto;
import com.carp.carpentry.entity.Address;
import com.carp.carpentry.entity.Department;
import com.carp.carpentry.entity.User;
import com.carp.carpentry.repository.DepartmentRepository;
import com.carp.carpentry.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public UserDetailService(UserRepository userRepository,DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.carp.carpentry.entity.User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), new ArrayList<>()
        );
    }

    //
    public com.carp.carpentry.entity.User CreateUser(String username, String password, Address address, Department department){

        com.carp.carpentry.entity.User user = new com.carp.carpentry.entity.User();


        Department department1 = departmentRepository.findByDepName(department.getDepName());

        user.setUsername(username);
        user.setPassword(password);
        user.setAddress(address);

        if (department1 != null) {
            user.setDepartment(department1);
        }else {
            user.setDepartment(department);
        }

        return userRepository.save(user);
    }
    @Transactional
    public List<UserResponseDto> getUsers() {
        List<User> response =  userRepository.findAll();
        List<UserResponseDto> responseDtos = new ArrayList<>();
        for (User user : response) {
            //System.out.println(user);
            // Initialize lazy-loaded properties within the transactional context
            //Hibernate.initialize(user.getAddress());
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setUsername(user.getUsername());
            userResponseDto.setAddress(user.getAddress());
            userResponseDto.setDepartment(user.getDepartment());

            responseDtos.add(userResponseDto);
        }
        return responseDtos;
    }
}
