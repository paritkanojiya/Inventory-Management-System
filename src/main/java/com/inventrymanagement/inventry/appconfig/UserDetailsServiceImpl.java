package com.inventrymanagement.inventry.appconfig;

import com.inventrymanagement.inventry.entity.Role;
import com.inventrymanagement.inventry.entity.User;
import com.inventrymanagement.inventry.repository.RoleRepo;
import com.inventrymanagement.inventry.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RoleRepo roleRepository;
    private final UserRepo userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user==null)
            throw new UsernameNotFoundException("Unauthorized");
        List<Role> roleList = roleRepository.findByUser(user);
        return new UserDetailsImpl(roleList, user);
    }
}
