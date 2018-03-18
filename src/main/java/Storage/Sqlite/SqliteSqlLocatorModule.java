package Storage.Sqlite;

import Storage.base.Util.AlternativeSqlLocator;
import Storage.base.Util.StoredSqlLocator;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class SqliteSqlLocatorModule
{
    @Provides
    @Singleton
    public static AlternativeSqlLocator provideAlternativeSqlLocator()
    {
        String rootPath = System.getProperty("user.dir") + "/src/scripts/sql";
        String parentPath = "sqlite";
        StoredSqlLocator locator = new StoredSqlLocator(rootPath, parentPath);

        locator.register("addUser", "user/addUser");
        locator.register("updateUser", "user/updateUser");
        locator.register("getUser", "user/getUser");
        locator.register("getUserByEmail", "user/getUserByEmail");
        locator.register("existUser", "user/existUser");
        locator.register("existUserByEmail", "user/existUserByEmail");



        locator.register("getSessionType", "sessionType/getSessionType");
        locator.register("addSessionType", "sessionType/addSessionType");
        locator.register("getSessionTypeByName", "sessionType/getSessionTypeByName");
        locator.register("updateSessionType", "sessionType/updateSessionType");
        locator.register("existSessionType", "sessionType/existSessionType");
        locator.register("existSessionTypeByName", "sessionType/existSessionTypeByName");

        locator.register("addSender", "sender/addSender");
        locator.register("updateSender", "sender/updateSender");
        locator.register("getSender", "sender/getSender");
        locator.register("getSenderByEmail", "sender/getSenderByEmail");
        locator.register("existSender","sender/existSender");
        locator.register("existSenderByEmail","sender/existSenderByEmail");

        locator.register("addMail", "Email/addMail");
        locator.register("getPath", "Email/getPath");
        return locator;
    }
}
