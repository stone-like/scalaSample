package domain.repository

import domain.repository.scalikejdbc.PostRepositoryImpl

trait MixinPostRepository {
  val postRepository:PostRepository = PostRepositoryImpl
}
