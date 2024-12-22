package com.inventrymanagement.inventry.service;

import com.inventrymanagement.inventry.entity.Role;
import com.inventrymanagement.inventry.entity.User;
import com.inventrymanagement.inventry.exceptionhandler.UserAlreadyExits;
import com.inventrymanagement.inventry.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepository;

    public User saveUser(User user){
        Role role=new Role();
        role.setUserRole("ROLE_USER");
        role.setUser(user);
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    public void isUserExist(User user) throws UserAlreadyExits {
        User exitsUser = userRepository.findByEmail(user.getEmail());
        if(exitsUser!=null){
            throw new UserAlreadyExits("provided email user is already exits");
        }
    }


}
