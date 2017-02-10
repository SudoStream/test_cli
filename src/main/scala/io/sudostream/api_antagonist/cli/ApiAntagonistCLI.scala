package io.sudostream.api_antagonist.cli

import java.io.File
import java.nio.file.Files

import akka.actor.ActorSystem
import akka.event.Logging
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import com.typesafe.config.ConfigFactory
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}

object ApiAntagonistCLI {

  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()
  val config = ConfigFactory.load()
  val logger = Logging(system, getClass)

  val producerSettings: ProducerSettings[Array[Byte], String] =
    ProducerSettings(system, new ByteArraySerializer, new StringSerializer).withBootstrapServers("localhost:9092")

  def main(args: Array[String]) {

    val parser = new scopt.OptionParser[Conf]("API Antagonist CLI") {
      head("AEH CLI", "1.x")

      opt[File]('a', "api-definition").required().valueName("<file>").
        action((x, c) => c.copy(apiDefinition = x)).
        text("api-definition is a required file property")

      help("help").text("prints this usage text")

      note("some notes.")

    }

    parser.parse(args, Conf()) match {
      case Some(conf) =>
        println("Api Definition is: " + conf.apiDefinition)
        val apiDefinitionAsString = new String(Files.readAllBytes(conf.apiDefinition.toPath))

        val done = Source.single(apiDefinitionAsString)
          .map { elem =>
            new ProducerRecord[Array[Byte], String]("aeh-api-definitions", elem)
          }
          .runWith(Producer.plainSink(producerSettings))

      case None =>
        println("No idea what you meant!")
    }

  }

}

case class Conf(apiDefinition: File = new File("."))
