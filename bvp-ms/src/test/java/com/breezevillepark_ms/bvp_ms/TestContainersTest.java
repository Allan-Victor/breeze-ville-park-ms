package com.breezevillepark_ms.bvp_ms;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class TestContainersTest extends AbstractTestContainersUnitTest {
    @Test
    void itShouldStartPostgresDB() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();

    }
}
