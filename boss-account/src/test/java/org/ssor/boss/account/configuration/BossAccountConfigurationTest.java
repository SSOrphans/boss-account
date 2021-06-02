package org.ssor.boss.account.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
class BossAccountConfigurationTest
{
  @Test
  void test_canCreateConfiguration(){
    var config = new BossAccountServiceConfiguration();
    assertNotNull(config);
  }
}
