package com.sage.builder

import com.sage.DressStatus
import com.sage.command.{Command, FailResponse, Response}

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

class CommandBuilder {


  def validate(dressingSteps: Seq[Command]):Seq[Response] = {
    val responses = ListBuffer[Response]()
    for(d <- dressingSteps){
      if(!d.isValid()){
        responses += new FailResponse
        throw new DressingException(responses)
      }
      responses += d
    }
    if(responses(responses.size - 1).code != Command.LEAVE_HOUSE){
      responses += new FailResponse
    }
    responses
  }

  def buildReadableDressingSteps(dressingSteps: Seq[Command]) = {
    val triedString: Try[String] = Try(validate(dressingSteps).map(_.response).mkString(", "))
    triedString match {
      case Success(s) => s
      case Failure(e) => e.asInstanceOf[DressingException].responses.map(_.response).mkString(", ")
    }

  }

  def getDressed(temperature:String, commands:Int *):String ={
    if(!temperature.equalsIgnoreCase("HOT") && !temperature.equalsIgnoreCase("cold")){
      throw new DressingException
    }
    val dressStatus = new DressStatus
    val dressingSteps = commands.map(Command(_,temperature, dressStatus))
    buildReadableDressingSteps(dressingSteps)
  }

}
