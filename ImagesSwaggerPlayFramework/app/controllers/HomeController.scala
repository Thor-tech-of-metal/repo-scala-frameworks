package controllers

import io.swagger.annotations.{Api, ApiParam, ApiResponse, ApiResponses}
import play.api.mvc.{Action, Controller}

@Api
class HomeController extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

  @ApiResponses(Array(
    new ApiResponse(code = 400, message = "Invalid name supplied"),
    new ApiResponse(code = 404, message = "Name not found")))
  def sayHello(@ApiParam(value = "Name to say hello") name: String) = Action {
    Ok(s"hello $name")
  }
}
