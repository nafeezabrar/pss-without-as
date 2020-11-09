import java.util

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}
import pss.data.combination_table.{DecodedState, NotAllowedMappings, ValueKey}
import pss.data.ooi.local.combination.SingleLocalOoiCombination

@RunWith(classOf[JUnitRunner])
class DecodedStateSpec extends FlatSpec with Matchers {

  private val valueKeyOne: ValueKey = new ValueKey(1)
  private val valueKeyTwo: ValueKey = new ValueKey(2)
  "A DecodedSpec" should "align with appropriate tables for false" in {
    val stateMap: util.Map[ValueKey, SingleLocalOoiCombination] = new util.HashMap[ValueKey, SingleLocalOoiCombination]()
    val valueKey: ValueKey = valueKeyOne
    stateMap.put(valueKeyOne, new SingleLocalOoiCombination(1))
    stateMap.put(valueKeyTwo, new SingleLocalOoiCombination(2))
    val decodedState = new DecodedState(stateMap)
    val constraintMap: util.Map[ValueKey, SingleLocalOoiCombination] = new util.HashMap[ValueKey, SingleLocalOoiCombination]()
    constraintMap.put(valueKeyOne, new SingleLocalOoiCombination(1))
    constraintMap.put(valueKeyTwo, new SingleLocalOoiCombination(2))
    val decodingConstraint = new NotAllowedMappings(constraintMap)
    decodedState.isSatisfied(decodingConstraint) should be(true)
  }

  "A DecodedSpec" should "align with appropriate tables for true" in {
    val stateMap: util.Map[ValueKey, SingleLocalOoiCombination] = new util.HashMap[ValueKey, SingleLocalOoiCombination]()
    stateMap.put(valueKeyOne, new SingleLocalOoiCombination(1))
    stateMap.put(valueKeyTwo, new SingleLocalOoiCombination(2))
    val decodedState = new DecodedState(stateMap)
    val constraintMap: util.Map[ValueKey, SingleLocalOoiCombination] = new util.HashMap[ValueKey, SingleLocalOoiCombination]()
    constraintMap.put(valueKeyOne, new SingleLocalOoiCombination(1))
    constraintMap.put(valueKeyTwo, new SingleLocalOoiCombination(2))
    val decodingConstraint = new NotAllowedMappings(constraintMap)
    decodedState.isSatisfied(decodingConstraint) should be(true)
  }

  it should "align with appropriate tables with matcher" in {
    matcher(List((1, None), (2, None)), Map(1 -> 1, 2 -> 2)) should be(false)
    matcher(List((1, Some(10)), (2, None)), Map(1 -> 1, 2 -> 2)) should be(false)
    matcher(List((1, Some(10)), (2, Some(20))), Map(1 -> 1, 2 -> 2)) should be(false)
    //    matcher(List((1, Some(20)), (2, None)), Map(10 -> 1, 20 -> 2)) should be(false)
    //    matcher(List((1, Some(12)), (2, None)), Map(10 -> 1, 20 -> 2)) should be(false)
    //    matcher(List((1, Some(10)), (2, Some(12))), Map(10 -> 1, 20 -> 2)) should be(false)
  }

  def matcher(states: List[(Int, Option[Int])], constraints: Map[Int, Int]): Boolean = {
    def newDecodatedState(): DecodedState[SingleLocalOoiCombination] = {
      val map = new util.HashMap[ValueKey, SingleLocalOoiCombination]()
      for (((ooiId, value), index) <- states.zipWithIndex) {
        val valueKey: ValueKey = newValueKey(index, value)
        map.put(valueKey, new SingleLocalOoiCombination(ooiId))
      }
      new DecodedState(map)
    }

    def newValueKey(id: Int, value: Option[Int]): ValueKey = {
      val valueKey = new ValueKey(id)
      valueKey
    }

    def newDecodingConstraint(): NotAllowedMappings[SingleLocalOoiCombination] = {
      val map = new util.HashMap[ValueKey, SingleLocalOoiCombination]()
      for ((ooiId, valueKey) <- constraints) {
        map.put(new ValueKey(valueKey), new SingleLocalOoiCombination(ooiId))
      }

      new NotAllowedMappings(map)
    }

    newDecodatedState().isSatisfied(newDecodingConstraint())
  }
}
