package services


import com.google.inject.ImplementedBy
import utils.StudentMap

import scala.concurrent.Future

/**
  * Created by aman.jamwal on 16/07/16.
  */
@ImplementedBy(classOf[StudentService])
trait IStudentService {
  def getStudents: Future[Seq[StudentMap]]

  def upsertStudent(student: StudentMap): Future[Int]
}
