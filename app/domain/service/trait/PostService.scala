package domain.service.`trait`

import domain.entity.Post
import domain.repository.UsesPostRepository
import domain.service.PostService.postRepository

import scala.concurrent.Future

trait PostService extends UsesPostRepository{
  import fujitask.scalikejdbc._ //runnerたちをimplicitするためにimport

  //あとでここをDIできるように変更
  //  val postRepository: PostRepository = PostRepositoryImpl

  def get(): Future[Seq[Post]] = postRepository.get().run()

  def post(post: Post): Future[Unit] = postRepository.post(post).run()
}