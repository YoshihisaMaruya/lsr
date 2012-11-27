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
import jp.dip.model.Voice
import net.liftweb.http.jquery
import java.util.Formatter.DateTime

class Postvoice {
//フォーム用の宣言
  object voice extends RequestVar[Box[FileParamHolder]](Empty)
  object lat extends RequestVar[Box[String]](Empty)
  object lng extends RequestVar[Box[String]](Empty)

  private def log(st: String) {
    println("##### " + st + "####")
  }

  /**
   * ImageTableに保存
   */
  private def add() {
     val v = this.voice.is.openOr(null)
     val lat = this.lat.is.openOr(null)
     val lng = this.lng.is.openOr(null)
     v match {
      case FileParamHolder(_, mine, fname, file) => {
    	  val voice_model = Voice.create
    	  voice_model.save()
    	  try{
    	    val file_path = voice_model.saveFile(v)
    	    voice_model.mimeType(v.mimeType).voice_file_path(file_path).lat(lat).lng(lng).created_datetime(new Date()).save
        	S.notice("ファイルのアップロードに成功しました : id = " + voice_model.id)
    	  }
    	  catch{
    	  case e => {
    	    voice_model.delete_!
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
      "file" -> SHtml.fileUpload(fh => voice(Full(fh)),"id" -> "file","accept" -> "sound/*","capture" -> "camera"),
      "lat" -> SHtml.text("",l => lat(Full(l)),"id" -> "lat","readonly" -> "readonly"), 
      "lng" -> SHtml.text("",l => lng(Full(l)),"id" -> "lng","readonly" -> "readonly"),
      "submit" -> SHtml.submit("アップロード", add))
  }
}