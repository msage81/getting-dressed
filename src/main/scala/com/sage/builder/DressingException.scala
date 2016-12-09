package com.sage.builder

import com.sage.command.Response

class DressingException(message:String) extends RuntimeException(message){
  def this(){
    this("")
  }
  def this(responses:Seq[Response]){
    this("fail")
    this.responses = responses
  }
  var responses:Seq[Response] = Seq()
}
