package domain.service

import domain.entity.Post
import domain.repository.{MixinPostRepository, PostRepository, UsesPostRepository}
import domain.repository.scalikejdbc.PostRepositoryImpl
import domain.service.`trait`.PostService

import scala.concurrent.Future

//trait PostService extends UsesPostRepository
////errorはfuture.failedでかけばいいっぽい？

//ここのMixInを変えれば使うRepositoryが変わる
object PostService extends PostService with MixinPostRepository{

//  import fujitask.scalikejdbc._ //runnerたちをimplicitするためにimport
//
//  //あとでここをDIできるように変更
////  val postRepository: PostRepository = PostRepositoryImpl
//
//  def get(): Future[Seq[Post]] = postRepository.get().run()
//
//  def post(post: Post): Future[Unit] = postRepository.post(post).run()

  //こういうFuture[Option]とか重なったり、Repositoryが違ったりしたりするとMonadが多くなってしまうので、こういうところでEffをしたり
  //そもそもCQRSをして一回で取得するようにするんだと思う
//  def findById(postId: Int): Future[Option[Post]] = postRepository.findById(postId).run()
//
//  def update(post: Post): Future[Unit] = postRepository.update(post).run()

  //FutureとFuture[Option]なのでLiftか何かしないとForが使えない...
//  def finAndUpdate(postId: Int): Future[Unit] =
//    for {
//      post <- postRepository.findById(postId)
//      _ <- postRepository.update(post)
//    } yield ()
}
