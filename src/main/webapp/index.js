let isLoggedIn = false;
let logInAttempt = 0;
let userAccount = sessionStorage.getItem("userAccount");

checkLoggedIn();
startJPA();

function startJPA() {
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", "http://localhost:8081/TravelWebsite_war_exploded/api/jpaController/startJPA", true);
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let successMessage = this.responseText;
            // alert(successMessage);
        }
    };
    xhttp.send();
}

function signIn() {
    if (!isLoggedIn) {
        document.getElementById("myForm").style.display = "block";
    } else {
        if (confirm("Are you sure you want to sign out?")) {
            document.getElementById("btnSign").innerHTML = "Sign In";
            document.getElementById("loggedInPage").hidden = true;
            document.getElementById("navbar").hidden = true;
            document.getElementById("update-section").hidden = true;
            sessionStorage.clear();
            isLoggedIn = false;
        }
    }
}


function closeForm() {
    document.getElementById("myForm").style.display = "none";
}

function logIn() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    userAccount = {"username" : username, "password" : password};
    if (username !== "" && password !== "") {
        if (logInAttempt === 3) {
            alert("Log in attempts exceeded, you have no business here!!")
            return;
        }
        let xhttp = new XMLHttpRequest();
        xhttp.open("POST", "http://localhost:8081/TravelWebsite_war_exploded/api/travelPackageController/verifyAccount", true);
        xhttp.onreadystatechange = function() {
            if (xhttp.readyState > 3 && xhttp.status == 200) {
                let isVerified = this.responseText;
                if (isVerified == "true") {
                    isLoggedIn = true;
                    logInAttempt = 0;
                    document.getElementById("myForm").style.display = "none";
                    document.getElementById("btnSign").innerHTML = "Sign Out";
                    document.getElementById("welcomeMassage").innerHTML = "Welcome " + username;
                    document.getElementById("loggedInPage").hidden = false;
                    document.getElementById("navbar").hidden = false;
                    sessionStorage.setItem("userAccount", JSON.stringify(userAccount));
                    sessionStorage.setItem("isLoggedIn", "true");
                } else {
                    logInAttempt++;
                    alert("Incorrect credentials entered")
                }
            }
        };
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify(userAccount));
    } else {
        alert("Incorrect credentials entered")
    }
}

function logInToggle() {
    if (!isLoggedIn) {
        document.getElementById("login_container").hidden = false;
    } else {
        alert("Already logged in")
    }
}

function checkLoggedIn() {
    if (sessionStorage.getItem("isLoggedIn") === "true") {
        isLoggedIn = true;
        document.getElementById("btnSign").innerHTML = "Sign Out";
        document.getElementById("welcomeMassage").innerHTML = "Welcome " + JSON.parse(sessionStorage.getItem("userAccount")).username;
        document.getElementById("loggedInPage").hidden = false;
        document.getElementById("navbar").hidden = false;
    }
}

function viewTravelPackages() {
    // console.log(userAccount);
    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:8081/TravelWebsite_war_exploded/api/travelPackageController/getTravelPackages", true);
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState > 3 && xhttp.status == 200) {
            let travelPackageList = JSON.parse(this.responseText);
            console.log(travelPackageList);
            let travelPackageText = "";
            for (let index = 0; index < travelPackageList.length; index++) {
                travelPackageText +=
                    "<hr>" +
                    "Travel package ID:" + travelPackageList[index].travelPackageId +
                    // ", Travel group ID:" + travelPackageList[index].travelGroup.travelGroupId +
                    ", Traveler count:" + travelPackageList[index].travelGroup.travelerCount +
                    "<br>";
                travelPackageList[index].travelGroup.travelers.forEach(function (traveler) {
                    travelPackageText +=
                        // "Traveler ID:" + traveler.travelerId +
                        "Traveler first name:" + traveler.firstName +
                        ", Traveler last name:" + traveler.lastName +
                        ", Traveler passport:" + traveler.passport +
                        ", Traveler age:" + traveler.age +
                        "<br>"
                });
                travelPackageText +=
                    // "Travel plan ID:" + travelPackageList[index].travelPlan.travelPlanId +
                    "Travel plan start date:" + travelPackageList[index].travelPlan.startDate +
                    ", Travel plan end date:" + travelPackageList[index].travelPlan.endDate +
                    ", Travel plan duration:" + travelPackageList[index].travelPlan.duration +
                    "<br>"
                travelPackageList[index].travelPlan.travelSegments.forEach(function (travelSegment, thisIndex) {
                    if (travelPackageList[index].travelPlan.travelSegments.length === 1) {
                        travelPackageText +=
                            "Destination:" + travelSegment.destination.country +
                            ", " + (travelSegment.destination.city != null ? travelSegment.destination.city : travelSegment.destination.location) +
                            ", Accommodation:" + travelSegment.accommodation.name +
                            "<br>" +
                            "Destination:" + travelSegment.followUpDestination.country +
                            ", " + (travelSegment.followUpDestination.city != null ? travelSegment.followUpDestination.city : travelSegment.followUpDestination.location) +
                            ", Accommodation:" + travelSegment.followUpAccommodation.name +
                            "<br>"
                    } else if (thisIndex === travelPackageList[index].travelPlan.travelSegments.length - 1) {
                        travelPackageText +=
                            "Destination:" + travelSegment.destination.country +
                            ", " + (travelSegment.destination.city != null ? travelSegment.destination.city : travelSegment.destination.location) +
                            ", Accommodation:" + travelSegment.accommodation.name +
                            "<br>" +
                            "Destination:" + travelSegment.followUpDestination.country +
                            ", " + (travelSegment.followUpDestination.city != null ? travelSegment.followUpDestination.city : travelSegment.followUpDestination.location) +
                            ", Accommodation:" + travelSegment.followUpAccommodation.name +
                            "<br>"
                    } else {
                        travelPackageText +=
                            "Destination:" + travelSegment.destination.country +
                            ", " + (travelSegment.destination.city != null ? travelSegment.destination.city : travelSegment.destination.location) +
                            ", Accommodation:" + travelSegment.accommodation.name +
                            "<br>"
                    }
                });
                travelPackageText +=
                "<button style='margin-right: 5px' id=" + travelPackageList[index].travelPackageId + " onclick='removeTravelPackage(this.id)'>Remove</button>";
                travelPackageText +=
                "<button id=" + travelPackageList[index].travelPackageId + " onclick='showUpdatePopup(this.id)'>Update</button>";
            }
            document.getElementById("view-travelpackages").innerHTML = travelPackageText;
        }
    };
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(sessionStorage.getItem("userAccount"));
}

function removeTravelPackage(id) {
    if (confirm("Are you sure you want to delete this travel package?")) {
        let travelPackageId = id /*{"travelPackageId": id}*/;
        console.log(travelPackageId);
        let xhttp = new XMLHttpRequest();
        xhttp.open("DELETE", "http://localhost:8081/TravelWebsite_war_exploded/api/travelPackageController/deleteTravelPackage", true);
        xhttp.onreadystatechange = function () {
            if (xhttp.readyState > 3 && xhttp.status == 200) {
                let isRemoved = this.responseText;
                if (isRemoved == "true") {
                    viewTravelPackages();
                    alert("Destination removed");
                } else {
                    alert("Destination not removed");
                }
            }
        };
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(travelPackageId);
    }
}

function showUpdatePopup(id) {
    document.getElementById("update-section").style.display = "block";
    document.addEventListener("DOMContentLoaded", () => {
        document.getElementById("btnUpdate").addEventListener("click", updateTravelPackage);
    });
}

function cancelUpdate() {
    document.getElementById("update-section").style.display = "none";
}

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

function updateTravelPackage() {
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
    }

}


