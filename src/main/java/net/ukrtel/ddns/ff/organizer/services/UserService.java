package net.ukrtel.ddns.ff.organizer.services;

import net.ukrtel.ddns.ff.organizer.domain.User;
import net.ukrtel.ddns.ff.organizer.exceptions.DuplicateUsernameException;
import net.ukrtel.ddns.ff.organizer.exceptions.UserNotFoundException;
import net.ukrtel.ddns.ff.organizer.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);

        if (user != null) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (user.isAdmin()) authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            else authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities);
        }

        throw new UsernameNotFoundException("User '" + username + "' not found.");
    }

    public User addUser(String username, String password) {
        User user = createUser(username, password, false);
        return usersRepository.save(user);
    }

    public User addAdmin(String username, String password) {
        User admin = createUser(username, password, true);
        return usersRepository.save(admin);
    }

    public void updateUser(long id, String username, String password, boolean isAdmin) {
        User user = usersRepository.findOne(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(isAdmin);
        usersRepository.save(user);
    }

    public void changePassword(long userId, String newPassword) {
        User user = usersRepository.findOne(userId);
        user.setPassword(newPassword);
        usersRepository.save(user);
    }

    public void removeUserById(long id) {
        usersRepository.delete(id);
    }

    private User createUser(String username, String password, boolean isAdmin) {
        if ("me".equals(username)) {
            throw new DuplicateUsernameException("Can't add user with name 'me'. Such name is in use already.");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAdmin(isAdmin);
        return user;
    }

    public User findUserByName(String name, boolean secured) {
        User user = usersRepository.findByUsername(name);
        if (user == null) throw new UserNotFoundException("Can't find user '" + name + "'.");
        if (secured) user.setPassword(null);
        return user;
    }

    public User findUserById(long id, boolean secured) {
        User user = usersRepository.findOne(id);
        if (user == null) throw new UserNotFoundException("Can't find user '" + id + "'.");
        if (secured) user.setPassword(null);
        return user;
    }

    public List<String> collectAllUsersNames() {
        return usersRepository.findAll().stream().map(User::getUsername).collect(Collectors.toList());
    }
}
