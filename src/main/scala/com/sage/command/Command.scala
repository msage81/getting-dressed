package com.sage.command

import com.sage.Validator

abstract class Command(val code:Int, val description:String, override val hotResponse:String,
                       override val coldResponse:String) extends Response{
  def isValid(): Boolean ={

    code match {
      case 8 => Validator.pjs = Validator.socks == false && Validator.footWear == false && Validator.headWear == false &&
        Validator.pants == false && Validator.jacket == false && Validator.shirt == false; Validator.pjs
      case 7 => Validator.pjs && Validator.footWear && Validator.shirt && Validator.pants
      case 6 => Validator.pants = (Validator.footWear == false && Validator.pjs); Validator.pants
      case 5 => Validator.jacket = this.isInstanceOf[Cold] && Validator.shirt && Validator.pjs; Validator.jacket
      case 4 => Validator.shirt = Validator.pjs; Validator.shirt
      case 3 => Validator.socks = (this.isInstanceOf[Cold] && Validator.footWear == false && Validator.pjs); Validator.socks
      case 2 => Validator.headWear = Validator.pjs;Validator.headWear
      case 1 => Validator.footWear = Validator.pants; Validator.footWear
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
  def apply (command:Int, temperature:String):Command = {

    commands.get(command) match{

      case Some(com) =>
        accomodateTemperature(com,temperature)
      case None => throw new RuntimeException(s"Invalid command code: $command")
    }
  }
  def accomodateTemperature (c:CommandData, temperature:String):Command = {
    temperature.toUpperCase match {
      case "HOT" =>  new Command(c.code,c.description,c.hotResponse,c.coldResponse) with Hot
      case "COLD" => new Command(c.code,c.description,c.hotResponse,c.coldResponse) with Cold
      case _ => throw new RuntimeException(s"Invalid temperature: $temperature")
    }
  }
}



