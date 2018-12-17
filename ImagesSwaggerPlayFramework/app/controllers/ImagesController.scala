package controllers

import java.util.UUID

import com.google.inject.Inject
import controllers.validations.Validations
import io.swagger.annotations._
import models.dtos.{Image, ImageResult}
import models.entities.ImageEntity
import play.api.libs.json.{JsSuccess, Json}
import play.api.mvc.{Action, Controller}
import service.ImagesService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Api
class ImagesController @Inject()(imagesService: ImagesService ) extends Controller {

  @ApiOperation(
    nickname = "Get a image by id.", value = "Get image", notes = "get a image by id.", httpMethod = "GET",
    response = classOf[ImageEntity],
    responseContainer = "List",
    authorizations = Array(new Authorization(value = "apiKey"))
  )
  @ApiResponses( Array( new ApiResponse(code = 400, message = "Invalid ID Supplied") ) )
  def getImage(
                @ApiParam(value = "Image ID", example = "baa26a8f-b71e-4661-a373-c60ef5ebfcfd", required = true) imageID:String
              ) = Action.async {   implicit request =>

    Validations.validateUUID(imageID) match {

      case Some(uuid) => {
        imagesService.cassandra.imagesRepositoryImpl.find(uuid) map { element =>
          Ok(Json.toJson(element))
        }
      }
      case _ => Future successful BadRequest("Invalid ID supplied")
    }
  }


  @ApiOperation(
    nickname = "Get all Images records.", value = "Get all Images", notes = "get all Images records.", httpMethod = "GET",
    response = classOf[ImageEntity],
    responseContainer = "List",
    authorizations = Array(new Authorization(value = "apiKey"))
  )
  def getImages( @ApiParam(value = "Pagination", example = "3", required = true) pages:String) = Action.async {   implicit request =>

    imagesService.cassandra.imagesRepositoryImpl.findAll(Some(pages)).map(imageResult => Ok(Json.toJson( ImageResult(imageResult.result, imageResult.next))))
  }

  @ApiOperation( nickname = "Add image",  value = "Add image", notes = "Upload a image.", httpMethod = "POST",  response = classOf[ImageEntity], authorizations = Array( new Authorization( value = "apiKey" ) ))
  @ApiResponses(Array(new ApiResponse(code = 400, message = "Invalid ID supplied"), new ApiResponse(code = 404, message = "ImageDescription not found")))
  @ApiImplicitParams(
    Array(new ApiImplicitParam(name = "body", dataType = "models.dtos.Image", required = true, paramType = "body", value = "ImageDescription object to be created with ID as JSON"))
  )
  def addImage( ) = Action.async(parse.json) { implicit request =>

    request.body.validate[Image] match {

      case JsSuccess(image, path) =>  {

        val imageToBePersisted = ImageEntity (UUID.randomUUID(),image.name,image.path,image.description)
        imagesService.cassandra.imagesRepositoryImpl.create(imageToBePersisted) map { element =>
          Ok(Json.toJson(element))
        }
      }
      case _ =>  Future successful BadRequest("The Image you tried to create was malformed")
    }
  }



  @ApiOperation( nickname = "Delete image",  value = "Delete image", notes = "Delete a image.", httpMethod = "DELETE")
  @ApiResponses(Array(new ApiResponse(code = 400, message = "Invalid ID supplied"), new ApiResponse(code = 404, message = "ImageDescription not found")))
  def deleteImage(
                   @ApiParam(value = "Image ID", example = "baa26a8f-b71e-4661-a373-c60ef5ebfcfd", required = true) imageID:String
                 ) = Action.async { implicit request =>

    Validations.validateUUID(imageID) match {

      case Some(uuid) => {
        imagesService.cassandra.imagesRepositoryImpl.deleteById(uuid) map { element =>
          Ok(Json.toJson(element))
        }
      }
      case _ => Future successful BadRequest("Invalid ID supplied")
    }
  }

  @ApiOperation( nickname = "Update image",  value = "Update image", notes = "Upload a image.", httpMethod = "PUT",  response = classOf[ImageEntity], authorizations = Array( new Authorization( value = "apiKey" ) ))
  @ApiResponses(Array(new ApiResponse(code = 400, message = "Invalid ID supplied"), new ApiResponse(code = 404, message = "ImageDescription not found")))
  @ApiImplicitParams(
    Array(new ApiImplicitParam(name = "body", dataType = "models.dtos.Image", required = true, paramType = "body", value = "ImageDescription object to be created with ID as JSON"))
  )
  def updateImage(
                   @ApiParam(value = "Image ID", example = "baa26a8f-b71e-4661-a373-c60ef5ebfcfd", required = true) imageID:String
                 ) = Action.async(parse.json) { implicit request =>


    request.body.validate[Image] match {

      case JsSuccess(image, path) =>  {

        Validations.validateUUID(imageID) match {

          case Some(uuid) => {

            val imageToBePersisted = ImageEntity (uuid,image.name,image.path,image.description)
            imagesService.cassandra.imagesRepositoryImpl.updateById(imageToBePersisted) map { element =>
              Ok(Json.toJson(imageToBePersisted))
            }
          }
          case _ => Future successful BadRequest("Invalid ID supplied")
        }
      }
      case _ =>  Future successful BadRequest("The Image you tried to create was malformed")
    }
  }

}
