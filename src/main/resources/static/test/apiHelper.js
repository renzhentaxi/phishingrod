function postRequest(url, parameters, jsonData, callback) {

    url = new URL(url);
    if (parameters != null)
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

function getRequest(url, parameters, callback) {
    console.log(callback);
    fetch(url, {method: "GET"}).then(response => response.json()).then(json => callback(json));
}