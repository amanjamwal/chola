package utils

import play.api.libs.functional.syntax._
import play.api.libs.json._
import slick.jdbc.GetResult

case class StudentMap(id: Int, name: String, email: String, company: String, position: String)

object Maps {

  import Converters._

  implicit val testStudentMapWrites: Writes[StudentMap] = new Writes[StudentMap] {
    def writes(map: StudentMap) = Json.obj(
      "id" -> map.id,
      "name" -> map.name,
      "email" -> map.email,
      "company" -> map.company,
      "position" -> map.position
    )
  }

  implicit val testStudentMapReads: Reads[StudentMap] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "name").read[String] and
      (JsPath \ "email").read[String] and
      (JsPath \ "company").read[String] and
      (JsPath \ "position").read[String]
    ) (StudentMap)

  implicit val getStudentResult: GetResult[StudentMap] = GetResult(r => StudentMap(r.nextInt(), r.nextString(), r.nextString(), r.nextString(), r.nextString()))

}