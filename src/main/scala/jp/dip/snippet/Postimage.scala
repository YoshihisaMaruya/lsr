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
import java.io.InputStream
import java.io.FileOutputStream

class Postimage {
  //フォーム用の宣言
  object img extends RequestVar[Box[FileParamHolder]](Empty)
  object lat extends RequestVar(Empty)
  object lon extends RequestVar(Empty)

  private def log(st: String) {
    println("##### " + st + "####")
  }

  /**
   * ImageTableに保存
   */
  private def add() {
     val f = img.is.openOr(null)
     f match {
      case FileParamHolder(_, mine, fname, file) => {
        val image_model = Image.create
        image_model.save() //idを取得するために、一度保存
        try{
        	val file_path = image_model.saveFile(f)
        	image_model.mimeType(mine).save()
        }
        catch{
          case e => S.error(e.getMessage())
        }
      }
      case _ => S.error("ファイルを選択してください");
    }
  }

  /**
   * ファイルアップロード用フォーム
   */
  def upload(xhtml: NodeSeq): NodeSeq = {

    bind("upload", xhtml,
      "file" -> SHtml.fileUpload(fh => img(Full(fh))),
      "submit" -> SHtml.submit("アップロード", add))
  }
}