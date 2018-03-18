package Storage.base.Mappers;

import Entities.SessionTypes.ISessionTypeEntity;
import Entities.SessionTypes.SessionTypeEntity;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionTypeMapper implements RowMapper<SessionTypeEntity>
{

    @Override
    public SessionTypeEntity map(ResultSet rs, StatementContext ctx) throws SQLException
    {

        int id = rs.getInt("sessionId");
        String name = rs.getString("name");
        String host = rs.getString("host");
        int port = rs.getInt("port");
        boolean auth = rs.getBoolean("auth");
        boolean tls = rs.getBoolean("tls");

        return new SessionTypeEntity(id, name, host, port, auth, tls);
    }
}
