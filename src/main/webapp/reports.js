let selectedYear = "";
let selectedPeriod = "";
let reportList = ["Traveler Report", "Destination Report", "Travel Period Report"];
let selectedReport = "";

reportDropDownList(reportList);
yearDropDownList();

function reportDropDownList(incomingList) {
    let option = document.createElement("option");
    option.value = "";
    option.text = "Please choose";
    document.getElementById("reportSelector").appendChild(option);

    for (let index = 0; index < incomingList.length; index++) {
        let option = document.createElement("option");
        option.value = incomingList[index];
        option.text = incomingList[index];
        document.getElementById("reportSelector").appendChild(option);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("reportSelector").addEventListener("input", handleReportSelect);
});
function handleReportSelect(ev) {
    let select = ev.target;
    if (select.value !== "") {
        selectedReport = select.value;
        document.getElementById("selectedReport").innerHTML = selectedReport;
    }
}

function yearDropDownList() {
    let currentYear = new Date().getFullYear();
    let yearLimit = 2018;
    let option = document.createElement("option");
    option.value = "";
    option.text = "Please choose";
    document.getElementById("yearSelector").appendChild(option);
    while (currentYear >= yearLimit) {
        let option = document.createElement("option");
        option.value = currentYear;
        option.text = currentYear;
        document.getElementById("yearSelector").add(option);
        currentYear--;
    }
}

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("yearSelector").addEventListener("input", handleYearSelect);
});
function handleYearSelect(ev) {
    let select = ev.target;
    if (select.value !== "") {
        selectedYear = select.value;
        document.getElementById("selectedYear").innerHTML = selectedYear;
    }
}

let radios = document.querySelectorAll("input[type=radio][name='period']");
radios.forEach(radio => radio.addEventListener('change', () => getSelectedPeriod(radio)));

function getSelectedPeriod(radio) {
    selectedPeriod =radio.value;
    document.getElementById("selectedPeriod").innerHTML = radio.id;
}

function getReport() {
    let reportViewer = new ViewerAdapter(selectedReport);
    let reportRequest = {"selectedReport": selectedReport, "selectedYear": selectedYear, "selectedPeriod": selectedPeriod}
    console.log(reportRequest);
    if (selectedReport !== "" && selectedYear !== "" && selectedPeriod !== "") {
        let xhttp = new XMLHttpRequest();
        xhttp.open("POST", "http://localhost:8081/TravelWebsite_war_exploded/api/travelReportController/getReport", true);
        xhttp.onreadystatechange = function() {
            if (xhttp.readyState > 3 && xhttp.status == 200) {
                let reportList = JSON.parse(this.responseText);
                console.log(reportList);
                if (reportList.length !== 0) {
                    // viewReport(reportList);
                    document.getElementById("view-reports").innerHTML = reportViewer.report(reportList);
                    resetSelection();
                } else {
                    document.getElementById("view-reports").innerHTML = "No reports available";
                    resetSelection();
                }
            }
        };
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(JSON.stringify(reportRequest));
    } else {
        alert("Please make a proper selection")
    }
}

function resetSelection() {
    selectedReport = "";
    selectedYear = "";
    selectedPeriod = "";
    document.getElementById("selectedReport").innerHTML = "";
    document.getElementById("selectedYear").innerHTML = "";
    document.getElementById("selectedPeriod").innerHTML = "";
}

function StandardViewer() {
    this.report = function (incomingList) {
        let reportText = "";
        incomingList.forEach(myFunction);
        function myFunction(value) {
            reportText += value + "<br>";
        }
        return reportText;
    }
}

function NewViewer() {
    this.report = function (incomingList, selectedReport) {
        let reportText = "<h3>Description: This report shows...........</h3><br>";
        incomingList.forEach(myFunction);
        function myFunction(value) {
            switch (selectedReport) {
                case "Traveler Report":
                    reportText += "Age range of travelers: " + value.ageRange +
                        ", Year of report: " + value.year +
                        ", Amount of travelers: " + value.ageAmount + "<br";
                    break;
                case "Destination Report":
                    reportText += "Destination: " + value.destination.country +
                        ", " + (value.destination.city != null ? value.destination.city : value.destination.location) +
                        ", Year of report: " + value.year +
                        ", Number of visits: " + value.frequency + "<br>";
                    break;
                case "Travel Period Report":
                    reportText += "Year or quarter of report: " + value.period +
                        ", Number of visits: " + value.frequency + "<br>";
                    break;
            }

        }
        return reportText;
    }
}

function ViewerAdapter(selectedReport) {
    let viewer = new NewViewer();
    this.report = function (incomingList) {
        return viewer.report(incomingList, selectedReport);
    }

}


