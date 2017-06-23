package repositories;

import models.User;

/**
 * Created by patrick on 6/22/17.
 */
public interface IUserRepository
{
    public User registerUser(User user);

    public User getUserById(long id);

    public boolean getUserbyUsername(String username);

    public User updateUserbyUsername(String firstname, String lastname,
                                     String username, String password, String rights);

    public User deleteUserbyUsername(String username);

    public boolean getPassword(String password);

}
