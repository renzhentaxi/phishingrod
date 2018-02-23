package Storage;

import Accounts.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserAccessor implements DatabaseAccessor<User>
{

    protected abstract String getGetQueryString();

    protected abstract String getAddQueryString();

    protected abstract String getUpdateQueryString();

    private final ConnectionProvider _connectionProvider;

    public UserAccessor(ConnectionProvider connectionProvider)
    {
        _connectionProvider = connectionProvider;
    }

    @Override
    public User get(String identifier)
    {
        try (Connection connection = _connectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(getGetQueryString());
        )
        {
            statement.setString(1, identifier);
            ResultSet set = statement.executeQuery();
            String emailAddress = set.getString("email_address");
            String firstName = set.getString("first_name");
            String lastName = set.getString("last_name");
            String nick_name = set.getString("nick_name");
            return new User(emailAddress, firstName, lastName, nick_name);
        } catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        throw new RuntimeException("No user with givenName");
    }

    @Override
    public void add(User user)
    {

    }

    @Override
    public void update(User user)
    {

    }
}
