package Storage.base.Mappers;

import Entities.Sender;
import Entities.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SenderMapper implements RowMapper<Sender>
{

    @Override
    public Sender map(ResultSet rs, StatementContext ctx) throws SQLException
    {

        RowMapper<User> userMapper = ctx.findRowMapperFor(User.class).orElseThrow(() -> new RuntimeException("No mapper for user"));
        User user = userMapper.map(rs, ctx);
        return new Sender(user, rs.getString("password"), rs.getString("session_Type"));
    }

}
