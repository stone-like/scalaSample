package controllers

import java.time.OffsetDateTime

import javax.inject.Inject
import play.api.mvc.{Action, BaseController, ControllerComponents}
import play.api.data.Form
import play.api.data.Forms._
import play.api.i18n.Messages
import play.api.libs.json.{Json, Writes}


case class PostRequest(body:String)
class TextboardController @Inject()(val controllerComponents: ControllerComponents) extends BaseController{


  private[this] val form = Form(
    mapping(
      "post" -> text(minLength = 1, maxLength = 10)
    )(PostRequest.apply)(PostRequest.unapply))

  def get = Action { implicit request =>

    Ok(
      Json.toJson(Response(Meta(200),Some(Json.obj("posts" -> PostRepository.findAll)))))
  }

  def post = Action { implicit request =>
    form.bindFromRequest.fold(
      error => {
        val errorMessage = error.errors("post").head.message
        BadRequest(Json.toJson(Response(Meta(400, Some(errorMessage)))))
      },
      postRequest => {
        val post = Post(postRequest.body, OffsetDateTime.now)
        println(post)
        PostRepository.add(post)
        Ok(Json.toJson(Response(Meta(200))))
      }
    )
  }
}
