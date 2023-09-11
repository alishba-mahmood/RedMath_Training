package org.example.user;

import org.example.account.Account;
import org.springframework.beans.factory.annotation.Value;
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
        System.out.println("in create users service------------------------------------------------");
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
        Optional<Users> user = usersRepository.findById(id);
        if(user.isPresent())
        {
            return user;
        }
        return null;
    }

    public void UpdateUser(Long id, Account account) {

        System.out.println("\n\n");
        System.out.println("in update user service------------------------------------------------");

        Users oldUser = usersRepository.getUserByAccId(id);
        System.out.println("before mofification : "+oldUser.getUser_id());
        oldUser.setUser_id(account.getAccount_id());
        oldUser.setUser_name(account.getName());
        oldUser.setUser_password("{noop}"+account.getName());
        oldUser.setUser_roles(oldUser.getUser_roles());
        oldUser.setUser_status(oldUser.getUser_status());
        oldUser.setAccount_id(account.getAccount_id());
        System.out.println("after mofification : "+oldUser.getUser_id());

        usersRepository.save(oldUser);

    }
    public Users findByUserName(String user_name)
    {
        System.out.println("in find user by name service------------------------------------------------");
         return usersRepository.findByUserName(user_name);
    }

    public void deleteUserByAccID(Long bal_id)
    {
        System.out.println("in delete users service------------------------------------------------");
        usersRepository.deleteByAccountId(bal_id);
    }

    public List<Users> getUsersByNameLike(String name){
        return usersRepository.findByNameLike(likeOperator+name+likeOperator);
    }
    @Override
    public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {
        System.out.println("\n\n\n in user service details ");
        System.out.println(user_name);
        System.out.println("\n\n\n in user service details ");
        Users user = usersRepository.findByUserName(user_name);
        System.out.println("\n\n\n in user service details after repo call");
        if (user == null) {

            return null;
        }
        System.out.println(user.getUser_id());
        System.out.println(user);System.out.println(user);
        return new org.springframework.security.core.userdetails.User(user.getUser_name(), user.getUser_password(), true,
                true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getUser_roles()));
    }

}