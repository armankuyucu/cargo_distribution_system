let map;


let markerArray = [];
let startLocation;
const api_url = "http://localhost:8080/";

function initMap() {
    map = new google.maps.Map(document.getElementById("map"), {
            center: {lat: 40.767, lng: 29.917},
            zoom: 14,
        }
    );
/*
    getData();

    async function getData() {
        const response = await fetch(api_url);
        const data = await response.json();
        console.log(data[0].name);
    }*/


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

    let marker1, marker2, marker3, marker4;
    let counter = 0;

    google.maps.event.addListener(map, 'rightclick', function (event) {

        /*
        if (counter === -1) {
            startLocation = event.latLng;
            counter++;
        } else {
            markerArray[counter] = event.latLng;
            counter++;
        }*/
        /*if (counter == 0) {
            marker1 = event.latLng;
        } else if (counter == 1) {
            marker2 = event.latLng;
            calcRoute();
        } else if (counter == 2) {
            marker3 = event.latLng;
            //getDistanceMatrix();
        } else if (counter == 3) {
            marker4 = event.latLng;
            getDistanceMatrix();
        }*/
        markerArray[counter] = event.latLng;
        addMarker(event.latLng);
        counter++;
        //alert(window.java.getLocationLngToJS() + ", " + windows.java.getLocationLatToJS());
    });



}

function calcRoute() {
    var start = markerArray[0];
    var end = markerArray[1];
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

function getDistanceMatrix() {
    var distanceMatrix = [];
    var distanceService = new google.maps.DistanceMatrixService();
    let counter2 = 0;
    distanceService.getDistanceMatrix({
        origins: markerArray,
        destinations: markerArray,/*[markerArray[0], markerArray[1], markerArray[2], markerArray[3], markerArray[4], markerArray[5],
            markerArray[6], markerArray[7], markerArray[8], markerArray[9]],*/
        travelMode: 'DRIVING',
    }, callback)

    function callback(response, status) {
        if (status == 'OK') {

                var origins = response.originAddresses;
                var destinations = response.destinationAddresses;

                for (let i = 0; i < origins.length; i++) {
                    const results = response.rows[i].elements;
                    for (let j = 0; j < results.length; j++) {
                        let element = results[j];

                        var distance = element.distance.text;
                        distanceMatrix[counter2] = element.distance.text;
                        var duration = element.duration.text;
                        var from = origins[i];
                        var to = destinations[j];
                        counter2++;
                    }
                }
            //alert(distanceMatrix[0][0]);

            const data = {
                id: '01',
                name: JSON.stringify(distanceMatrix),
                route: ''
            }

            const options = {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            };
            fetch(api_url, options);
        }
    }
}