function postRequest(url, parameters, jsonData, callback) {

    url = new URL(url);
    url.search = new URLSearchParams(parameters);
    const options =
        {
            method: "POST",
        };


    if (jsonData !== null) {
        options.headers = {'content-type': "application/json"};
        options.body = JSON.stringify(jsonData);
    }

    fetch(url, options).then(response => callback(response));
}

function getRequest(url, callback) {
    fetch(url, {method: "GET"}).then(response => response.json()).then(json => callback(json));
}