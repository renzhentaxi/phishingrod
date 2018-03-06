package Storage.base.Mappers;

import Entities.Users.UserEntity;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper implements RowMapper<UserEntity>
{

    @Override
    public UserEntity map(ResultSet rs, StatementContext ctx) throws SQLException
    {
        return new UserEntity(
                rs.getInt("userId"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("nick_name"),
                rs.getString("email_address"));
    }
}
