package domain.repository.scalikejdbc

import domain.entity.Post
import domain.repository.PostRepository
import fujitask.{ReadTransaction, ReadWriteTransaction, Task}
import fujitask.scalikejdbc._
import scalikejdbc._

object PostRepositoryImpl extends PostRepository{

   def get(): Task[ReadTransaction, Seq[Post]] =
     ask.map{ implicit session =>
       sql"SELECT id, body, date FROM posts".map { rs =>
         Post(rs.long("id"), rs.string("body"), rs.offsetDateTime("date"))
       }.list().apply()
     }
   def post(post:Post): Task[ReadWriteTransaction, Unit] =
     ask.map{ implicit session =>
       println("sqlPostActivated")
       sql"INSERT INTO posts (body, date) VALUES (${post.body}, ${post.date})".update().apply()
     }
  def findById(postId:Int):Task[ReadTransaction,Option[Post]] =
    ask.map{ implicit session =>
      sql"""SELECT id, body, date FROM posts WHERE id = ${postId}""".map { rs =>
        Post(rs.long("id"), rs.string("body"), rs.offsetDateTime("date"))
      }.single.apply()
    }
  def update(post:Post):Task[ReadWriteTransaction,Unit] =
    ask.map{ implicit session =>
      val sql = sql"""update posts set body = ${post.body},date = ${post.date} where id = ${post.id}"""
      sql.update.apply()
    }
}
