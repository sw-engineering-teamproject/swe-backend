package swe.support;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import swe.config.DatabaseClearExtension;

@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(DatabaseClearExtension.class)
public abstract class ServiceTest {

}
