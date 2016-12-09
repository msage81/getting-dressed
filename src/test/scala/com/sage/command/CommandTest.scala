package com.sage.command

import org.scalatest.{FlatSpec, Matchers}

class CommandTest extends FlatSpec with Matchers{

  "Accessing the apply method with the command code" should "return the appropriate command for a hot temperature" in {
    assertCommand(1,"Put on footwear","sandals","boots","HOT", () =>"sandals")
    assertCommand(2,"Put on headwear","sunglasses","hat","HOT", () =>"sunglasses")
    assertCommand(3,"Put on socks","fail","socks","HOT", () =>"fail")
    assertCommand(4,"Put on shirt","shirt","shirt","HOT", () =>"shirt")
    assertCommand(5,"Put on jacket","fail","jacket","HOT", () =>"fail")
    assertCommand(6,"Put on pants","shorts","pants","HOT", () =>"shorts")
    assertCommand(7,"Leave house","leaving house","leaving house","HOT",() =>"leaving house")
    assertCommand(8,"Take off pajamas","Removing PJs","Removing PJs","HOT",() =>"Removing PJs")
  }
  "Accessing the apply method with the command code" should "return the appropriate command for a cold temperature" in {
    assertCommand(1,"Put on footwear","sandals","boots","COLD", () =>"boots")
    assertCommand(2,"Put on headwear","sunglasses","hat","COLD", () =>"hat")
    assertCommand(3,"Put on socks","fail","socks","COLD", () =>"socks")
    assertCommand(4,"Put on shirt","shirt","shirt","COLD", () =>"shirt")
    assertCommand(5,"Put on jacket","fail","jacket","COLD", () =>"jacket")
    assertCommand(6,"Put on pants","shorts","pants","COLD", () =>"pants")
    assertCommand(7,"Leave house","leaving house","leaving house","COLD",() =>"leaving house")
    assertCommand(8,"Take off pajamas","Removing PJs","Removing PJs","COLD",() =>"Removing PJs")
  }
  def assertCommand(code:Int, desc:String, hotResponse:String, coldResponse:String, temp:String, whichResponse:() => String): Unit ={
    val comm = Command(code,temp)
    comm.code should be(code)
    comm.description should be(desc)
    comm.hotResponse should be(hotResponse)
    comm.coldResponse should be(coldResponse)
    comm.response should be(whichResponse())
  }

}
