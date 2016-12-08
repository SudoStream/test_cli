package io.sudostream.api_event_horizon.cli

import java.io.{ByteArrayInputStream, InputStream}
import java.util

import io.sudostream.api_event_horizon.messages.GeneratedTestsEvent
import org.apache.avro.io.DecoderFactory
import org.apache.avro.specific.SpecificDatumReader
import org.apache.kafka.common.serialization.Deserializer

class GeneratedTestsEventDeserialiser extends Deserializer[GeneratedTestsEvent] {
  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def deserialize(topic: String, data: Array[Byte]): GeneratedTestsEvent = {
    val reader = new SpecificDatumReader[GeneratedTestsEvent](GeneratedTestsEvent.SCHEMA$)
    val in: InputStream = new ByteArrayInputStream(data)
    val decoder: org.apache.avro.io.Decoder = new DecoderFactory().binaryDecoder(in, null)
    val outVersion = new GeneratedTestsEvent()
    reader.read(outVersion, decoder)
    outVersion
  }

  override def close(): Unit = {}
}
