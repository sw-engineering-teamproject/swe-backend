package swe.core.acceptacne.support;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = DEFINED_PORT)
public abstract class AcceptanceTest {

}
