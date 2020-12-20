package co.g2academy.bootcamp.ecommerce.controller;


import co.g2academy.bootcamp.ecommerce.entity.User;
import co.g2academy.bootcamp.ecommerce.model.Register;
import co.g2academy.bootcamp.ecommerce.model.RegisterValidator;
import co.g2academy.bootcamp.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RegisterValidator registerValidator;

    @PostMapping("/register")
    public String register(@RequestBody Register newRegister) {
        if (registerValidator.validate(newRegister)) {
            User checkUser = userRepository.findByUserName(newRegister.getUserName());
            if (checkUser == null) {
                User newUser = new User();
                newUser.setUserName(newRegister.getUserName());
                newUser.setName(newRegister.getName());
                newUser.setPassword(bCryptPasswordEncoder.encode(newRegister.getPassword()));
                userRepository.save(newUser);
                return "REGISTRATION SUCCESS!";
            }
        }
        return "REGISTRATION FAILED";
    }

}
