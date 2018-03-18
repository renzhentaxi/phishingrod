package Storage.Sqlite;

import Storage.base.Accessors.User.UserAccessor;
import Storage.base.Util.AlternativeSqlLocator;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.jdbi.v3.core.Jdbi;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SqliteModule_ProvideUserAccessorFactory implements Factory<UserAccessor> {
  private final Provider<Jdbi> jdbiProvider;

  private final Provider<AlternativeSqlLocator> locatorProvider;

  public SqliteModule_ProvideUserAccessorFactory(
      Provider<Jdbi> jdbiProvider, Provider<AlternativeSqlLocator> locatorProvider) {
    this.jdbiProvider = jdbiProvider;
    this.locatorProvider = locatorProvider;
  }

  @Override
  public UserAccessor get() {
    return Preconditions.checkNotNull(
        SqliteModule.provideUserAccessor(jdbiProvider.get(), locatorProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static SqliteModule_ProvideUserAccessorFactory create(
      Provider<Jdbi> jdbiProvider, Provider<AlternativeSqlLocator> locatorProvider) {
    return new SqliteModule_ProvideUserAccessorFactory(jdbiProvider, locatorProvider);
  }

  public static UserAccessor proxyProvideUserAccessor(Jdbi jdbi, AlternativeSqlLocator locator) {
    return Preconditions.checkNotNull(
        SqliteModule.provideUserAccessor(jdbi, locator),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
