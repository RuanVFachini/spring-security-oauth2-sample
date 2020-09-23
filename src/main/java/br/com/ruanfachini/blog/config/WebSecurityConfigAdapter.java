package br.com.ruanfachini.blog.config;

import br.com.ruanfachini.blog.domains.Role;
import br.com.ruanfachini.blog.domains.User;
import br.com.ruanfachini.blog.domains.dto.UserDetailDTO;
import br.com.ruanfachini.blog.repositories.IRoleRepository;
import br.com.ruanfachini.blog.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManagerBean();
    }

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder builder, IUserRepository userRepository,
                                      IRoleRepository roleRepository)
            throws Exception {

        if (roleRepository.count() == 0) {
            Role role = new Role("ADMIN");
            roleRepository.save(role);
        }

        if (userRepository.count() == 0) {
            String login = "admin";
            String password = passwordEncoder().encode("admin");
            Role role = roleRepository.findByName("ADMNI");
            List<Role> roles = Collections.singletonList(role);
            User user = new User(login, password, roles);
            userRepository.save(user);
        }

        builder.userDetailsService(login -> new UserDetailDTO(userRepository.findByLogin(login)))
                .passwordEncoder(passwordEncoder());
    }

}
