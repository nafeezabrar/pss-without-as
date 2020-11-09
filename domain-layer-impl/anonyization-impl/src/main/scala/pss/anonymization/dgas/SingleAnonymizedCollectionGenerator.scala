package pss.anonymization.dgas

import java.util

import pss.anonymization.AnonymizedCollectionGenerable
import pss.data.anonymity.SingleAnonymity
import pss.data.ooi.local.collection.SingleLocalOoiCollection
import pss.data.ooi.local.combination.SingleLocalOoiCombination
import pss.library.combinatorics.CombinatoricsUtility.getCombinations

import scala.collection.JavaConverters._

class SingleAnonymizedCollectionGenerator(val singleLocalOoiCollection: SingleLocalOoiCollection) extends AnonymizedCollectionGenerable[SingleLocalOoiCombination, SingleAnonymity, SingleLocalOoiCollection] {

  override def generatePossibleAnonymization(localOoiCombination: SingleLocalOoiCombination, anonymity: SingleAnonymity): util.Set[SingleLocalOoiCollection] = {
    val reportedOoi = localOoiCombination.getLocalOoi

    val otherOois = singleLocalOoiCollection.getLocalOoiSet.asScala.filter(ooi => ooi != reportedOoi).asJava

    val otherAnonymizedOois = getCombinations(otherOois, anonymity.getAnonymity - 1)

    otherAnonymizedOois.forEach(oois => oois.add(reportedOoi))

    otherAnonymizedOois.asScala.map(oois => new SingleLocalOoiCollection(oois)).toSet.asJava
  }
}
