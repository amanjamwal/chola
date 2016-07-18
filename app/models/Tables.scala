package models

/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.driver.H2Driver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.driver.JdbcProfile

  import profile.api._

  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Student.schema

  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Student
    *
    * @param id       Database column ID SqlType(INTEGER), AutoInc, PrimaryKey
    * @param name     Database column DATE SqlType(VARCHAR)
    * @param email    Database column NAME SqlType(VARCHAR)
    * @param company  Database column DESCRIPTION SqlType(VARCHAR)
    * @param position Database column DESCRIPTION SqlType(VARCHAR) */
  case class StudentRow(id: Int, name: String, email: String, company: String, position: String)

  /** GetResult implicit for fetching StudentRow objects using plain SQL queries */
  implicit def GetResultStudentRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp], e2: GR[String], e3: GR[Option[String]]): GR[StudentRow] = GR {
    prs => import prs._
      StudentRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String]))
  }

  /** Table description of table student. Objects of this class serve as prototypes for rows in queries. */
  class Student(_tableTag: Tag) extends Table[StudentRow](_tableTag, "student") {
    def * = (id, name, email, company, position) <>(StudentRow.tupled, StudentRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(email), Rep.Some(company), Rep.Some(position)).shaped.<>({ r => import r._; _1.map(_ => StudentRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column ID SqlType(INTEGER), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column NAME SqlType(VARCHAR) */
    val name: Rep[String] = column[String]("NAME")
    /** Database column NAME SqlType(VARCHAR) */
    val email: Rep[String] = column[String]("EMAIL")
    /** Database column NAME SqlType(VARCHAR) */
    val company: Rep[String] = column[String]("COMPANY")
    /** Database column NAME SqlType(VARCHAR) */
    val position: Rep[String] = column[String]("POSITION")
  }

  /** Collection-like TableQuery object for table Student */
  lazy val Student = new TableQuery(tag => new Student(tag))
}

