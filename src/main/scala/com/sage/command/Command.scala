package com.sage.command

import com.sage.Validator

abstract class Command(val code:Int, val description:String, override val hotResponse:String,
                       override val coldResponse:String, validator: Validator) extends Response{
  def isValid(): Boolean ={

    code match {
      case 8 => validator.pjs = validator.socks == false && validator.footWear == false && validator.headWear == false &&
        validator.pants == false && validator.jacket == false && validator.shirt == false; validator.pjs
      case 7 => validator.pjs && validator.footWear && validator.shirt && validator.pants
      case 6 => validator.pants = (validator.footWear == false && validator.pjs); validator.pants
      case 5 => validator.jacket = this.isInstanceOf[Cold] && validator.shirt && validator.pjs; validator.jacket
      case 4 => validator.shirt = validator.pjs; validator.shirt
      case 3 => validator.socks = (this.isInstanceOf[Cold] && validator.footWear == false && validator.pjs); validator.socks
      case 2 => validator.headWear = validator.pjs;validator.headWear
      case 1 => validator.footWear = validator.pants; validator.footWear
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

  val commands = Map[Int,CommandData](1 -> CommandData(1,"Put on footwear","sandals","boots"),
    2 -> CommandData(2,"Put on headwear","sunglasses","hat"),
    3 -> CommandData(3,"Put on socks","fail","socks"),
    4 -> CommandData(4,"Put on shirt","shirt","shirt"),
    5 -> CommandData(5,"Put on jacket","fail","jacket"),
    6 -> CommandData(6,"Put on pants","shorts","pants"),
    7 -> CommandData(7,"Leave house","leaving house","leaving house"),
    8 -> CommandData(8,"Take off pajamas","Removing PJs","Removing PJs"))
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



