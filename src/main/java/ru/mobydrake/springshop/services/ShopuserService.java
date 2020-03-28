package ru.mobydrake.springshop.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mobydrake.springshop.persistence.entities.Role;
import ru.mobydrake.springshop.persistence.entities.Shopuser;
import ru.mobydrake.springshop.persistence.repositories.RoleRepository;
import ru.mobydrake.springshop.persistence.repositories.ShopuserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopuserService implements UserDetailsService {

    private final ShopuserRepository shopuserRepository;
    private final RoleRepository roleRepository;

    public Shopuser findByPhone(String phone) {
        return shopuserRepository.findOneByPhone(phone);
    }

    public Shopuser getAnonymousUser() {
        return shopuserRepository.findOneByPhone("anonymous");
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Shopuser shopuser = shopuserRepository.findOneByPhone(username);
        if (shopuser == null) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new User(shopuser.getPhone(), shopuser.getPassword(), mapRolesToAuthorities(shopuser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public boolean isUserExist(String phone) {
        return shopuserRepository.existsByPhone(phone);
    }

}
