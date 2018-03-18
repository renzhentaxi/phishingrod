package Storage.base.Mappers;

import Entities.Senders.old.SenderEntity;
import Entities.Users.UserEntity;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SenderMapper implements RowMapper<SenderEntity>
{

    @Override
    public SenderEntity map(ResultSet rs, StatementContext ctx) throws SQLException
    {

        RowMapper<UserEntity> userMapper = ctx.findRowMapperFor(UserEntity.class).orElseThrow(() -> new RuntimeException("No mapper for user"));
        UserEntity user = userMapper.map(rs, ctx);
        return new SenderEntity(user, rs.getString("password"), rs.getString("session_Type"));
    }

}
