var success = function(data) {
    console.log('latitude: ' + data.coords.latitude + ' longitude: ' + data.coords.longitude);
    document.getElementById("scrawl").location.value = '' + data.coords.latitude + ', ' + data.coords.longitude;
};

var fail = function() {
    console.log('location failure :(');
    document.getElementById("scrawl").location.value = 'None Detected';
};

var logLocation = function() {

    if(navigator.geolocation){
       navigator.geolocation.getCurrentPosition(success, fail);
    }
    else{
       alert("Geolocation not available.");
    }
};


