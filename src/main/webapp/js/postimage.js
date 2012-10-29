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
    document.getElementById("nowloading").style.display= "";
    document.getElementById("success").style.display= "none";
    gpsReceive();
} 

//gps受信用
function gpsReceive(){
    var position_options = {
        enableHighAccuracy: true
    };
    if (navigator.geolocation) navigator.geolocation.watchPosition(gpsReceiveSuccess,gpsReceiveFailed,position_options);
    else window.alert("");
    navigator.geolocation.getCurrentPosition(
        gpsReceiveSuccess, gpsReceiveFailed,position_options
    );
}

//success
function gpsReceiveSuccess(position){ 
    lat = position.coords.latitude;
    lng = position.coords.longitude;
    document.getElementById("nowloading").style.display= "none";
    document.getElementById("success").style.display= "";
    document.getElementById("lat").value = lat;
    document.getElementById("lon").value = lng;
}

function gpsReceiveFailed(err){
    document.querySelector('#gps_status').textContent = "not";
}