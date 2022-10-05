package yuhan_3_2.EasyGym.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import yuhan_3_2.EasyGym.entity.Role;
import yuhan_3_2.EasyGym.entity.User;
import yuhan_3_2.EasyGym.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CredentialService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Role> userAuthorities = user.getRoles();

        for (Role role : userAuthorities) {
            authorities.add(new SimpleGrantedAuthority(role.getId().toString()));
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(user.getPassword()).authorities(authorities).build();
        return userDetails;
    }
}
