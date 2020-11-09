package pss.anonymity.generation

import pss.data.anonymity.Anonymity
import pss.data.user.User

trait AnonymityGenerable[TAnonymity <: Anonymity] {
  def generateAnonymity(user: User): TAnonymity
}
