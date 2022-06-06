package uz.task5.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.task5.model.User;
import uz.task5.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;



    public User login(User user) {
        User userByEmail = userRepository.findByEmail(user.getEmail());
        if(userByEmail==null){
            userByEmail = userRepository.save(new User(user.getEmail()));
        }
        return userByEmail;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
