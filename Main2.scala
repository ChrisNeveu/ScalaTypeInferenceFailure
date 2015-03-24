import scalaz._, Scalaz._
import scalaz.syntax.ToMonadOps
import scalaz.std.AllInstances

object Main2 {

	type Erroring[A] = String \/ A
	type WarnedT[F[_], A] = WriterT[F, Vector[String], A]
	type Accumulator[A] = WarnedT[Erroring, A]

	case class Bar[B[+_]: Monad, +C](bc: B[C]) {
		def map[D](f: C => D): Bar[B, D] = Bar(bc.map(f))
	}

	type Baz[+A] = Bar[Option, A]

	def mkBaz[A](a: A): Baz[A] = Bar(Option(a))

	def mkAcc[A](a: A): Accumulator[A] = a.pure[Erroring].liftM[WarnedT]

	trait TYPE_CLASS[T[_]] {
		def buzz[A](a: T[A]): Int
	}
	implicit val INSTANCE1 = new TYPE_CLASS[Accumulator] {
		def buzz[A](a: Accumulator[A]) = 5
	}
	implicit val INSTANCE2 = new TYPE_CLASS[Baz] {
		def buzz[A](a: Baz[A]) = 5
	}
	implicit val INSTANCE3 = new TYPE_CLASS[Option] {
		def buzz[A](a: Option[A]) = 5
	}
	implicit final class OPS_CLASS[E[_], A](self: E[A])(implicit ev: TYPE_CLASS[E]) {
		def buzz = ev.buzz(self)
	}

	def foo: Int = OPS_CLASS(mkBaz(5).map(a => a): Baz[Int]).buzz
}
