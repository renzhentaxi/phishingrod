package Storage.Sqlite;

import Storage.base.Util.AlternativeSqlLocator;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.processing.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SqliteSqlLocatorModule_ProvideAlternativeSqlLocatorFactory
    implements Factory<AlternativeSqlLocator> {
  private static final SqliteSqlLocatorModule_ProvideAlternativeSqlLocatorFactory INSTANCE =
      new SqliteSqlLocatorModule_ProvideAlternativeSqlLocatorFactory();

  @Override
  public AlternativeSqlLocator get() {
    return Preconditions.checkNotNull(
        SqliteSqlLocatorModule.provideAlternativeSqlLocator(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static SqliteSqlLocatorModule_ProvideAlternativeSqlLocatorFactory create() {
    return INSTANCE;
  }

  public static AlternativeSqlLocator proxyProvideAlternativeSqlLocator() {
    return Preconditions.checkNotNull(
        SqliteSqlLocatorModule.provideAlternativeSqlLocator(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
