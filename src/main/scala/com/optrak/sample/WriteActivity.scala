package com.optrak.sample

//import android.util.Log
import akka.persistence.{Persistent, Processor}
import akka.actor.{Props, ActorSystem}
import android.app.Activity
import android.os.Bundle
import java.util.concurrent.{TimeUnit, ThreadPoolExecutor, LinkedBlockingQueue}
import scala.concurrent.ExecutionContext
import android.widget.EditText

object ExampleProcessor {
  def props(et: EditText): Props = Props(new ExampleProcessor(et))
}

class ExampleProcessor(et: EditText) extends Processor {
  var received: List[String] = Nil // state

  def receive = {
    case "print"                        => et.setText(s"received ${received.reverse}") //Log.d(TAG, s"received ${received.reverse}")
    case "boom"                         => throw new Exception("boom")
    case Persistent("boom", _)          => throw new Exception("boom")
    case Persistent(payload: String, _) => received = payload :: received
  }

  override def preRestart(reason: Throwable, message: Option[Any]) {
    message match {
      case Some(p: Persistent) if !recoveryRunning => deleteMessage(p.sequenceNr) // mark failing message as deleted
      case _                                       => // ignore
    }
    super.preRestart(reason, message)
  }
}

/**
 * Created by oscarvarto on 4/23/14.
 */
class WriteActivity extends Activity {

  var printEditText: EditText = _

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.simple)

    printEditText = findViewById(R.id.printEditText).asInstanceOf[EditText]

    val (corePoolSize, maximumPoolSize, keepAliveTime) = (10, 10, 100)
    val workQueue = new LinkedBlockingQueue[Runnable]
    // Execution context for futures below
    implicit val exec = ExecutionContext.fromExecutor(
      new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue)
    )

    val system = ActorSystem("example")
    val processor = system.actorOf(ExampleProcessor.props(printEditText), "DummyProcessor")

    processor ! Persistent("a")
    processor ! "print"
    processor ! "boom" // restart and recovery
    processor ! "print"
    processor ! Persistent("b")
    processor ! "print"
    processor ! Persistent("boom") // restart, recovery and deletion of message from journal
    processor ! "print"
    processor ! Persistent("c")
    processor ! "print"

    Thread.sleep(1000)
    system.shutdown()
  }

}
