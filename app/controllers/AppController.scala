package controllers

import play.api.mvc._

/**
  * Created by aman.jamwal on 16/07/16.
  */
class AppController extends Controller {

  def index = Action(Ok(views.html.main.render()))

  def naya = Action(Ok(views.html.naya.render()))

  def mock = Action(Ok(views.html.mock.render()))

  def angular(any: Any) = index

}