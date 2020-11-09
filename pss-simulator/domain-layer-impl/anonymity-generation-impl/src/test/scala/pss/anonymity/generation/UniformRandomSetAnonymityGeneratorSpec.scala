package pss.anonymity.generation

import java.util

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}
import pss.data.anonymity.{Anonymity, SingleAnonymity}
import pss.data.user.User
import pss.library.MyRandom


@RunWith(classOf[JUnitRunner])
class UniformRandomSetAnonymityGeneratorSpec extends FlatSpec with Matchers {

  "A UniformDistributionRandomSetAnonymityGenerator" should "generate random anonymity from given set" in {
    val anonymity1 = new SingleAnonymity(3)
    val anonymity2 = new SingleAnonymity(5)
    val anonymity3 = new SingleAnonymity(7)
    val anonymities: util.List[Anonymity] = new util.ArrayList[Anonymity]()
    anonymities.add(anonymity1)
    anonymities.add(anonymity2)
    anonymities.add(anonymity3)

    val myRandom = new MyRandom()
    val anonymityGenerator = new UniformRandomSetAnonymityGenerator(anonymities, myRandom)

    for (_ <- 1 to 10) {
      val user = new User(myRandom.nextInt(100), myRandom.nextName(12))
      val generatedAnonymity = anonymityGenerator.generateAnonymity(user)
      anonymities should contain(generatedAnonymity)
    }
  }
}
