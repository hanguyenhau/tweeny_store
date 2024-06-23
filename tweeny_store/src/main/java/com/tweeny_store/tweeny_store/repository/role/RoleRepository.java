package com.tweeny_store.tweeny_store.repository.role;

import com.tweeny_store.tweeny_store.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String role);
}
