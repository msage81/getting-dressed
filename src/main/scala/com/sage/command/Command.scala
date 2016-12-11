package com.sage.command

import com.sage.status.DressStatus

abstract class Command(val code:Int, val description:String, override val hotResponse:String,
                       override val coldResponse:String, dressStatus: DressStatus) extends Response{
  import Command._
  def isValid(): Boolean ={

    code match {
      case PAJAMAS_OFF => dressStatus.pjs = !dressStatus.socks  && !dressStatus.footWear  && !dressStatus.headWear  &&
        !dressStatus.pants  && !dressStatus.jacket  && !dressStatus.shirt  && !dressStatus.pjs; dressStatus.pjs
      case LEAVE_HOUSE => dressStatus.leaveHouse = dressStatus.pjs && dressStatus.footWear && dressStatus.shirt &&
        dressStatus.pants && dressStatus.headWear && !dressStatus.leaveHouse &&
        (if(this.isInstanceOf[Cold]) dressStatus.jacket else true); dressStatus.leaveHouse
      case PANTS => dressStatus.pants = !dressStatus.footWear  && dressStatus.pjs && !dressStatus.pants; dressStatus.pants
      case JACKET => dressStatus.jacket = this.isInstanceOf[Cold] && dressStatus.shirt && dressStatus.pjs && !dressStatus.jacket; dressStatus.jacket
      case SHIRT => dressStatus.shirt = dressStatus.pjs && !dressStatus.shirt; dressStatus.shirt
      case SOCKS => dressStatus.socks = (this.isInstanceOf[Cold] && !dressStatus.footWear && !dressStatus.socks && dressStatus.pjs); dressStatus.socks
      case HEADWEAR => dressStatus.headWear = dressStatus.pjs && !dressStatus.headWear;dressStatus.headWear
      case FOOTWEAR => dressStatus.footWear = dressStatus.pants && !dressStatus.footWear; dressStatus.footWear
    }

  }
}
class FailResponse extends Response{
  override val code:Int = 0
  override val hotResponse: String = ""
  override val coldResponse: String = ""
  override def response: String = "fail"
}

case class CommandData(code:Int, description:String, hotResponse:String, coldResponse:String)

object Command {
  val FOOTWEAR = 1
  val HEADWEAR = 2
  val SOCKS = 3
  val SHIRT = 4
  val JACKET = 5
  val PANTS = 6
  val LEAVE_HOUSE = 7
  val PAJAMAS_OFF = 8
  
  val commands = Map[Int,CommandData](FOOTWEAR -> CommandData(FOOTWEAR,"Put on footwear","sandals","boots"),
    HEADWEAR -> CommandData(HEADWEAR,"Put on headwear","sunglasses","hat"),
    SOCKS -> CommandData(SOCKS,"Put on socks","fail","socks"),
    SHIRT -> CommandData(SHIRT,"Put on shirt","shirt","shirt"),
    JACKET -> CommandData(JACKET,"Put on jacket","fail","jacket"),
    PANTS -> CommandData(PANTS,"Put on pants","shorts","pants"),
    LEAVE_HOUSE -> CommandData(LEAVE_HOUSE,"Leave house","leaving house","leaving house"),
    PAJAMAS_OFF -> CommandData(PAJAMAS_OFF,"Take off pajamas","Removing PJs","Removing PJs"))
  def apply (command:Int, temperature:String, validator: DressStatus):Command = {

    commands.get(command) match{

      case Some(com) =>
        accomodateTemperature(com,temperature,validator)
      case None => throw new RuntimeException(s"Invalid command code: $command")
    }
  }
  def accomodateTemperature (c:CommandData, temperature:String, validator: DressStatus):Command = {
    temperature.toUpperCase match {
      case "HOT" =>  new Command(c.code,c.description,c.hotResponse,c.coldResponse,validator) with Hot
      case "COLD" => new Command(c.code,c.description,c.hotResponse,c.coldResponse,validator) with Cold
      case _ => throw new RuntimeException(s"Invalid temperature: $temperature")
    }
  }
}



