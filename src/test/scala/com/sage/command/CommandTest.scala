package com.sage.command

import com.sage.Validator
import org.scalatest.{FlatSpec, Matchers}
import com.sage.command.Command._
class CommandTest extends FlatSpec with Matchers{

  "Accessing the apply method with the command code" should "return the appropriate command for a hot temperature" in {
    assertCommand(FOOTWEAR,"Put on footwear","sandals","boots","HOT", () =>"sandals")
    assertCommand(HEADWEAR,"Put on headwear","sunglasses","hat","HOT", () =>"sunglasses")
    assertCommand(SOCKS,"Put on socks","fail","socks","HOT", () =>"fail")
    assertCommand(SHIRT,"Put on shirt","shirt","shirt","HOT", () =>"shirt")
    assertCommand(JACKET,"Put on jacket","fail","jacket","HOT", () =>"fail")
    assertCommand(PANTS,"Put on pants","shorts","pants","HOT", () =>"shorts")
    assertCommand(LEAVE_HOUSE,"Leave house","leaving house","leaving house","HOT",() =>"leaving house")
    assertCommand(PAJAMAS_OFF,"Take off pajamas","Removing PJs","Removing PJs","HOT",() =>"Removing PJs")
  }
  "Accessing the apply method with the command code" should "return the appropriate command for a cold temperature" in {
    assertCommand(FOOTWEAR,"Put on footwear","sandals","boots","COLD", () =>"boots")
    assertCommand(HEADWEAR,"Put on headwear","sunglasses","hat","COLD", () =>"hat")
    assertCommand(SOCKS,"Put on socks","fail","socks","COLD", () =>"socks")
    assertCommand(SHIRT,"Put on shirt","shirt","shirt","COLD", () =>"shirt")
    assertCommand(JACKET,"Put on jacket","fail","jacket","COLD", () =>"jacket")
    assertCommand(PANTS,"Put on pants","shorts","pants","COLD", () =>"pants")
    assertCommand(LEAVE_HOUSE,"Leave house","leaving house","leaving house","COLD",() =>"leaving house")
    assertCommand(PAJAMAS_OFF,"Take off pajamas","Removing PJs","Removing PJs","COLD",() =>"Removing PJs")
  }
  def assertCommand(code:Int, desc:String, hotResponse:String, coldResponse:String, temp:String, whichResponse:() => String): Unit ={
    val comm = Command(code,temp, new Validator(false,false,false,false,false,false,false))
    comm.code should be(code)
    comm.description should be(desc)
    comm.hotResponse should be(hotResponse)
    comm.coldResponse should be(coldResponse)
    comm.response should be(whichResponse())
  }

}
