package lbd.fissst.securitylbd.service.implementation;

import lbd.fissst.securitylbd.security.UserPermissions;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private List<UserDetails> getUsers(){
        List<UserDetails> users = new ArrayList<>();

        users.add(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .authorities(
                                UserPermissions.ADMIN.getPermission()

                                )
                        .build()
        );
        users.add(
                User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("user"))
                        .authorities(
                                UserPermissions.USER_EDIT.getPermission(),
                                UserPermissions.USER_READ.getPermission()
                        )
                        .build()
        );
        users.add(
                User.builder()
                        .username("spectator")
                        .password(passwordEncoder.encode("spectator"))
                        .authorities(
                                UserPermissions.USER_READ.getPermission()
                        )
                        .build()
        );

        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  getUsers().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("Username %s not found", username))
                );
    }
}
