package jp.dip.snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb.util._
import net.liftweb.common._
import net.liftweb.http._
import java.util.Date
import Helpers._
import js.JsCmds._
import js.JE.{JsRaw, JsArray}
import js.JsCmds.JsCrVar
import js.{JsObj, JE, JsCmd}
import JE._
import _root_.scala.xml.{NodeSeq, Text}
import net.liftweb.http._
import jp.dip.model.Image

class Viewimage {
  // replace the contents of the element with what map to render
  def googlemap = renderGoogleMap()
 
  // converts a the location into a JSON Object
  def makeLocation(image: Image): JsObj = {
    JsObj(("title", image.id.toString),
      ("lat", image.lat.toString),
      ("lng", image.lng.toString),
      ("thumbnail_file_path",image.thumbnail_file_path.toString),
      ("video_file_path",image.video_file_path.toString)
      )
  }
 
   // called by renderGoogleMap which passes the list of locations
   // into the javascript function as json objects
  def ajaxFunc(locobj: List[JsObj]): JsCmd = {
    JsCrVar("locations", JsObj(("loc", JsArray(locobj: _*)))) & JsRaw("drawmap(locations)").cmd
  }
  
  
  def createData(d: List[Image],locations: List[JsObj]): List[JsObj] = {
    d match {
    	case h::t =>  createData(t, makeLocation(h)::locations)
    	case Nil => locations
    }
  }
  
  //model
  def getModelData() : List[JsObj] = {
    val d = Image.findAll()
    createData(d, Nil)
  }
 
  // render the google map
  def renderGoogleMap(): NodeSeq = {
    //set header(デフォルトではxhtmlのため)
    S.setHeader("Content-Type", "text/html;charset=utf-8")
    // setup some locations to display on the map
    val locations: List[JsObj] = getModelData()
     
    // where the magic happens
    (<head>
      {Script(OnLoad(ajaxFunc(locations)))}
    </head>)
  }
}