package domain.entity

import java.time.OffsetDateTime

import play.api.libs.json.{JsPath, Json, Reads, Writes}

case class Post(id:Long,body:String,date:OffsetDateTime)

object Post {
  implicit val writes:Writes[Post] = Json.writes[Post]
  implicit val reads:Reads[Post] = Json.reads[Post]
  def apply(id:Long = 0,body: String, date: OffsetDateTime): Post =
    Post(id, body, date)
}

