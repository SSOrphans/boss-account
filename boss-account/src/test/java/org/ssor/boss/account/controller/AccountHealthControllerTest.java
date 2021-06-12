package org.ssor.boss.account.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AccountHealthControllerTest
{

  @InjectMocks
  AccountHealthController accountHealthController;

  @Test
  void test_canBeHealthy()
  {
    var response = new ResponseEntity<>("", HttpStatus.OK);
    assertEquals(response, accountHealthController.accountHealth());
  }
}
