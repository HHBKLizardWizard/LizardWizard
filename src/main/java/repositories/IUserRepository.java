package repositories;

import models.User;

/**
 * Created by patrick on 6/22/17.
 */
public interface IUserRepository
{
    public User registerUser(String firstname, String lastname,
                             String username, String password, long rights);

    public User getUserById(long id);

    public User getUserbyUsername(String username);

    public User updateUserbyUsername(String firstname, String lastname,
                                     String username, String password, long rights);

    public User deleteUserbyUsername(String username);

}
