package org.example.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.account.Account;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService  implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public void createUser(String name, String password, String roles, String status, Long account_id) {
        System.out.println("in create users service------------------------------------------------");
        Users user = new Users();
        user.setUser_password(password);
        user.setUser_name(name);
        user.setUser_status(status);
        user.setUser_roles(roles);
        user.setAccount_id(account_id);
        usersRepository.save(user);
    }
    public Optional<Users> getUserById(Long id)
    {
        Optional<Users> user = usersRepository.findById(id);
        if(user.isPresent())
        {
            return user;
        }
        return null;
    }

    public void UpdateUser(Long id, Account account) {
        Optional<Users> oldUser = getUserById(id);
        Users updatedUser = oldUser.get();
        updatedUser.setUser_id(account.getAccount_id());
        updatedUser.setUser_name(account.getName());
        updatedUser.setUser_password("{noop}"+account.getName());
        updatedUser.setUser_roles(updatedUser.getUser_roles());
        updatedUser.setUser_status(updatedUser.getUser_status());
        updatedUser.setAccount_id(account.getAccount_id());

        usersRepository.save(updatedUser);

    }
    public Users findByUserName(String user_name) {
        return usersRepository.findByUserName(user_name);
    }

    public void deleteUserByAccID(Long bal_id)
    {
        System.out.println("in delete users service------------------------------------------------");
        usersRepository.deleteByAccountId(bal_id);
    }
    @Override
    public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {

        logger.debug("user login start: {}", user_name);
        System.out.println("\n\n\n in user service details ");
        Users user = usersRepository.findByUserName(user_name);
        if (user == null) {

            return null;
        }
        return new org.springframework.security.core.userdetails.User(user.getUser_name(), user.getUser_password(), true,
                true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getUser_roles()));
    }

}