package Storage.base.Mappers;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class SessionPropertiesMapper implements RowMapper<Properties> {

    @Override
    public Properties map(ResultSet rs, StatementContext ctx) throws SQLException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", rs.getBoolean("auth"));
        props.put("mail.smtp.starttls.enable", rs.getBoolean("tls"));
        props.put("mail.smtp.host", rs.getString("host"));
        props.put("mail.smtp.port", rs.getInt("port"));
        return props;
    }
}
