let userAccount = JSON.parse(sessionStorage.getItem("userAccount"));

saveTravelPackage(userAccount);

function saveTravelPackage(userAccount) {
    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", "http://localhost:8081/TravelWebsite_war_exploded/api/travelPackageController/addTravelPackage", true);
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState > 3 && xhttp.status == 200) {
            let travelPackage = JSON.parse(this.responseText);
            console.log(travelPackage)
            NewViewTravelPackage(travelPackage);
        }
    };
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify(userAccount));
}

function OldViewTravelPackage() {
    let travelPackageText = function (travelPackage) {
        let text;
        let keys = Object.keys(travelPackage);
        console.log(keys);
        keys.forEach((key, index) => {
            text += `${key}: ${travelPackage[key]}`;
            console.log(`${key}: ${travelPackage[key]}`);
        });
        return text;
    }
    alert(travelPackageText);
    // document.getElementById("view-travelpackage").innerHTML = travelPackageText;
}

function viewTravelPackageAdapter(travelPackage) {
    let viewer = new NewViewTravelPackage();


}



function NewViewTravelPackage(travelPackage) {
    let travelPackageText = "";
    travelPackageText +=
        "Travel package ID:" + travelPackage.travelPackageId +
        ", Travel group ID:" + travelPackage.travelGroup.travelGroupId +
        ", Traveler count:" + travelPackage.travelGroup.travelerCount +
        "<br>";
    travelPackage.travelGroup.travelers.forEach(function (traveler) {
        travelPackageText +=
            "Traveler ID:" + traveler.travelerId +
            ", Traveler first name:" + traveler.firstName +
            ", Traveler last name:" + traveler.lastName +
            ", Traveler passport:" + traveler.passport +
            ", Traveler age:" + traveler.age +
            "<br>"
    });
    travelPackageText +=
        "Travel plan ID:" + travelPackage.travelPlan.travelPlanId +
        ", Travel plan start date:" + travelPackage.travelPlan.startDate +
        ", Travel plan end date:" + travelPackage.travelPlan.endDate +
        ", Travel plan duration:" + travelPackage.travelPlan.duration +
        "<br>"
    travelPackage.travelPlan.travelSegments.forEach(function (travelSegment, thisIndex) {
        if (travelPackage.travelPlan.travelSegments.length === 1) {
            travelPackageText +=
                "Destination:" + travelSegment.destination.country +
                ", " + (travelSegment.destination.city != null ? travelSegment.destination.city : travelSegment.destination.location) +
                ", Accommodation:" + travelSegment.accommodation.name +
                "<br>" +
                "Destination:" + travelSegment.followUpDestination.country +
                ", " + (travelSegment.followUpDestination.city != null ? travelSegment.followUpDestination.city : travelSegment.followUpDestination.location) +
                ", Accommodation:" + travelSegment.followUpAccommodation.name +
                "<br>"
        } else if (thisIndex === travelPackage.travelPlan.travelSegments.length - 1) {
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
    document.getElementById("view-travelpackage").innerHTML = travelPackageText;
}