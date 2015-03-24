import scalaz._, Scalaz._

object Main {

	type WarnedT[F[_], A] = WriterT[F, Vector[String], A]
	type Warned[A] = WarnedT[Id, A]

	def mkW[A](a: A): Warned[A] = a.pure[Id].liftM[WarnedT]

	trait TYPE_CLASS[T[_]] {
		def buzz[A](a: T[A]): Int
	}
	val INSTANCE1 = new TYPE_CLASS[Warned] {
		def buzz[A](a: Warned[A]) = 5
	}
	def buzz[E[_], A](self: E[A])(implicit ev: TYPE_CLASS[E]): Int = ev.buzz(self)

	def foo: Int = buzz(mkW(5).map(a => a))(INSTANCE1)
}
