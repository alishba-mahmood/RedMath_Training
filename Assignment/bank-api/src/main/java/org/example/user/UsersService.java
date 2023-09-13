package org.example.user;

import org.example.account.Account;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService  implements UserDetailsService {

    private final UsersRepository usersRepository;
    @Value("${bank.db.like.operator:%}")    //used to set default value of things
    private String likeOperator;
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public void createUser(Long id,String name, String password, String roles, String status, Long account_id) {
        Users user = new Users();
        user.setUser_id(id);
        user.setUser_password(password);
        user.setUser_name(name);
        user.setUser_status(status);
        user.setUser_roles(roles);
        user.setAccount_id(account_id);
        usersRepository.save(user);
    }

    public List<Users> findAll() {
        return usersRepository.findAll();
    }
    public Optional<Users> getUserById(Long id)
    {
        return usersRepository.findById(id);
    }

    public void UpdateUser(Long id, Account account) {
        Users oldUser = usersRepository.getUserByAccId(id);
        oldUser.setUser_id(account.getAccount_id());
        oldUser.setUser_name(account.getName());
        oldUser.setUser_password("{noop}"+account.getName());
        oldUser.setUser_roles(oldUser.getUser_roles());
        oldUser.setUser_status(oldUser.getUser_status());
        oldUser.setAccount_id(account.getAccount_id());

        usersRepository.save(oldUser);

    }
    public Users findByUserName(String user_name)
    {
         return usersRepository.findByUserName(user_name);
    }

    public void deleteUserByAccID(Long bal_id)
    {
        usersRepository.deleteByAccountId(bal_id);
    }

    public List<Users> getUsersByNameLike(String name){
        return usersRepository.findByNameLike(likeOperator+name+likeOperator);
    }
    @Override
    public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {

        System.out.println("----------------------------in user service----------------------------\n");
        System.out.println(user_name);
        Users user = usersRepository.findByUserName(user_name);
        System.out.println(user.getUser_roles());
        if (user == null) {

            return null;
        }
        return new org.springframework.security.core.userdetails.User(user.getUser_name(), user.getUser_password(), true,
                true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getUser_roles()));
    }

    @Cacheable("users")
    public UserDetails loadUserByUsername(String jti, String userName) throws UsernameNotFoundException {
        System.out.println(("------------------------- in web config --------------------------\n"));
        System.out.println(userName);
        System.out.println(jti);
        System.out.println(("------------------------- out web config --------------------------\n"));
        return loadUserByUsername(userName);
    }
}