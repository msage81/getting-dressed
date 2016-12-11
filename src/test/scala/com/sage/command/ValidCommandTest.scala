package com.sage.command

import org.scalatest.{FlatSpec, Matchers}
import Command._
import com.sage.status.DressStatus

class ValidCommandTest extends FlatSpec with Matchers{

    "Removing Pajamas" should "be the first action taken when getting dressed and can only be done once" in {

      val dressStatus = new DressStatus
      Command(PAJAMAS_OFF,"HOT",dressStatus).isValid() should be(true)
      Command(PAJAMAS_OFF,"HOT",dressStatus).isValid() should be(false)

    }



  "Pants" should "be on before footwear and can only be put on once" in {
    val dressStatus = new DressStatus(pants = true)
    Command(FOOTWEAR,"HOT",dressStatus).isValid() should be(true)
    Command(FOOTWEAR,"HOT",dressStatus).isValid() should be(false)
    Command(FOOTWEAR,"HOT",new DressStatus(pants = false)).isValid() should be(false)
  }
  "Headwear" should "be put on only after pajamas off and can only be put on once" in {
    val dressStatus = new DressStatus(pjs = true)
    Command(HEADWEAR,"HOT",dressStatus).isValid() should be(true)
    Command(HEADWEAR,"HOT",dressStatus).isValid() should be(false)
    Command(HEADWEAR,"HOT",new DressStatus(pjs = false)).isValid() should be(false)

  }
  "Socks" should "be put on before shoes and only when cold and after pjs off and can only be put on once" in {
    val dressStatus = new DressStatus(pjs = true)
    Command(SOCKS,"Cold",dressStatus).isValid() should be(true)
    Command(SOCKS,"Cold",dressStatus).isValid() should be(false)
    Command(SOCKS,"Cold",new DressStatus(pjs = false)).isValid() should be(false)
    Command(SOCKS,"Hot",new DressStatus(pjs = true)).isValid() should be(false)

  }
  "Shirt" should "be put on after removing pjs and can only be put on once" in {
    val dressStatus = new DressStatus(pjs = true)
    Command(SHIRT,"HOT",dressStatus).isValid() should be(true)
    Command(SHIRT,"HOT",dressStatus).isValid() should be(false)
    Command(SHIRT,"HOT",new DressStatus(pjs = false)).isValid() should be(false)

  }

  "Leaving the house in the heat" should "occur after all appropriate closting items have been put on and can only be put on once" in {
    val dressStatus = new DressStatus(pjs = true, shirt = true, footWear = true, pants = true, headWear = true)
    Command(LEAVE_HOUSE,"Hot",dressStatus).isValid() should be(true)
    Command(LEAVE_HOUSE,"Hot",dressStatus).isValid() should be(false)

  }
  "Leaving the house in the cold" should "occur after all appropriate closting items have been put on and can only be put on once" in {
    val dressStatus = new DressStatus(pjs = true, shirt = true, footWear = true, pants = true, headWear = true, jacket = true)
    Command(LEAVE_HOUSE,"Cold",dressStatus).isValid() should be(true)
    Command(LEAVE_HOUSE,"Cold",dressStatus).isValid() should be(false)

  }
  "Jacket" should "be put on after shirt and only when cold and can only be put on once" in {
    val dressStatus = new DressStatus(pjs = true, shirt = true)
    Command(JACKET,"Cold",dressStatus).isValid() should be(true)
    Command(JACKET,"Cold",dressStatus).isValid() should be(false)
    Command(JACKET,"Cold",new DressStatus(pjs = true, shirt = false)).isValid() should be(false)
    Command(JACKET,"Hot",new DressStatus(pjs = true, shirt = true)).isValid() should be(false)

  }

}
