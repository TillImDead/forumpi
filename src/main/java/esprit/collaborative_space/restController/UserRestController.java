package esprit.collaborative_space.restController;

import esprit.collaborative_space.entity.User;
import esprit.collaborative_space.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private IUserService userService;

    // http://localhost:8090/codingfactory/user
    @PostMapping("/user")
    User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/user/{idUser}")
    void deleteUser(@PathVariable("idUser") Long idUser) {
        userService.deleteUser(idUser);
    }

    @GetMapping("/user/find/{idUser}")
    User getUserById(@PathVariable Long idUser) {
        return userService.getUserById(idUser);
    }

    @PutMapping("/user/modifier")
    User modifierUser(@RequestBody User user) {
        return userService.updateuser(user);
    }


}
