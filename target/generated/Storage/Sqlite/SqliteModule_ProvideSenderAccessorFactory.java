package Storage.Sqlite;

import Storage.base.Accessors.ExceptionHandler;
import Storage.base.Accessors.Sender.SenderAccessor;
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
public final class SqliteModule_ProvideSenderAccessorFactory implements Factory<SenderAccessor> {
  private final Provider<Jdbi> jdbiProvider;

  private final Provider<AlternativeSqlLocator> locatorProvider;

  private final Provider<ExceptionHandler> exceptionHandlerProvider;

  private final Provider<UserAccessor> userAccessorProvider;

  public SqliteModule_ProvideSenderAccessorFactory(
      Provider<Jdbi> jdbiProvider,
      Provider<AlternativeSqlLocator> locatorProvider,
      Provider<ExceptionHandler> exceptionHandlerProvider,
      Provider<UserAccessor> userAccessorProvider) {
    this.jdbiProvider = jdbiProvider;
    this.locatorProvider = locatorProvider;
    this.exceptionHandlerProvider = exceptionHandlerProvider;
    this.userAccessorProvider = userAccessorProvider;
  }

  @Override
  public SenderAccessor get() {
    return Preconditions.checkNotNull(
        SqliteModule.provideSenderAccessor(
            jdbiProvider.get(),
            locatorProvider.get(),
            exceptionHandlerProvider.get(),
            userAccessorProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static SqliteModule_ProvideSenderAccessorFactory create(
      Provider<Jdbi> jdbiProvider,
      Provider<AlternativeSqlLocator> locatorProvider,
      Provider<ExceptionHandler> exceptionHandlerProvider,
      Provider<UserAccessor> userAccessorProvider) {
    return new SqliteModule_ProvideSenderAccessorFactory(
        jdbiProvider, locatorProvider, exceptionHandlerProvider, userAccessorProvider);
  }

  public static SenderAccessor proxyProvideSenderAccessor(
      Jdbi jdbi,
      AlternativeSqlLocator locator,
      ExceptionHandler exceptionHandler,
      UserAccessor userAccessor) {
    return Preconditions.checkNotNull(
        SqliteModule.provideSenderAccessor(jdbi, locator, exceptionHandler, userAccessor),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
