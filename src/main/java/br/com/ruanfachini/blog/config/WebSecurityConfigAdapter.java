package br.com.ruanfachini.blog.config;

import br.com.ruanfachini.blog.domains.User;
import br.com.ruanfachini.blog.domains.dto.UserDetailDTO;
import br.com.ruanfachini.blog.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    public void authenticationManager(AuthenticationManagerBuilder builder, IUserRepository usuarioRepository) throws Exception {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new User("admin", passwordEncoder().encode("admin")));
        }

        builder.userDetailsService(login -> new UserDetailDTO(usuarioRepository.findByLogin(login)))
                .passwordEncoder(passwordEncoder());
    }

}
