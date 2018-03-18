package Storage;

import Entities.SessionTypes.ISessionType;
import Entities.SessionTypes.ISessionTypeEntity;
import Entities.SessionTypes.SessionType;
import Entities.SessionTypes.SessionTypeEntity;
import Storage.base.Accessors.Exceptions.EntityAlreadyExistException;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.Exceptions.EntityUpdateException;
import Storage.base.Accessors.SessionType.SessionTypeAccessor;
import Storage.base.Mappers.SessionTypeMapper;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


public class SessionTypeAccessorTest
{
    // setups
    private static Jdbi jdbi;

    private static Jdbi getJdbi()
    {
        if (jdbi == null)
        {
            jdbi = AccessorTestHelper.newJdbi(new SessionTypeMapper());
        }
        return jdbi;
    }

    private static SessionTypeAccessor getDefaultSessionTypeAccessor()
    {
        return DefaultAccessors.getDefaultSessionTypeAccessor(getJdbi());
    }

    @BeforeEach
    void setup()
    {
        AccessorTestHelper.setupDatabase(getJdbi());
    }
    @AfterEach
    void tearDown()
    {
        AccessorTestHelper.clearDatabase(getJdbi());
    }

    //tests
    @Test
    void add_sessionTypeAlreadyExist_throwsEntityAlreadyExistException()
    {
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();

        ISessionType sessionType = new SessionType("name", "host", 0, true, true);

        //pre populate database
        accessor.add(sessionType);

        //act
        Throwable throwable = catchThrowable(() -> accessor.add(sessionType));

        assertThat(throwable).isInstanceOf(EntityAlreadyExistException.class);
    }

    @Test
    void get_sessionTypeExist_returnSessionType()
    {
        //arrange
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();
        ISessionType sessionType = new SessionType("name", "host", 0, true, true);
        ISessionTypeEntity expectedEntity = accessor.add(sessionType);

        //act
        ISessionTypeEntity actualEntity = accessor.get(expectedEntity.getId());


        //assert
        assertThat(actualEntity).isEqualTo(expectedEntity);
    }

    @Test
    void get_sessionTypeDoesNotExist_throwsEntityDoesNotExistException()
    {
        //arrange
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();

        //act
        Throwable expectedException = catchThrowable(() -> accessor.get(0));
        //assert

        assertThat(expectedException).isInstanceOf(EntityDoesNotExistException.class);
    }

    @Test
    void getByName_sessionTypeExist_returnsSessionType()
    {
        //arrange
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();
        ISessionType sessionType = new SessionType("name", "host", 0, true, true);
        ISessionTypeEntity expectedEntity = accessor.add(sessionType);

        //act
        ISessionTypeEntity actualEntity = accessor.getByName(expectedEntity.getName());

        //assert
        assertThat(actualEntity).isEqualTo(expectedEntity);
    }

    @Test
    void getByName_sessionTypeDoesNotExist_throwsEntityDoesNotExistException()
    {
        //arrange
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();

        //act
        Throwable expectedException = catchThrowable(() -> accessor.getByName("blah"));

        //assert
        assertThat(expectedException).isInstanceOf(EntityDoesNotExistException.class);

    }

    @Test
    void update_sessionTypeDoesNotExist_throwsEntityDoesNotExistException()
    {
        //arrange
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();

        ISessionTypeEntity imaginarySessionType = new SessionTypeEntity(0, "fake", "fake", 0, false, false);
        //act
        Throwable expectedException = catchThrowable(() -> accessor.update(imaginarySessionType));

        //assert
        assertThat(expectedException).isInstanceOf(EntityDoesNotExistException.class);
    }

    @Test
    void update_sessionTypeExist_updatesSessionType()
    {
        //arrange
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();
        ISessionType sessionType = new SessionType("name", "host", 0, true, true);
        ISessionTypeEntity expectedEntity = accessor.add(sessionType);

        //update
        expectedEntity.setTls(false);
        expectedEntity.setHost("blah");
        expectedEntity.setName("newName");


        accessor.update(expectedEntity);

        ISessionTypeEntity actualEntityUpdated = accessor.get(expectedEntity.getId());

        //assert
        assertThat(actualEntityUpdated).isEqualTo(expectedEntity);
    }

    @Test
    void update_anotherSessionTypeWithSameNewNameExist_throwsEntityUpdateException()
    {
        //arrange
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();
        ISessionType sessionType = new SessionType("name", "host", 0, true, true);
        ISessionType sessionType2 = new SessionType("name2", "host", 0, true, true);
        ISessionTypeEntity sessionTypeEntity = accessor.add(sessionType);
        ISessionTypeEntity sessionTypeEntity2 = accessor.add(sessionType2);

        //act
        sessionTypeEntity.setName(sessionTypeEntity2.getName());
        Throwable expectedException = catchThrowable(() -> accessor.update(sessionTypeEntity));

        //assert
        assertThat(expectedException).isInstanceOf(EntityUpdateException.class);
    }

    @Test
    void exist_sessionTypeDoesNotExist_returnsFalse()
    {
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();

        boolean exist = accessor.exist(1);

        assertThat(exist).isEqualTo(false);
    }

    @Test
    void exist_sessionTypeExist_returnsTrue()
    {
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();
        ISessionType sessionType = new SessionType("name", "host", 0, true, true);
        ISessionTypeEntity sessionTypeEntity = accessor.add(sessionType);

        boolean exist = accessor.exist(sessionTypeEntity.getId());

        assertThat(exist).isEqualTo(true);
    }

    @Test
    void existByName_sessionTypeDoesNotExist_returnsFalse()
    {
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();

        boolean exist = accessor.exist("name");

        assertThat(exist).isEqualTo(false);
    }

    @Test
    void existByName_sessionTypeExist_returnsTrue()
    {
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();

        ISessionType sessionType = new SessionType("name", "host", 0, true, true);
        ISessionTypeEntity sessionTypeEntity = accessor.add(sessionType);

        boolean exist = accessor.exist(sessionTypeEntity.getName());

        assertThat(exist).isEqualTo(true);
    }
}
