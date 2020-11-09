package pss.anonymity.generation

import pss.data.anonymity.Anonymity
import pss.data.user.User

class FixedAnonymityGenerator(anonymity: Anonymity) extends AnonymityGenerable[Anonymity] {
  def generateAnonymity(user: User): Anonymity = anonymity
}
