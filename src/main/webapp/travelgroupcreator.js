let traveler;
let travelerList = [];
let isRetrieved = false;
let isGetHidden = false;
let travelerAddCount = 0;

function validateForm() {
    let pass = true;
    let firstName = document.getElementById("firstname").value;
    let lastName = document.getElementById("lastname").value;
    let passport = document.getElementById("passport").value;
    let age = document.getElementById("age").value;

    if (firstName == null || firstName == "",
    lastName == null || lastName == "",
    passport == null || passport == "",
    age == null || age == "" || age < 0 || age > 100) {
        alert("Please fill in all fields properly");
        pass = false;
    }
    return pass;
}

function addTraveler() {
    if (validateForm()) {
        if (travelerAddCount < 3) {
            traveler = {"firstName" : document.getElementById("firstname").value,
                "lastName" : document.getElementById("lastname").value,
                "passport" : document.getElementById("passport").value,
                "age" : document.getElementById("age").value}
            travelerList.push(traveler);
            clearInputFields();
            viewTravelers();
            travelerAddCount++;
        } else {
            clearInputFields();
            alert("Limit of 3 travelers reached")
        }
    }
}

function viewTravelers() {
    let travelerText = "";
    let count = 1;
    // console.log(travelerList);
    travelerList.forEach(myFunction);
    document.getElementById("view-travelers").innerHTML = travelerText;

    function myFunction(item) {
        travelerText += "Traveler " + count++ + ": " +JSON.stringify(item) + "<br>";
    }
}

function clearInputFields() {
    document.getElementById("firstname").value = "";
    document.getElementById("lastname").value = "";
    document.getElementById("passport").value = "";
    document.getElementById("age").value = "";
}

function resetTravelerInput() {
    clearInputFields();
    travelerList.length = 0;
    travelerAddCount = 0;
    document.getElementById("view-travelers").innerHTML = "";
}

function saveTraveler() {
    if (travelerList.length != 0) {
        let xhttp = new XMLHttpRequest();
        xhttp.open("POST", "http://localhost:8081/TravelWebsite_war_exploded/api/controller/addTravelers", true);
        xhttp.onreadystatechange = function() {
            if (xhttp.readyState > 3 && xhttp.status == 200) {
                let isAdded = this.responseText;
                if (isAdded == "true") {
                    clearInputFields();
                    travelerList.length = 0;
                    travelerAddCount = 0;
                    document.getElementById("view-travelers").innerHTML = "";
                    location.replace("travelplancreator.html");
                    alert("Travelers saved");
                } else {
                    alert("Travelers not saved")
                }
            }
        };
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify(travelerList));
        isRetrieved = false;
    } else {
        alert("No travelers added");
    }
}

function getTravelers() {
    if (!isRetrieved) {
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                let travelerList = JSON.parse(this.responseText);
                console.log(travelerList);
                let travelers = "";
                if (travelerList.length != 0) {
                    for (let index = 0; index < travelerList.length; index++) {
                        travelers +=
                            "<hr>" +
                            "First Name: " + travelerList[index].firstName + "<br/>" +
                            "Last Name: " + travelerList[index].lastName + "<br/>" +
                            "Passport: " + travelerList[index].passport + "<br/>" +
                            "Age: " + travelerList[index].age + "<br/>"
                    }
                    document.getElementById("get-travelers").innerHTML = travelers;
                } else {
                    alert("No travelers in list");
                }
            }
        };
        xhttp.open("GET", "http://localhost:8081/TravelWebsite_war_exploded/api/controller/getTravelers", true);
        xhttp.send();
        isRetrieved = true;
    }
    toggleGetTravelers();
}

function toggleGetTravelers() {
    let divElement = document.getElementById("get-travelers");
    if (divElement.style.display == "none" && !isGetHidden) {
        divElement.style.display = "none";
        isGetHidden = true;
    } else if (divElement.style.display == "block") {
        divElement.style.display = "none";
    } else {
        divElement.style.display = "block";
    }
}

function updateTraveler() {
    if (validateForm()) {
        let traveler = {"firstName" : document.getElementById("firstname").value,
            "lastName" : document.getElementById("lastname").value,
            "passport" : document.getElementById("passport").value,
            "age" : document.getElementById("age").value};
        let xhttp = new XMLHttpRequest();
        xhttp.open("PUT", "http://localhost:8081/TravelWebsite_war_exploded/api/controller/putTraveler", true);
        xhttp.onreadystatechange = function() {
            if (xhttp.readyState > 3 && xhttp.status == 200) {
                let isUpdated = this.responseText;
                if (isUpdated == "true") {
                    clearInputFields();
                    alert("Traveler updated");
                } else {
                    alert("Traveler not updated")
                }
            }
        };
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify(traveler));
        isRetrieved = false;
    }
}

function removeTraveler() {
    let passport = document.getElementById("passport").value;
    if (passport == null || passport == "") {
        alert("Please fill in passport field properly");
    } else {
        let xhttp = new XMLHttpRequest();
        xhttp.open("DELETE", "http://localhost:8081/TravelWebsite_war_exploded/api/controller/deleteTraveler", true);
        xhttp.onreadystatechange = function() {
            if (xhttp.readyState > 3 && xhttp.status == 200) {
                let isRemoved = this.responseText;
                if (isRemoved == "true") {
                    clearInputFields();
                    alert("Traveler removed");
                } else {
                    alert("Traveler not removed");
                }
            }
        };
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(passport);
        isRetrieved = false;
    }
}