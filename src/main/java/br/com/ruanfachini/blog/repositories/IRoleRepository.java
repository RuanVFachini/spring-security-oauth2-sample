package br.com.ruanfachini.blog.repositories;

import br.com.ruanfachini.blog.domains.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
