package io.sudostream.api_event_horizon.cli

import akka.actor.ActorSystem
import akka.event.Logging
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.ByteArrayDeserializer

/**
  * Created by andy on 08/12/16.
  */
object MyConsumer {

  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()
  val config = ConfigFactory.load()
  val logger = Logging(system, getClass)

  val consumerSettings = ConsumerSettings(system, new ByteArrayDeserializer, new GeneratedTestsEventDeserialiser)
    .withBootstrapServers("localhost:9092")
    .withGroupId("aeh-cli-listener")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")

  def main(args: Array[String]) {
    val done =
      Consumer.committableSource(consumerSettings, Subscriptions.topics("generated-test-scripts"))
        .map {
          msg =>
            println("Test Script = " + msg.record.value())
            msg
        }
        .mapAsync(1) { msg =>
          msg.committableOffset.commitScaladsl()
        }
        .runWith(Sink.ignore)

  }

}
