package br.com.ruanfachini.blog.repositories;

import br.com.ruanfachini.blog.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
}
