package com.sage.builder

import org.scalatest.{FlatSpec, Matchers}

class CommandBuilderTest extends FlatSpec with Matchers{

  "Getting dressed to start your day" should "start with removing PJs regardless of temperature" in {
    val builder = new CommandBuilder()
    val dressed = builder.getDressed("HOT",7)
    dressed should be("fail")
    new CommandBuilder().getDressed("HOT",8) should be("Removing PJs, fail")

  }
  "HOT, 8, 6, 4, 2, 1, 7" should "return the appropriate text" in {
    val builder = new CommandBuilder()
    val dressed = builder.getDressed("HOT", 8, 6, 4, 2, 1, 7)
    dressed should be("Removing PJs, shorts, shirt, sunglasses, sandals, leaving house")
  }
  "COLD 8, 6, 3, 4, 2, 5, 1, 7" should "return the appropriate text" in {
    val builder = new CommandBuilder()
    val dressed = builder.getDressed("COLD", 8, 6, 3, 4, 2, 5, 1, 7)
    dressed should be("Removing PJs, pants, socks, shirt, hat, jacket, boots, leaving house")
  }
  "HOT 8, 6, 3, 4, 2, 5, 1, 7" should "return the appropriate text" in {
    val builder = new CommandBuilder()
    val dressed = builder.getDressed("HOT", 8, 6, 3, 4, 2, 5, 1, 7)
    dressed should be("Removing PJs, shorts, fail")
  }
  "HOT 8, 6, 3" should "return the appropriate text" in {
    val builder = new CommandBuilder()
    val dressed = builder.getDressed("HOT", 8, 6, 3)
    dressed should be("Removing PJs, shorts, fail")
  }
  "COLD 8, 6, 3, 4, 2, 5, 7" should "return the appropriate text" in {
    val builder = new CommandBuilder()
    val dressed = builder.getDressed("COLD", 8, 6, 3, 4, 2, 5, 7)
    dressed should be("Removing PJs, pants, socks, shirt, hat, jacket, fail")
  }
  "COLD 6" should "return the appropriate text" in {
    val builder = new CommandBuilder()
    val dressed = builder.getDressed("COLD",6)
    dressed should be("fail")
  }
  "Temperatures other than HOT or COLD" should "throw an DressingException" in {
    assertThrows[DressingException]{
      val dressed = new CommandBuilder().getDressed("HOT3",7)
    }
  }
}
