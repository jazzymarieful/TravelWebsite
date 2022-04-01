let destinationList = [];
let destinationAddCount = 0;
let selectedDestinationList = [];
let selectedRating = "";
let selectedStartDate = "";
let selectedEndDate = "";
let duration = 0;

getDestinations();

function getDestinations() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            destinationList = JSON.parse(this.responseText);
            let destinations = "";
            if (destinationList.length != 0) {
                for (let index = 0; index < destinationList.length; index++) {
                    let destiCountry = "Country: " + destinationList[index].country + "<br/>";
                    let destiCity = (destinationList[index].city == null) ? "" : "City: " + destinationList[index].city + "<br/>";
                    let destiLocation = (destinationList[index].location == null) ? "" : "Location: " + destinationList[index].location + "<br/>";

                    destinations +=
                        "<hr>" + destiCountry + destiCity + destiLocation;
                }
                // document.getElementById("get-destinations").innerHTML = destinations;
            } else {
                alert("No destinations in list");
            }
            createDropDownList(destinationList);
        }
    };
    xhttp.open("GET", "http://localhost:8081/TravelWebsite_war_exploded/api/travelPlanController/getDestinations", true);
    xhttp.send();
}

function createDropDownList(incomingList) {
    let option = document.createElement("option");
    option.value = "";
    option.text = "Please choose";
    document.getElementById("destinationlist").appendChild(option);

    for (let index = 0; index < destinationList.length; index++) {
        let option = document.createElement("option");
        option.value = incomingList[index].destinationId;
        option.text = adjustDestinationList(index);
        document.getElementById("destinationlist").appendChild(option);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("destinationlist").addEventListener("input", handleSelect);
});

function handleSelect(ev) {
    let select = ev.target;
    if (select.value !== "" && destinationAddCount < 5) {
        selectedDestinationList.push(select.value);
        viewDestinations();
        destinationAddCount++
    } else {
        alert("Limit of 5 destinations reached")
    }
}

function viewDestinations() {
    let destinationText = "";
    let count = 1;
    selectedDestinationList.forEach(myFunction);
    document.getElementById("view-destinations").innerHTML = destinationText;

    function myFunction(item) {
        destinationText += "Destination " + count++ + ": " + adjustDestinationList(parseInt(item) - 1) + "<br>";
    }
}

function adjustDestinationList(index) {
    let destiCountry = destinationList[index].country;
    let destiCity = (destinationList[index].city == null) ? "" : ", " + destinationList[index].city;
    let destiLocation = (destinationList[index].location == null) ? "" : ", " + destinationList[index].location;

    return destiCountry + destiCity + destiLocation;
}

function resetDestinationSelection() {
    selectedDestinationList.length = 0;
    destinationAddCount = 0;
    viewDestinations();
}

let radios = document.querySelectorAll("input[type=radio][name='rate']");
radios.forEach(radio => radio.addEventListener('change', () => selectedRating = radio.value));

document.getElementById("startdate").setAttribute("min", new Date().toISOString().split("T")[0]);
document.getElementById("enddate").setAttribute("min", new Date().toISOString().split("T")[0]);
let btn = document.getElementById("btnConfirmDates");
btn.addEventListener('click', function onClick() {
    let startDate = document.getElementById("startdate").value;
    let endDate = document.getElementById("enddate").value;
    let differenceInTime = new Date(endDate).getTime() - new Date(startDate).getTime();
    let differenceInDays = differenceInTime / (1000 * 3600 * 24);

    if (differenceInDays >= 1 && differenceInDays <= 31) {
        selectedStartDate = startDate;
        selectedEndDate = endDate;
        duration = differenceInDays;
        btn.style.backgroundColor = "green";
        btn.style.color = "white";
    } else {
        btn.style.backgroundColor = "red";
        btn.style.color = "white";
        alert("Incorrect travel period chosen (up to 31 days)");
    }
});

function saveTravelPlan() {
    let travelPlanData = {"selectedDestinationList" : selectedDestinationList,
        "selectedRating" : selectedRating, "selectedStartDate" : selectedStartDate,
        "selectedEndDate" : selectedEndDate, "duration" : duration};
    if (travelPlanData.selectedDestinationList.length !== 0 && travelPlanData.selectedRating !== "" &&
        travelPlanData.selectedStartDate !== "" && travelPlanData.selectedEndDate !== "" && travelPlanData.duration !== 0) {
        console.log(travelPlanData);
        let xhttp = new XMLHttpRequest();
        xhttp.open("POST", "http://localhost:8081/TravelWebsite_war_exploded/api/travelPlanController/addDestinations", true);
        xhttp.onreadystatechange = function() {
            if (xhttp.readyState > 3 && xhttp.status == 200) {
                let isAdded = this.responseText;
                if (isAdded == "true") {
                    selectedDestinationList.length = 0;
                    destinationAddCount = 0;
                    document.getElementById("view-destinations").innerHTML = "";
                    location.replace("travelpackagecreator.html");
                    alert("Travel plan saved");
                } else {
                    alert("Travel plan not saved")
                }
            }
        };
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify(travelPlanData));
    } else {
        alert("Something went wong!!!");
    }
}