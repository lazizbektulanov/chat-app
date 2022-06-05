package uz.task5.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import uz.task5.model.User;
import uz.task5.payload.MessageDto;
import uz.task5.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping("/login")
    public String getloginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, Model model){
        User currentUser = userService.login(user);
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("currentUser",currentUser);
        model.addAttribute("allUsers",allUsers);
        return "messages";
    }
    @ModelAttribute("subjectFormDto")
    public MessageDto registerDto() {
        return new MessageDto();
    }
}
