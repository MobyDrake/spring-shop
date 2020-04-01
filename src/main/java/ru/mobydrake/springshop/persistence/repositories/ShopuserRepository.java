package ru.mobydrake.springshop.persistence.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mobydrake.springshop.persistence.entities.Shopuser;

import java.util.UUID;

@Repository
public interface ShopuserRepository extends CrudRepository<Shopuser, UUID> {
    Shopuser findOneByPhone(String phone);
    boolean existsByPhone(String phone);
}