package Storage.Sqlite;

import Storage.base.Accessors.ExceptionHandler;
import Storage.base.Accessors.Sender.old.SenderAccessor;
import Storage.base.Accessors.User.UserAccessor;
import Storage.base.DatabaseModule_ProvideJdbiFactory;
import Storage.base.Util.AlternativeSqlLocator;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerSqliteStorageManager implements SqliteStorageManager {
  private Provider<String> databaseUrlProvider;

  private Provider<DataSource> provideDataSourceProvider;

  private Provider<AlternativeSqlLocator> provideAlternativeSqlLocatorProvider;

  private Provider<Jdbi> provideJdbiProvider;

  private Provider<UserAccessor> provideUserAccessorProvider;

  private Provider<ExceptionHandler> provideExceptionHandlerProvider;

  private Provider<SenderAccessor> provideSenderAccessorProvider;

  private DaggerSqliteStorageManager(Builder builder) {
    initialize(builder);
  }

  public static SqliteStorageManager.Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.databaseUrlProvider = InstanceFactory.create(builder.databaseUrl);
    this.provideDataSourceProvider =
        DoubleCheck.provider(SqliteModule_ProvideDataSourceFactory.create(databaseUrlProvider));
    this.provideAlternativeSqlLocatorProvider =
        DoubleCheck.provider(SqliteSqlLocatorModule_ProvideAlternativeSqlLocatorFactory.create());
    this.provideJdbiProvider =
        DoubleCheck.provider(
            DatabaseModule_ProvideJdbiFactory.create(
                provideDataSourceProvider, provideAlternativeSqlLocatorProvider));
    this.provideUserAccessorProvider =
        DoubleCheck.provider(
            SqliteModule_ProvideUserAccessorFactory.create(
                provideJdbiProvider, provideAlternativeSqlLocatorProvider));
    this.provideExceptionHandlerProvider =
        DoubleCheck.provider(SqliteModule_ProvideExceptionHandlerFactory.create());
    this.provideSenderAccessorProvider =
        DoubleCheck.provider(
            SqliteModule_ProvideSenderAccessorFactory.create(
                provideJdbiProvider,
                provideAlternativeSqlLocatorProvider,
                provideExceptionHandlerProvider,
                provideUserAccessorProvider));
  }

  @Override
  public UserAccessor getUserAccessor() {
    return provideUserAccessorProvider.get();
  }

  @Override
  public SenderAccessor getSenderAccessor() {
    return provideSenderAccessorProvider.get();
  }

  @Override
  public Jdbi getJdbi() {
    return provideJdbiProvider.get();
  }

  private static final class Builder implements SqliteStorageManager.Builder {
    private String databaseUrl;

    @Override
    public SqliteStorageManager build() {
      if (databaseUrl == null) {
        throw new IllegalStateException(String.class.getCanonicalName() + " must be set");
      }
      return new DaggerSqliteStorageManager(this);
    }

    @Override
    public Builder databaseUrl(String url) {
      this.databaseUrl = Preconditions.checkNotNull(url);
      return this;
    }
  }
}
