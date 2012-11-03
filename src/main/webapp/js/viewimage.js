function drawmap(locations) {

	var myOptions = {
		zoom : 12,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	var map = new google.maps.Map(document.getElementById("map"), myOptions);
	initialLocation = new google.maps.LatLng(locations.loc[0].lat, locations.loc[0].lng);
	map.setCenter(initialLocation);
	for ( i = 0; i < locations.loc.length; i++) {
		var latlng = new google.maps.LatLng(locations.loc[i].lat, locations.loc[i].lng);

		var myMarker = new google.maps.Marker({
			position : latlng, //どこに表示するか
			map : map, //気にしないで
			title : "名前 : " + locations.loc[i].title, //ポップアップの部分
			animation : google.maps.Animation.DROP,
		});
		//マーカーにアクションをセット
		attachMessage(myMarker, locations.loc[i].video_file_path);
	}
}

//マーカー上のhtml
function createContentString(video_file_path) {
	var contentString = '<a href="a.shtml" target="_top">フレームをすべて解除してリンクします</a>'
	return contentString
}

//マーカーのアクション
function attachMessage(marker, video_file_path) {
	google.maps.event.addListener(marker, 'click', function() {
		new google.maps.Geocoder().geocode({
			latLng : marker.getPosition()
		}, function(result, status) {
			if (status == google.maps.GeocoderStatus.OK) {
				new google.maps.InfoWindow({
					content : createContentString(video_file_path)
				}).open(marker.getMap(), marker);
			}
		});
	});
}
