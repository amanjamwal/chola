package services

import com.google.inject.Inject
import models.Tables.{Student, StudentRow}
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile
import utils.StudentMap

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by aman.jamwal on 16/07/16.
  */
class StudentService @Inject()(dbConfigProvider: DatabaseConfigProvider) extends IStudentService {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.driver.api._

  override def getStudents: Future[Seq[StudentMap]] = db.run {
    Student.result.map(res => res.map(row => StudentMap(row.id, row.name, row.email, row.company, row.position)))
  }

  override def upsertStudent(student: StudentMap): Future[Int] = db.run {
    Student.insertOrUpdate(StudentRow(student.id, student.name, student.email, student.company, student.position))
  }
}
