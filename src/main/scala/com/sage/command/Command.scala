package com.sage.command

import com.sage.Validator

abstract class Command(val code:Int, val description:String, override val hotResponse:String,
                       override val coldResponse:String, validator: Validator) extends Response{
  import Command._
  def isValid(): Boolean ={

    code match {
      case PAJAMAS_OFF => validator.pjs = validator.socks == false && validator.footWear == false && validator.headWear == false &&
        validator.pants == false && validator.jacket == false && validator.shirt == false; validator.pjs
      case LEAVE_HOUSE => validator.pjs && validator.footWear && validator.shirt && validator.pants
      case PANTS => validator.pants = (validator.footWear == false && validator.pjs); validator.pants
      case JACKET => validator.jacket = this.isInstanceOf[Cold] && validator.shirt && validator.pjs; validator.jacket
      case SHIRT => validator.shirt = validator.pjs; validator.shirt
      case SOCKS => validator.socks = (this.isInstanceOf[Cold] && validator.footWear == false && validator.pjs); validator.socks
      case HEADWEAR => validator.headWear = validator.pjs;validator.headWear
      case FOOTWEAR => validator.footWear = validator.pants; validator.footWear
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
  def apply (command:Int, temperature:String, validator: Validator):Command = {

    commands.get(command) match{

      case Some(com) =>
        accomodateTemperature(com,temperature,validator)
      case None => throw new RuntimeException(s"Invalid command code: $command")
    }
  }
  def accomodateTemperature (c:CommandData, temperature:String, validator: Validator):Command = {
    temperature.toUpperCase match {
      case "HOT" =>  new Command(c.code,c.description,c.hotResponse,c.coldResponse,validator) with Hot
      case "COLD" => new Command(c.code,c.description,c.hotResponse,c.coldResponse,validator) with Cold
      case _ => throw new RuntimeException(s"Invalid temperature: $temperature")
    }
  }
}



