package service

import java.time.OffsetDateTime

import domain.entity.Post
import domain.repository.{PostRepository, UsesPostRepository}
import domain.service.PostService
import domain.service.`trait`.PostService
import fujitask.{ReadTransaction, ReadWriteTransaction, Task}
import org.scalatest.wordspec.AnyWordSpecLike
import service.PostServiceSpec.FakePostService

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class PostServiceSpec extends AnyWordSpecLike{
   "get" should{
    "return 1 post" in {
      val postList = Await.result( FakePostService.get(), 1 seconds)
      println(postList)
    }
  }
}

object PostServiceSpec{
  import fujitask.{ReadTransaction, ReadWriteTransaction, Task}
  import fujitask.scalikejdbc._

  object FakePostRepository extends PostRepository{
    //そもそもTaskを返さずにそのままSeq[Post]を返せるようにすればよい,
    //そうしないと結局Taskが起動してsessionがないみたいなこと言われる、そもそもTaskをとおさなければいい
    //そのためには結局F[_}みたいにするしかない？,つまりminimalCakeとTaglessFreeFinalを合わせて、型を変更できるようにする
    val postList = Seq(Post("dummy1",OffsetDateTime.now()))
    override def get(): Task[ReadTransaction, Seq[Post]] =
      ask.map{
        (dummy) => postList
      }

    def addToList(post:Post):Unit = { postList :+ post }

    override def post(post: Post): Task[ReadWriteTransaction, Unit] =
      ask.map{
        (dummy) =>
            addToList(post)
      }
  }

  trait FakeMixinPostRepository extends UsesPostRepository{
    override val postRepository: PostRepository = FakePostRepository
  }

  object FakePostService extends PostService with FakeMixinPostRepository
}
