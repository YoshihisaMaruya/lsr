package jp.dip.model

import net.liftweb.mapper._
import net.liftweb.http.FileParamHolder
import net.liftweb.util.Props
import java.io.InputStream
import scala.sys.process.Process
import java.io.FileOutputStream
import org.apache.commons._

class Image extends LongKeyedMapper[Image] with IdPK {
  def getSingleton = Image

  object lat extends MappedString(this, 100) //軽度
  object lon extends MappedString(this, 100) //緯度
  object wheather extends MappedString(this, 100) //天気 
  object mimeType extends MappedString(this, 100) //ファイル形式
  object video_file_path extends MappedString(this, 100) //ビデオファイルパス
  object thumbnail_file_path extends MappedString(this, 100) //サムネイルパス
  object created_datetime extends MappedDateTime(this) //作成時間
  
  /**
   * ファイルを保存(パスはprops参照)
   * (id,f) : file_path
   */
  def saveFile(f: FileParamHolder): (String,String) = {
    //保存用ディレクトリ
    val save_dir = Props.get("save.dirpath") openOr
      "/Users/tmp/"

    //ローカルファイル書き込み用
    /**
     * 動画を保存。サムレイルを作成
     */
    def write(extention: String, is: InputStream): (String,String) = {
      //一時ファイルを作成
      val tmp_file_path = save_dir + id + "." + extention
      val os = new FileOutputStream(tmp_file_path)
      try{
    	  org.apache.commons.io.IOUtils.copy(is, os)
      }catch {
        case e =>  throw new Exception(e.getMessage())
      } finally {
        os.close
      }
      
      //ビデオとサムレイルを作成
      val video_file_path = save_dir + id + ".flv"
      val thumbnail_file_path = save_dir + id + ".jpg"
      println("####" + video_file_path + "####")
      extention match {
      	case "mp4" => Process("mv " + tmp_file_path + " "+ video_file_path).run
      	case "mov" => Process("ffmpeg -i " + tmp_file_path +" " + video_file_path + ";rm " + tmp_file_path).run
      	case _ => null
      }
      Process("ffmpeg -i " + video_file_path +" -ss 1 -vframes 1 -f image2 " + thumbnail_file_path)
      (video_file_path,thumbnail_file_path)
    }

    //ファイル形式によって、コマンドを分ける。
    val extention = f.mimeType match {
      case "video/mp4" => "mp4"
      case "video/MOV" => "mov"
      case _ => throw new Exception("ファイル形式をサポートしていません。")
    }
    try{
      write(extention,f.fileStream)
    }
    catch{
      case e => throw new Exception(e.getMessage())
    }
  }
}

object Image extends Image with LongKeyedMetaMapper[Image] {
  override def dbTableName = "Images" 
}

