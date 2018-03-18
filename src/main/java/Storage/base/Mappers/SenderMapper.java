package Storage.base.Mappers;

import Entities.Senders.SenderEntity;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SenderMapper implements RowMapper<SenderEntity>
{

    @Override
    public SenderEntity map(ResultSet rs, StatementContext ctx) throws SQLException
    {
        int id = rs.getInt("id");
        String sessionTypeName = rs.getString("sessionTypeName");
        String password = rs.getString("password");
        String emailAddress = rs.getString("email_address");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String nickName = rs.getString("nick_name");
        return new SenderEntity(id, sessionTypeName, password, firstName, lastName, nickName, emailAddress);
    }

}
