package jp.dip.snippet

import _root_.net.liftweb.http.ResourceServer

class ResourceLoc {
	def show = <p>{ResourceServer.baseResourceLocation}</p>
}