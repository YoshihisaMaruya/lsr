package jp.dip.model

import net.liftweb.mapper._

class Image extends LongKeyedMapper[Image] with IdPK {     
  def getSingleton = Image                                 

  object lat extends MappedString(this, 100)
  object lon extends MappedString(this, 100)
  object wheather extends MappedString(this, 100)  
  object file_path extends MappedString(this,100)
  object created_datetime extends MappedDateTime(this)
}

object Image extends Image with LongKeyedMetaMapper[Image] {
  override def dbTableName = "Images"
}

