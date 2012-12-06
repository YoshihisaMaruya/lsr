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

class Viewimagelists {
  
  
  def content( xhtml : NodeSeq) = {
     val images = Image.findAll()
     val i = new Image()
     
    def bindVideoInfo(template: NodeSeq): NodeSeq = {
    	 images.flatMap{ image => 
    	   bind("video_info", template,"id" -> image.id,"lat" -> image.lat,"lng" -> image.lng,"weather" -> image.weather,"comment" -> image.comment, "link" -> <a href={image.video_file_path}><img  width="100" height="100" src={image.thumbnail_file_path}/></a>)   
    	 }
    }
    
    bind("lists",xhtml, "videos" -> bindVideoInfo _)
  }
}