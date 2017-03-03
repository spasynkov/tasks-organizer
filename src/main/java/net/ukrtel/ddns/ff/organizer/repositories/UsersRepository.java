package net.ukrtel.ddns.ff.organizer.repositories;

import net.ukrtel.ddns.ff.organizer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
