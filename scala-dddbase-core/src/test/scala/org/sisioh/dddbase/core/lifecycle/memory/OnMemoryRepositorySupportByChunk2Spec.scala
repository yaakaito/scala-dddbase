package org.sisioh.dddbase.core.lifecycle.memory

import org.specs2.mutable.Specification
import org.sisioh.dddbase.core.model._
import org.specs2.mock.Mockito

class OnMemoryRepositorySupportByChunk2Spec extends Specification with Mockito {

  case class IntIdentity(value: Int)
    extends AbstractOrderedIdentity[Int, IntIdentity]

  class EntityImpl(val identity: IntIdentity)
    extends Entity[IntIdentity]
    with EntityCloneable[IntIdentity, EntityImpl]
    with EntityOrdered[Int, IntIdentity, EntityImpl]

  class TestRepository
    extends GenericOnMemoryRepository[IntIdentity, EntityImpl]()
    with OnMemoryRepositorySupportByChunk[TestRepository, IntIdentity, EntityImpl]

  "The repository" should {
    "have stored entities" in {

      var repository = new TestRepository

      for (i <- 1 to 10) {
        val entity = new EntityImpl(IntIdentity(i))
        repository = repository.store(entity).get.repository
      }

      val chunk = repository.resolveChunk(0, 5).get

      chunk.index must_== 0
      chunk.entities.size must_== 5
      chunk.entities(0) must_== new EntityImpl(IntIdentity(1))
      chunk.entities(1) must_== new EntityImpl(IntIdentity(2))
      chunk.entities(2) must_== new EntityImpl(IntIdentity(3))
      chunk.entities(3) must_== new EntityImpl(IntIdentity(4))
      chunk.entities(4) must_== new EntityImpl(IntIdentity(5))
    }
  }


}
