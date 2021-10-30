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


    let marker1, marker2, marker3, marker4;

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
        markerArray.push(event.latLng);
        addMarker(event.latLng);
        //alert(window.java.getLocationLngToJS() + ", " + windows.java.getLocationLatToJS());
    });



}

const distanceMatrix = [];

function calcRoute() {
    //Create a directions service
    var directionsService = new google.maps.DirectionsService();

    //Create a DirectionsRenderer object which we will use to display the route
    var directionsDisplay = new google.maps.DirectionsRenderer();

    //API GET
    const data = {
        id: '01',
        name: JSON.stringify(distanceMatrix),
        route: ''
    }

    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    };
    fetch(api_url, options);

    //API GET


    var waypoints = new Array();

    //bind the DirectionsRenderer to the map
    directionsDisplay.setMap(map);
    for(i=1;i<markerArray.length-1;i++){
        var adress = markerArray[i];
        if(adress !== ""){
            waypoints.push({
                location: adress,
                stopover: true
            })
        }
    }
    var request = {
        origin: markerArray[0],
        destination: markerArray[markerArray.length-1],
        waypoints: waypoints,//[{location: markerArray[2]},{location: markerArray[3]}],
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