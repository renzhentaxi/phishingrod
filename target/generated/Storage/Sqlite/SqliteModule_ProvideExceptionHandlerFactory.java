package Storage.Sqlite;

import Storage.base.Accessors.ExceptionHandler;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SqliteModule_ProvideExceptionHandlerFactory
    implements Factory<ExceptionHandler> {
  private static final SqliteModule_ProvideExceptionHandlerFactory INSTANCE =
      new SqliteModule_ProvideExceptionHandlerFactory();

  @Override
  public ExceptionHandler get() {
    return Preconditions.checkNotNull(
        SqliteModule.provideExceptionHandler(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static SqliteModule_ProvideExceptionHandlerFactory create() {
    return INSTANCE;
  }

  public static ExceptionHandler proxyProvideExceptionHandler() {
    return Preconditions.checkNotNull(
        SqliteModule.provideExceptionHandler(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
