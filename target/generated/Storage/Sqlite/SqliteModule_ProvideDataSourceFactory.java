package Storage.Sqlite;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import javax.sql.DataSource;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SqliteModule_ProvideDataSourceFactory implements Factory<DataSource> {
  private final Provider<String> databaseUrlProvider;

  public SqliteModule_ProvideDataSourceFactory(Provider<String> databaseUrlProvider) {
    this.databaseUrlProvider = databaseUrlProvider;
  }

  @Override
  public DataSource get() {
    return Preconditions.checkNotNull(
        SqliteModule.provideDataSource(databaseUrlProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static SqliteModule_ProvideDataSourceFactory create(Provider<String> databaseUrlProvider) {
    return new SqliteModule_ProvideDataSourceFactory(databaseUrlProvider);
  }

  public static DataSource proxyProvideDataSource(String databaseUrl) {
    return Preconditions.checkNotNull(
        SqliteModule.provideDataSource(databaseUrl),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
