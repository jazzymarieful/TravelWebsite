let destination;
let destinationList = [];
let isRetrieved = false;
let isGetHidden = false;

function validateForm() {
    let pass = true;
    let destinationId = document.getElementById("destinationid").value;
    let country = document.getElementById("country").value;
    let city = document.getElementById("city").value;
    let location = document.getElementById("location").value;

    if (destinationId == null || destinationId == "",
    country == null || country == "",
    city == null || city == "",
    location == null || location == "") {
        alert("Please fill in all fields properly");
        pass = false;
    }
    return pass;
}

function addDestination() {
    if (validateForm()) {
        destination = {"destinationId" : document.getElementById("destinationid").value,
            "country" : document.getElementById("country").value,
            "city" : document.getElementById("city").value,
            "location" : document.getElementById("location").value};
        destinationList.push(destination);
        clearInputFields();
        viewDestination();
    }
}

function viewDestination() {
    let destinationText = "";
    let count = 1;
    // console.log(destinationList);
    destinationList.forEach(myFunction);
    document.getElementById("view-destination").innerHTML = destinationText;

    function myFunction(item) {
        destinationText += "Destination " + count++ + ": " +JSON.stringify(item) + "<br>";
    }
}

function clearInputFields() {
    document.getElementById("destinationid").value = "";
    document.getElementById("country").value = "";
    document.getElementById("city").value = "";
    document.getElementById("location").value = "";
}

function saveDestinations() {
    if (destinationList.length != 0) {
        let xhttp = new XMLHttpRequest();
        xhttp.open("POST", "http://localhost:8081/TravelWebsite_war_exploded/api/destinationController/addDestinations", true);
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState > 3 && xhttp.status == 200) {
                let isAdded = this.responseText;
                if (isAdded == "true") {
                    clearInputFields();
                    destinationList.length = 0;
                    document.getElementById("view-destination").innerHTML = "";
                    alert("Destinations saved");
                } else {
                    alert("Destinations not saved")
                }
            }
        };
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify(destinationList));
        isRetrieved = false;
    } else {
        alert("No destinations added");
    }
}

function getDestinations() {
    if (!isRetrieved) {
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                let destinationList = JSON.parse(this.responseText);
                console.log(destinationList);
                let destinations = "";
                if (destinationList.length != 0) {
                    for (let index = 0; index < destinationList.length; index++) {
                        destinations +=
                            "<hr>" +
                            "Destination Id: " + destinationList[index].destinationId + "<br/>" +
                            "Country: " + destinationList[index].country + "<br/>" +
                            "City: " + destinationList[index].city + "<br/>" +
                            "Location: " + destinationList[index].location + "<br/>"
                    }
                    document.getElementById("get-destinations").innerHTML = destinations;
                } else {
                    alert("No destinations in list");
                }
            }
        };
        xhttp.open("GET", "http://localhost:8081/TravelWebsite_war_exploded/api/destinationController/getDestinations", true);
        xhttp.send();
        isRetrieved = true;
    }
    toggleGetDestinations();
}

function toggleGetDestinations() {
    let divElement = document.getElementById("get-destinations");
    if (divElement.style.display == "none" && !isGetHidden) {
        divElement.style.display = "none";
        isGetHidden = true;
    } else if (divElement.style.display == "block") {
        divElement.style.display = "none";
    } else {
        divElement.style.display = "block";
    }
}

function updateDestination() {
    if (validateForm()) {
        let destination = {"destinationId" : document.getElementById("destinationid").value,
            "country" : document.getElementById("country").value,
            "city" : document.getElementById("city").value,
            "location" : document.getElementById("location").value}
        let xhttp = new XMLHttpRequest();
        xhttp.open("PUT", "http://localhost:8081/TravelWebsite_war_exploded/api/destinationController/putDestination", true);
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState > 3 && xhttp.status == 200) {
                let isUpdated = this.responseText;
                if (isUpdated == "true") {
                    clearInputFields();
                    alert("Destination updated");
                } else {
                    alert("Destination not updated")
                }
            }
        };
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify(destination));
        isRetrieved = false;
    }
}

function removeDestination() {
    let destinationId = document.getElementById("destinationId").value;
    console.log(destinationId);
    if (destinationId == null || destinationId == "") {
        alert("Please fill in destinationId field properly");
    } else {
        let xhttp = new XMLHttpRequest();
        xhttp.open("DELETE", "http://localhost:8081/TravelWebsite_war_exploded/api/destinationController/deleteDestination", true);
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState > 3 && xhttp.status == 200) {
                let isRemoved = this.responseText;
                if (isRemoved == "true") {
                    clearInputFields();
                    alert("Destination removed");
                } else {
                    alert("Destination not removed");
                }
            }
        };
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(destinationId);
        isRetrieved = false;
    }
}
