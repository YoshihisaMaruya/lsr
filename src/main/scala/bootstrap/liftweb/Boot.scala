package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import _root_.net.liftweb.http.provider._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import _root_.net.liftweb.mapper.{ DB, ConnectionManager, Schemifier, DefaultConnectionIdentifier, StandardDBVendor }
import _root_.java.sql.{ Connection, DriverManager }
import _root_.jp.dip.model._
import net.liftweb.db.ConnectionIdentifier

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    DefaultConnectionIdentifier.jndiName = "lift"
    if (!DB.jndiJdbcConnAvailable_?) {
     val vendor = new StandardDBVendor("com.mysql.jdbc.Driver", "jdbc:mysql://12.7.0.0.1:3306/lsr", Box("root"), Box("ma1192"))
     LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)
     DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }

     Schemifier.schemify(true, Log.infoF _,Image)
    // where to search snippet
    LiftRules.addToPackages("jp.dip")
   

    // Build SiteMap
    //メニュバーにサイトマップ
    def sitemap() = SiteMap(
      Menu("Home") / "index" // Simple menu form
      // Menu with special Link
      )

    LiftRules.setSiteMapFunc(() => User.sitemapMutator(sitemap()))

    /*
     * Show the spinny image when an Ajax call starts
     */
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    /*
     * Make the spinny image go away when it ends
     */
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    LiftRules.early.append(makeUtf8)

    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    S.addAround(DB.buildLoanWrapper)
  }

  /**
   * Force the request to be UTF-8
   */
  private def makeUtf8(req: HTTPRequest) {
    req.setCharacterEncoding("UTF-8")
  }
}

