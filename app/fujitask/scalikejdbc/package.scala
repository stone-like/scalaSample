package fujitask

//なぜか普通にscalikejdbcからDBSessionをimportできなかったのに、_root_とすればできた
import _root_.scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
//パッケージオブジェクトは別のところからこれをimportするのに使う
package object scalikejdbc {
  def ask: Task[Transaction,DBSession] =
    new Task[Transaction,DBSession]{
      def execute(transaction: Transaction)(implicit ec: ExecutionContext): Future[DBSession] =
        Future.successful(transaction.asInstanceOf[ScalikeJDBCTransaction].session)
    }

  implicit def readRunner[R >: ReadTransaction] : TaskRunner[R] =
    new TaskRunner[R] {
       def run[A](task: Task[R, A]): Future[A] = {
         println("Read Runner Activated")
         val session = DB.readOnlySession()
         val future = task.execute(new ScalikeJDBCReadTransaction(session))
         future.onComplete(_ => session.close())
         future
       }
    }

  implicit def readWriteRunner[R >: ReadWriteTransaction] : TaskRunner[R] =
    new TaskRunner[R] {
      def run[A](task: Task[R, A]): Future[A] = {
        println("Read Write Runner Activated")
        DB.futureLocalTx(session => task.execute(new ScalikeJDBCReadWriteTransaction(session)))
      }
    }
}
