package ImageHoster.controller;

import ImageHoster.model.Image;
import ImageHoster.model.User;
import ImageHoster.model.UserProfile;
import ImageHoster.service.ImageService;
import ImageHoster.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;


    @RequestMapping("users/registration")
    public String registration(Model model) {
        User user = new User();
        UserProfile profile = new UserProfile();
        user.setProfile(profile);
        model.addAttribute("User", user);
        return "users/registration";
    }


    @RequestMapping(value = "users/registration", method = RequestMethod.POST)
    public String registerUser(User user, Model model) {
        String UserdPassword = user.getPassword();
        String passwordPattern = "((?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{3,})";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(UserdPassword);
        Boolean validationResult = matcher.matches();
        if (validationResult) {
            userService.registerUser(user);
            return "users/login";
        } else {
            user = new User();
            UserProfile profile = new UserProfile();
            user.setProfile(profile);
            model.addAttribute("User", user);
            String error = "Password must contain atleast 1 alphabet, 1 number & 1 special character";
            model.addAttribute("passwordTypeError", error);
            return "users/registration";
        }
    }


    @RequestMapping("users/login")
    public String login() {
        return "users/login";
    }

    @RequestMapping(value = "users/login", method = RequestMethod.POST)
    public String loginUser(User user, HttpSession session) {
        User existingUser = userService.login(user);
        if (existingUser != null) {
            session.setAttribute("loggeduser", existingUser);
            return "redirect:/images";
        } else {
            return "users/login";
        }
    }


    @RequestMapping(value = "users/logout", method = RequestMethod.POST)
    public String logout(Model model, HttpSession session) {
        session.invalidate();

        List<Image> images = imageService.getAllImages();
        model.addAttribute("images", images);
        return "index";
    }
}