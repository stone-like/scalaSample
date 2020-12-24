package controllers

import java.time.OffsetDateTime

import domain.service.PostService
import javax.inject.Inject
import play.api.mvc.{Action, BaseController, ControllerComponents}
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.Messages
import play.api.libs.json.{Json, Writes}

import domain.entity.Post

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps


//UseCaseも後でDIできるようにする
//ここのリクエストでpostIdが取れるようにする
case class PostRequest(body: String)

case class UpdateRequest(postId: Long, body: String)

class TextboardController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  val postService = PostService

  private[this] val postForm = Form(
    mapping(
      "post" -> text(minLength = 1, maxLength = 10)
    )(PostRequest.apply)(PostRequest.unapply))


  def get = Action { implicit request =>

    val postList = Await.result(postService.get(), 1 seconds)
    Ok(
      Json.toJson(Response(Meta(200), Some(Json.obj("posts" -> postList)))))
  }

  def post = Action { implicit request =>
    postForm.bindFromRequest.fold(
      error => {
        val errorMessage = error.errors("post").head.message
        BadRequest(Json.toJson(Response(Meta(400, Some(errorMessage)))))
      },
      postRequest => {
        val post = Post(postRequest.body,OffsetDateTime.now)
        PostService.post(post)
        Ok(Json.toJson(Response(Meta(200))))
      }
    )
  }

//  def update = Action { implicit request =>
//
//    val post = Json.parse(request.body.toString)
//    println(post)
////    PostService.update(post)
//    Ok(Json.toJson(Response(Meta(200))))
//  }
}
