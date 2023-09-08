package com.redmath.studentapp.user;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    /*@Autowired
    private JdbcTemplate jdbcTemplate;*/
    private final Logger logger = LoggerFactory.getLogger(getClass());
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //@Cacheable
    public User findByUname(String uname){
        return  userRepository.findByUname(uname);
        //return jdbcTemplate.queryForObject("select * from users where uname = ?", new Object[]{uname}, User.class);
    }

    @Override
    public UserDetails loadUserByUsername(String uname) throws UsernameNotFoundException {
        User user = userRepository.findByUname(uname);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid user: " + uname);
        }
        return new org.springframework.security.core.userdetails.User(user.getUname(), user.getPassword(), true,
                true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));
    }
}


