package repositories;

import models.User;

/**
 * Created by patrick on 6/22/17.
 */
public interface IUserRepository{
    public boolean registerUser(User user);
    public boolean deleteUserbyId(Integer userId);
    public User getUserbyId(Integer userId);
    public User getUserbyUsername(String userName);
    public String getPasswordByUserId(Integer userId);
    public boolean updateUser(User user);
}
