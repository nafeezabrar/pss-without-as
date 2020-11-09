package pss.anonymity.generation

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}
import pss.data.anonymity.{Anonymity, SingleAnonymity}
import pss.data.user.User

import scala.util.Random


@RunWith(classOf[JUnitRunner])
class FixedAnonymityGeneratorSpec extends FlatSpec with Matchers {

  "A FixedAnonymityGenerator" should "generate fixed anonymity every times" in {
    val providedAnonymity: Anonymity = new SingleAnonymity(3)
    val anonymityGenerator = new FixedAnonymityGenerator(providedAnonymity)
    val random = new Random()

    for (_ <- 1 to 10) {
      val user = new User(random.nextInt(100), random.nextString(12))
      val generatedAnonymity = anonymityGenerator.generateAnonymity(user)
      generatedAnonymity should equal(providedAnonymity)
    }
  }
}
