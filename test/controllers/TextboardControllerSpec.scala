package controllers




import org.scalatest.fixture
import play.api.libs.json.{JsValue, Json}
import play.api.test.WsTestClient
import scalikejdbc._
import scalikejdbc.scalatest.AutoRollback

import scala.concurrent.duration._
import scala.concurrent.Await
import scala.language.postfixOps


class TextboardControllerSpec extends fixture.FlatSpec with AutoRollback  {

//  override def db() = NamedDB("test").toDB()
//
//
//  "GET /posts" should "return []" in {db =>
//      WsTestClient.withClient { ws =>
//        val response = Await.result(ws.url(s"http://localhost:9000/posts").get(), Duration.Inf)
//        assert(response.json == Json.parse("""{"meta":{"status":200},"data":{"posts":[]}}"""))
//      }
//    }
//
//
//  "POST /posts" should
//    "投稿したものが返される" in { db =>
//      WsTestClient.withClient { ws =>
//        val body = "test post"
//        val postResponse = Await.result(ws.url(s"http://localhost:9000/posts").post(Map("post" -> Seq(body))), Duration.Inf)
//        assert(postResponse.status == 200)
//        val getResponse = Await.result(ws.url(s"http://localhost:9000/posts").get(), Duration.Inf)
//        assert(getResponse.status == 200)
//        assert((getResponse.json \ "meta" \ "status").as[Int] == 200)
//        val posts = (getResponse.json \ "data" \ "posts").as[Array[JsValue]]
//        assert(posts.length == 1)
//        assert((posts(0) \ "body").as[String] == body)
//      }
//    }



}
