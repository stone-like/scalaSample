package domain.repository

import domain.entity.Post
import fujitask.{ReadTransaction, ReadWriteTransaction, Task}

trait PostRepository {
   def get():Task[ReadTransaction,Seq[Post]]
   def post(post:Post):Task[ReadWriteTransaction,Unit]
   def findById(postId:Int):Task[ReadTransaction,Option[Post]]
   def update(post:Post):Task[ReadWriteTransaction,Unit]
}
