let map;

function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
            center: {lat: 40.767, lng: 29.917},
            zoom: 14,
        }
    );

    //addMarker({ lat: 40.767 , lng: 29.917 });
    //Add marker function
    function addMarker(coords) {
        var marker = new google.maps.Marker({
            position: coords,
            map: map
        });
    }

    let locationLat = window.java.getLocationLatToJS();


    //Create a directions service
    var directionsService = new google.maps.DirectionsService();

    //Create a DirectionsRenderer object which we will use to display the route
    var directionsDisplay = new google.maps.DirectionsRenderer();

    //bind the DirectionsRenderer to the map
    directionsDisplay.setMap(map);

    let marker1, marker2;
    let counter = 0;

    google.maps.event.addListener(map, 'rightclick', function (event) {
        if (counter == 0) {
            marker1 = event.latLng;
        } else if (counter == 1) {
            marker2 = event.latLng;
            calcRoute();
        }
        addMarker(event.latLng);
        //alert(window.java.getLocationLngToJS() + ", " + windows.java.getLocationLatToJS());
        counter++;
    });

    function calcRoute() {
        var start = marker1;
        var end = marker2;
        var request = {
            origin: start,
            destination: end,
            travelMode: google.maps.TravelMode.DRIVING
        };
        //Pass the request to the route method
        directionsService.route(request, function (response, status) {
            if (status == google.maps.DirectionsStatus.OK) {
                directionsDisplay.setDirections(response);
            }
        });
    }

}