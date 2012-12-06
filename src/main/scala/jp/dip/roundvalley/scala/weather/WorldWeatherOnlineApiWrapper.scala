package jp.dip.roundvalley.scala.weather


/* weather report
 * http://www.worldweatheronline.com/feed-generater.aspx
 * 
 */
import scala.io.Source
import scala.xml.XML
import scala.xml.parsing.{ConstructingParser,XhtmlParser}
import scala.xml.NodeSeq


object WorldWeatherOnlineApiWrapper {
    def getCurrentWeatherFromLatLon(key: String,lat:String,lng:String,day:String):String = {
        //ex. http://free.worldweatheronline.com/feed/weather.ashx?q=0.00,0.00&format=xml&num_of_days=2&key=???
    	val url = "http://free.worldweatheronline.com/feed/weather.ashx?q="+lat+","+lng+"&format=xml&num_of_days="+day+"&key="+key
    	val source = Source.fromURL(url)
    	val xml = XML.loadString( source.getLines.mkString )
    	val weatherDesc = xml \\ "weatherDesc"
    	weatherDesc.toList.head.text
	}
}
