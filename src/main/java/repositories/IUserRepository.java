package repositories;

import javafx.collections.ObservableList;
import models.User;

/**
 * Created by patrick on 6/22/17.
 */
public interface IUserRepository{
    public User registerUser(User user);
    public boolean deleteUserById(Integer userId);
    public User getUserById(Integer userId);
    public User getUserByUsername(String userName);
    public String getPasswordByUserId(Integer userId);
    public boolean updateUser(User user);
    public ObservableList<User> getAllUsers();
}
