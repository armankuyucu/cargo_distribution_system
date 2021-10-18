let map;
let rightClickLat,rightClickLng;

function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 40.767 , lng: 29.917 },
        zoom: 14,
    }
    );

    //addMarker({ lat: 40.767 , lng: 29.917 });
    //Add marker function
    function addMarker(coords) {
        var marker = new google.maps.Marker({
            position:coords,
            map:map
        });
    }

    google.maps.event.addListener(map, 'rightclick', function(event) {
        addMarker(event.latLng);
    });

}