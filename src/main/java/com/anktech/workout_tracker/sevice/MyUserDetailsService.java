package com.anktech.workout_tracker.sevice;

import com.anktech.workout_tracker.collection.User;
import com.anktech.workout_tracker.collection.UserDetailsReposne;
import com.anktech.workout_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

    @Override
    public UserDetailsReposne loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserDetailsReposne(user.getId(),user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
