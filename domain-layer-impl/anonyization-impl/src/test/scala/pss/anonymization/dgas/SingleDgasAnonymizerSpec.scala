package pss.anonymization.dgas

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SingleDgasAnonymizerSpec extends FreeSpec with BeforeAndAfterEach with Matchers {

  //  "An Anonymizer" - {
  //    val anonymizer: SingleDgasAnonymizer = null
  //    val totalOois = 4
  //    val ooiOne = 1
  //    val ooiTwo = 2
  //    val ooiThree = 3
  //    val ooiFour = 4
  //    "should have 24 possible decodedStates" in {
  //
  //    }
  //    "when anonymizes a 3-anonymity anonymizable report" - {
  //      val anonymity = new SingleAnonymity(3)
  //      "for Ooi one (value-10) for first time" - {
  //        val reportId = 1
  //        val reportedValue = new Value(10)
  //        val reportedOoiCombination = new SingleLocalOoiCombination(ooiOne)
  //        val anonymizableReportData = new SingleIdgasAnonymizableReportData(anonymity, reportedOoiCombination)
  //        val anonymizableReport = new SingleIdgasAnonymizableReport(reportId, reportedValue, anonymizableReportData)
  //        val anonymizedReport = anonymizer.anonymize(anonymizableReport)
  //        "then anonymized report should" - {
  //          "have length 3" in {
  //            anonymizedReport.getAnonymizedLocalOoiSet should have size 3
  //          }
  //          "anonymized oois should" - {
  //            val anonymizedOois = anonymizedReport.getAnonymizedLocalOoiSet.getAnonymizedIdSet
  //            "contain actual ooi" in {
  //              anonymizedOois should contain(ooiOne)
  //            }
  //            "contain valid oois" in {
  //
  //              val validOois = List(ooiOne, ooiTwo, ooiThree, ooiFour)
  //              anonymizedOois.forEach(anonymizedOoi => validOois should contain(anonymizedOoi))
  //            }
  //          }
  //        }
  //        "for Ooi one (value-10) for second time " - {
  //          "for Ooi one (value-10) for third time " - {
  //            "for Ooi one (value-20) for first time " - {
  //              "for Ooi one (value-20) for second time " - {
  //                "for Ooi one (value-30) for first time " - {
  //                  "for Ooi one (value-40) for second time " - {
  //                    "anonymizer should have 1 possible combination" in {
  //
  //                    }
  //                  }
  //                }
  //              }
  //            }
  //          }
  //        }
  //      }
  //
  //    }
  //
  //
  //  }
}
