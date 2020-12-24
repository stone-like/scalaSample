package fujitask

import scala.concurrent.{ExecutionContext, Future}

trait Task[-Resource,+A] { self =>

  def execute(resource: Resource)(implicit ec:ExecutionContext):Future[A]

  def flatMap[ExtendedResource <: Resource,B](f:A => Task[ExtendedResource,B]):Task[ExtendedResource,B] =
    new Task[ExtendedResource,B] {
      def execute(resource: ExtendedResource)(implicit ec:ExecutionContext):Future[B] =
        self.execute(resource).map(f).flatMap(_.execute(resource))
    }

  def map[B](f:A=>B): Task[Resource,B] = flatMap(a => Task(f(a)))

  def run[ExtendedResource <: Resource]()(implicit runner:TaskRunner[ExtendedResource]):Future[A]  = runner.run(this)

}

object Task{
  def apply[Resource,A](a: => A): Task[Resource,A] =
    new Task[Resource,A]{
      def execute(resource: Resource)(implicit ec:ExecutionContext):Future[A] =
        Future(a)
    }
}

trait TaskRunner[Resource]{
  def run[A](task:Task[Resource,A]):Future[A]
}
