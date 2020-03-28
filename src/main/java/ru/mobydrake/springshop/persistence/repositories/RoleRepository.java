package ru.mobydrake.springshop.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.mobydrake.springshop.persistence.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findOneByName(String name);
}