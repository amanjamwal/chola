package controllers

import com.google.inject.{Inject, Singleton}
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, BodyParsers, Controller}
import services.IStudentService
import utils.Maps._
import utils.StudentMap

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by aman.jamwal on 15/07/16.
  */
@Singleton
class SchoolController @Inject()(studentService: IStudentService) extends Controller {

  def getStudents = Action.async {
    studentService.getStudents.map(data => Ok(Json.toJson(data)))
  }

  def upsertStudent = Action.async(BodyParsers.parse.json) { request =>
    val json = request.body.validate[StudentMap]
    json match {
      case JsError(e) => Future(BadRequest(Json.obj("status" -> "error", "message" -> JsError.toJson(e))))
      case JsSuccess(student, _) =>
        studentService.upsertStudent(student).map { count =>
          if (count == 1) Ok(Json.obj("status" -> "success", "message" -> "student saved"))
          else BadRequest(Json.obj("status" -> "error", "message" -> "unknown error occurred"))
        }
    }
  }

}