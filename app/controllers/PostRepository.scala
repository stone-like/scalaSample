package controllers

import scalikejdbc._

//ここで暗黙的にconnectionDefault作っちゃってるぽいが・・・,多分application.confのdb.defaultを暗黙的に読んでいる
object PostRepository {
  def findAll: Seq[Post] = DB readOnly { implicit session =>
    sql"SELECT id, body, date FROM posts".map { rs =>
      Post(rs.long("id"), rs.string("body"), rs.offsetDateTime("date"))
    }.list().apply()
  }

  def add(post: Post): Unit = DB localTx { implicit session =>
    sql"INSERT INTO posts (body, date) VALUES (${post.body}, ${post.date})".update().apply()
  }
}
