package com.sage

object Validator{
  def init(): Unit ={
    pants = false
    pjs = false
    socks = false
    jacket = false
    headWear = false
    footWear = false
    shirt = false
  }
  var pjs:Boolean = false
  var socks:Boolean= false
  var shirt:Boolean = false
  var jacket:Boolean = false
  var pants:Boolean = false
  var headWear:Boolean = false
  var footWear:Boolean = false
}
/*
•	You start in the house with your PJ’s on
•	Pajamas must be taken off before anything else can be put on
•	Only 1 piece of each type of clothing may be put on
•	You cannot put on socks when it is hot
•	You cannot put on a jacket when it is hot
•	Socks must be put on before footwear
•	Pants must be put on before footwear
•	The shirt must be put on before the headwear or jacket
•	You cannot leave the house until all items of clothing are on
(except socks and a jacket when it’s hot)
If an invalid command is issued, respond with “fail” and stop processing commands
 */
