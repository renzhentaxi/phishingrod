package Storage.base;

import Storage.base.Util.AlternativeSqlLocator;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DatabaseModule_ProvideJdbiFactory implements Factory<Jdbi> {
  private final Provider<DataSource> dataSourceProvider;

  private final Provider<AlternativeSqlLocator> sqlLocatorProvider;

  public DatabaseModule_ProvideJdbiFactory(
      Provider<DataSource> dataSourceProvider, Provider<AlternativeSqlLocator> sqlLocatorProvider) {
    this.dataSourceProvider = dataSourceProvider;
    this.sqlLocatorProvider = sqlLocatorProvider;
  }

  @Override
  public Jdbi get() {
    return Preconditions.checkNotNull(
        DatabaseModule.provideJdbi(dataSourceProvider.get(), sqlLocatorProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static DatabaseModule_ProvideJdbiFactory create(
      Provider<DataSource> dataSourceProvider, Provider<AlternativeSqlLocator> sqlLocatorProvider) {
    return new DatabaseModule_ProvideJdbiFactory(dataSourceProvider, sqlLocatorProvider);
  }

  public static Jdbi proxyProvideJdbi(DataSource dataSource, AlternativeSqlLocator sqlLocator) {
    return Preconditions.checkNotNull(
        DatabaseModule.provideJdbi(dataSource, sqlLocator),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
