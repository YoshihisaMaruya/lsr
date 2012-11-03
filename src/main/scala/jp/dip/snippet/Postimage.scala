package jp.dip.snippet

import _root_.scala.xml.{ NodeSeq, Text }
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.java.util.Date
import jp.dip.lib._
import Helpers._
import net.liftweb.http.{ FileParamHolder, RequestVar, S }
import net.liftweb.util.Helpers._
import net.liftweb.http.SHtml
import jp.dip.model.Image
import net.liftweb.http.jquery
import java.util.Formatter.DateTime

class Postimage {
  //フォーム用の宣言
  object img extends RequestVar[Box[FileParamHolder]](Empty)
  object lat extends RequestVar[Box[String]](Empty)
  object lng extends RequestVar[Box[String]](Empty)

  private def log(st: String) {
    println("##### " + st + "####")
  }

  /**
   * ImageTableに保存
   */
  private def add() {
     val f = this.img.is.openOr(null)
     val lat = this.lat.is.openOr(null)
     val lng = this.lng.is.openOr(null)
     f match {
      case FileParamHolder(_, mine, fname, file) => {
        log(mine)
        val image_model = Image.create
        image_model.save() //idを取得するために、一度保存
        try{
        	val file_path = image_model.saveFile(f)
        	image_model.mimeType(mine).video_file_path(file_path._1).lat(lat).lng(lng).created_datetime(new Date()).thumbnail_file_path(file_path._2).save
        	S.notice("ファイルのアップロードに成功しました : id = " + image_model.id)
        }
        catch{
          case e => {
            image_model.delete_!
            S.error(e.getMessage())
            }
        }
      } 
      case _ => S.error("ファイルを選択してください");
    }
  }

  /**
   * ファイルアップロード用フォーム
   */
  def upload(xhtml: NodeSeq): NodeSeq = {
    //Ajaxのやり方を見直す必要あり(やらなくてもいいかもね!!)
    bind("upload", xhtml,
      "file" -> SHtml.fileUpload(fh => img(Full(fh)),"id" -> "file","accept" -> "video/*","capture" -> "camera"),
      "lat" -> SHtml.text("",l => lat(Full(l)),"id" -> "lat","readonly" -> "readonly"), 
      "lng" -> SHtml.text("",l => lng(Full(l)),"id" -> "lng","readonly" -> "readonly"),
      "submit" -> SHtml.submit("アップロード", add))
  }
}