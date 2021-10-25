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

    //let locationLat = window.java.getLocationLatToJS();


    //Create a directions service
    var directionsService = new google.maps.DirectionsService();

    //Create a DirectionsRenderer object which we will use to display the route
    var directionsDisplay = new google.maps.DirectionsRenderer();

    //bind the DirectionsRenderer to the map
    directionsDisplay.setMap(map);

    let marker1, marker2, marker3;
    let counter = 0;

    google.maps.event.addListener(map, 'rightclick', function (event) {
        if (counter == 0) {
            marker1 = event.latLng;
        } else if (counter == 1) {
            marker2 = event.latLng;
            calcRoute();
        } else if (counter == 2) {
            marker3 = event.latLng;
            //calcRoute();
            getDistanceMatrix();
        }
        addMarker(event.latLng);
        //alert(window.java.getLocationLngToJS() + ", " + windows.java.getLocationLatToJS());
        counter++;
    });

    //Distance Matrix
    let distanceMatrix = [[]];
    var counter2 = 0;

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
                //alert(response.routes[0].legs[0].distance.text);

            }
        });
    }

    function getDistanceMatrix(){
        var distanceService = new google.maps.DistanceMatrixService();
        distanceService.getDistanceMatrix({
            origins: [marker1],
            destinations: [marker2, marker3],
            travelMode: 'DRIVING',
        }, callback)

        function callback(response, status) {
            if (status == 'OK') {
                var origins = response.originAddresses;
                var destinations = response.destinationAddresses;

                for (var i = 0; i < origins.length; i++) {
                    var results = response.rows[i].elements;
                    for (var j = 0; j < results.length; j++) {
                        var element = results[j];
                        //var distance = element.distance.text;
                        distanceMatrix[i][j] = element.distance.text;
                        var duration = element.duration.text;
                        var from = origins[i];
                        var to = destinations[j];
                    }
                }
                alert(distanceMatrix[0][0]);
            }
        }

    }/*
    for(let a=0;a<distanceMatrix.length;a++){
        for(let b=0;b<distanceMatrix.length;b++){
            if(distanceMatrix[a][b] !== undefined){
                alert(distanceMatrix[a][b]);
            }
        }
    }*/

}