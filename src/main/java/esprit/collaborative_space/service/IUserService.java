package esprit.collaborative_space.service;

import esprit.collaborative_space.entity.User;

import java.util.List;

public interface IUserService {


    List<User> getAllUsers();
    User getUserById(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
    User updateuser(User user);
}
