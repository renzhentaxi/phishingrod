package Storage;

import Entities.SessionTypes.ISessionType;
import Entities.SessionTypes.ISessionTypeEntity;
import Entities.SessionTypes.SessionType;
import Entities.SessionTypes.SessionTypeEntity;
import Storage.base.Accessors.Exceptions.EntityAlreadyExistException;
import Storage.base.Accessors.Exceptions.EntityDoesNotExistException;
import Storage.base.Accessors.Exceptions.EntityUpdateException;
import Storage.base.Accessors.SessionType.SessionTypeAccessor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


public class SessionTypeAccessorTest
{
    private static SessionTypeAccessor getDefaultSessionTypeAccessor()
    {
        return new SessionTypeAccessor(AccessorTestHelper.getSimpleJdbi(), AccessorTestHelper.getSimpleSqlLocator());
    }

    @Test
    void add_sessionTypeAlreadyExist_throwsEntityAlreadyExistException()
    {
        SessionTypeAccessor accessor = getDefaultSessionTypeAccessor();

        ISessionType sessionType = new SessionType("name", "host", 0, true, true);

        //pre populate database
        accessor.add(sessionType);

        //act
        Throwable throwable = catchThrowable(() -> accessor.add(sessionType));

        assertThat(throwable)
                .withFailMessage("Should have thrown an exception but didn't")
                .isNotNull()
                .withFailMessage("Should have thrown an entityAlreadyExistException but % was thrown", throwable.getClass())
                .isInstanceOf(EntityAlreadyExistException.class);
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

        assertThat(expectedException).isNotNull().isInstanceOf(EntityDoesNotExistException.class);
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
        assertThat(expectedException).isNotNull().isInstanceOf(EntityDoesNotExistException.class);

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
        assertThat(expectedException).isNotNull().isInstanceOf(EntityDoesNotExistException.class);
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
        assertThat(expectedException).isNotNull().isInstanceOf(EntityUpdateException.class);
    }

}
