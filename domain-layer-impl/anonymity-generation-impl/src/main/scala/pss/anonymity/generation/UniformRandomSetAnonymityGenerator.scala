package pss.anonymity.generation

import pss.data.anonymity.Anonymity
import pss.data.user.User
import pss.library.MyRandom

class UniformRandomSetAnonymityGenerator(anonymities: java.util.List[Anonymity], myRandom: MyRandom) extends AnonymityGenerable[Anonymity] {
  override def generateAnonymity(user: User): Anonymity = myRandom.nextItem(anonymities)
}
