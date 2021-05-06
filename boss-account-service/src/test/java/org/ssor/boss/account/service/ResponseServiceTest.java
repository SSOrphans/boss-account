package org.ssor.boss.account.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ResponseServiceTest
{

  @Test
  void test_canCreateCompleteResponseService()
  {
    ResponseService rs = new ResponseService(200, "OK");
  }

  @Test
  void test_canModifyResponseService()
  {
    ResponseService rs = new ResponseService(200, "OK");
    rs.setMessage("Not Found");
    rs.setStatus(404);

    assertEquals(404, rs.getStatus());
    assertEquals("Not Found", rs.getMessage());
  }

  @Test
  void test_canEvaluateResponseService(){
    ResponseService rs1 = new ResponseService(200, "OK");
    ResponseService rs2 = new ResponseService(200, "OK");
    ResponseService rs3 = new ResponseService(404, "Not Found");

    assertEquals(rs1, rs2);
    assertNotEquals(rs2, rs3);

    assertEquals(rs1.hashCode(), rs2.hashCode());
    assertNotEquals(rs2.hashCode(), rs3.hashCode());
  }

}
