package jp.dip.model

import net.liftweb.mapper._
import net.liftweb.http.FileParamHolder
import net.liftweb.util.Props
import java.io.InputStream
import scala.sys.process.Process
import java.io.FileOutputStream
import org.apache.commons._
import net.liftweb.http.S


object Voice extends Voice with LongKeyedMetaMapper[Voice] {
  override def dbTableName = "Voices" 
}

class Voice extends LongKeyedMapper[Voice] with IdPK  {
  def getSingleton = Voice

  object lat extends MappedString(this, 100) //軽度
  object lng extends MappedString(this, 100) //緯度
  object wheather extends MappedString(this, 100) //天気 
  object mimeType extends MappedString(this, 100) //ファイル形式
  object voice_file_path extends MappedString(this, 100) //音声ファイルパス
  object created_datetime extends MappedDateTime(this) //作成時間
  
  /*
   * @return : 保存ファイルパス
   */
   def saveFile(f: FileParamHolder): String = {
	      //保存用ディレクトリ
	  val save_dir = Props.get("save_voice.dirpath") openOr
      "/Users/tmp/"
	   
	   val is = f.fileStream
	   val e = f.mimeType match { 
	   	case "video/3gpp" => "3gpp" 
	   	case _ => throw new Exception(f.mimeType.toString() + "は受け付けられません")
	  } 
	  
	   val os = new FileOutputStream(save_dir + id + "." + f.mimeType)
	  try{
		  org.apache.commons.io.IOUtils.copy(is,os)
      }catch {
        case e =>  throw new Exception(e.getMessage())
      } finally {
        os.close
      }
      save_dir + id + "." + f.mimeType
  }
}
