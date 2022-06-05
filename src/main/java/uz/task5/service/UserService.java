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
        User currentUser = user;
        if(!userRepository.existsUserByEmail(user.getEmail())){
            currentUser = userRepository.save(new User(user.getEmail()));
        }
        return currentUser;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
