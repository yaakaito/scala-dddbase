/*
 * Copyright 2010 TRICREO, Inc. (http://tricreo.jp/)
 * Copyright 2011 Sisioh Project and others. (http://www.sisioh.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.sisioh.dddbase.core.lifecycle.memory.mutable

import org.sisioh.dddbase.core.lifecycle.AsyncRepository
import org.sisioh.dddbase.core.lifecycle.memory.{AsyncOnMemoryRepositorySupport, OnMemoryRepository}
import org.sisioh.dddbase.core.model.{Identity, EntityCloneable, Entity}

/**
 * 非同期型オンメモリ可変リポジトリのためのトレイト。
 *
 * @tparam AR 当該リポジトリを実装する派生型
 * @tparam SR 内部で利用する同期型リポジトリの型
 * @tparam ID 識別子の型
 * @tparam T エンティティの型
 */
trait AsyncOnMemoryRepository
[+AR <: AsyncRepository[_, ID, T],
SR <: OnMemoryRepository[_, ID, T],
ID <: Identity[_],
T <: Entity[ID] with EntityCloneable[ID, T] with Ordered[T]]
  extends AsyncOnMemoryRepositorySupport[AR, SR, ID, T] {

  protected def createInstance(state: (SR, Option[T])): (AR, Option[T]) =
    (this.asInstanceOf[AR], state._2)

}