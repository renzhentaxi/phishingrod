// import Elements from "elements";

function request(method, url, data, callback) {
    const r = new XMLHttpRequest();
    r.open(method, url, true);
    r.onreadystatechange = function () {
        if (r.readyState === 4 && r.status === 200) {
            if (callback) callback(r.responseText);
        }
    };
    r.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    r.send(data);
}

function post(url, data, callback) {
    request("POST", url, data, callback);
}

function deleteRow(element) {
    const row = element.parentElement.parentElement;
    const id = row.getAttribute("data-pt-id");
    post("/api/phishingTarget/delete", `id=${id}`, () => row.parentElement.removeChild(row));
}

function addTarget() {
    const email = document.querySelector("#emailInput").value;
    const table = document.querySelector("#phishingTargetTable");
    post("api/phishingTarget/add",`emailAddress=${email}`, (data)=> {
        const newRow = Elements.row(data.emailAddress,data.lastModified,data.createdAt);
        table.appendChild(newRow);
    });
}


function editTarget(element) {
    const row = element.parentElement;
    const id = row.get("data-pt-id");
}