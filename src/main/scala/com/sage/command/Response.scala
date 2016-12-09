package com.sage.command

trait Response {
  val code:Int
  val hotResponse:String
  val coldResponse:String
  def response:String
}
