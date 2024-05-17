package swe.config;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseClearExtension implements BeforeEachCallback {

  @Override
  public void beforeEach(final ExtensionContext context) throws Exception {
    final DatabaseCleaner databaseCleaner = getDataCleaner(context);
    databaseCleaner.clear();
  }

  private DatabaseCleaner getDataCleaner(final ExtensionContext extensionContext) {
    return SpringExtension.getApplicationContext(extensionContext)
        .getBean(DatabaseCleaner.class);
  }
}
