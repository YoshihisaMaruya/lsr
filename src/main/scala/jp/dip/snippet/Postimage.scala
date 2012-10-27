package jp.dip.snippet

import _root_.scala.xml.{ NodeSeq, Text }
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.java.util.Date
import jp.dip.lib._
import Helpers._
import net.liftweb.http.FileParamHolder
import net.liftweb.http.RequestVar
import net.liftweb.util.Helpers._
import net.liftweb.http.SHtml

class Postimage {
  //フォーム用の宣言
  object img extends RequestVar[Box[FileParamHolder]](Empty)
  object lat extends RequestVar(Empty)
  object lon extends RequestVar(Empty)

  def upload(xhtml: NodeSeq): NodeSeq = {
    def add() {
      val a = Full(img.is)
      aa
      img.is match {
        case Full(FileParamHolder(_, mine, fname, file)) => {
          println(mine)
        }

      }

    }

    bind("upload", xhtml,
      "file" -> SHtml.fileUpload(fh => img(Full(fh))),
      "submit" -> SHtml.submit("アップロード", add))
  }
}