/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*参考
 * GPS
 * http://www.atmarkit.co.jp/fdotnet/chushin/introhtml5_01/introhtml5_01_01.html
 * http://www6.ocn.ne.jp/~wot/web/html5/geoapi/wp.html
 * http://gihyo.jp/dev/feature/01/location-based-services/0003
 * http://www.extjs.co.jp/blog/2010/10/15/the-html5-family-geolocation/
 * 加速度
 * http://ameblo.jp/chicktack123/entry-11299544291.html
 * http://d.hatena.ne.jp/moto_maka/20120604/1338752269
 */

window.onload = function(){
    document.getElementById("nowload").style.display= "";
    document.getElementById("contents").style.display= "none";
    gpsReceive();
} 
