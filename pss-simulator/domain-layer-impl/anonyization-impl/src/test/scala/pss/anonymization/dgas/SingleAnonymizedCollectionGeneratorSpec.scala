package pss.anonymization.dgas

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FreeSpec, Matchers}
import pss.data.anonymity.SingleAnonymity
import pss.data.ooi.local.collection.SingleLocalOoiCollection
import pss.data.ooi.local.combination.SingleLocalOoiCombination

import scala.collection.JavaConverters._

@RunWith(classOf[JUnitRunner])
class SingleAnonymizedCollectionGeneratorSpec extends FreeSpec with Matchers {
  "A SingleAnonymizedCollectionGenerator should generate possible ooi collection" - {
    val ooiSet = newSet(1 to 4)

    val ooiCollection = new SingleLocalOoiCollection(ooiSet)
    val singleAnonymizedCollectionGenerator = new SingleAnonymizedCollectionGenerator(ooiCollection)

    "for ooi 1 with anonymity 3" in {
      val ooi1: Int = 1
      val anonymity = new SingleAnonymity(3)
      val possibleAnonymization = singleAnonymizedCollectionGenerator.generatePossibleAnonymization(new SingleLocalOoiCombination(ooi1), anonymity)
      val a = newCollection(Seq(1, 2, 3))
      val b = newCollection(Seq(1, 2, 4))
      val c = newCollection(Seq(1, 3, 4))
      possibleAnonymization should equal(Set(a, b, c).asJava)
    }

    "for ooi 2 with anonymity 2" in {
      val ooi2: Int = 2
      val anonymity = new SingleAnonymity(2)
      val possibleAnonymization = singleAnonymizedCollectionGenerator.generatePossibleAnonymization(new SingleLocalOoiCombination(ooi2), anonymity)
      val a = newCollection(Seq(1, 2))
      val b = newCollection(Seq(2, 3))
      val c = newCollection(Seq(2, 4))
      possibleAnonymization should equal(Set(a, b, c).asJava)
    }
  }

  def newCollection(seq: Seq[Int]) = new SingleLocalOoiCollection(newSet(seq))

  def newSet(seq: Seq[Int]) = toInteger(seq).toSet.asJava

  def toInteger(seq: Seq[Int]) = seq.map(i => new Integer(i))
}