function deleteRow(element)
{
    const row = element.parentElement.parentElement;
    const id = row.getAttribute("data-pt-id");
    const xhttp = new XMLHttpRequest();
    xhttp.open("POST", "all/delete", true);
    xhttp.onreadystatechange= ()=> {
        if(xhttp.readyState ===4 && xhttp.status === 200)
        {
            if(xhttp.responseText === "false")
                console.error("Trying to delete an already deleted target");
            row.parentElement.removeChild(row);
        }
    };
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.send(`id=${id}`);
}

function addTarget()
{
    const email = document.querySelector("#emailInput").value;
    const xhttp = new XMLHttpRequest();

    xhttp.open("POST", "all/add", true);
    xhttp.onreadystatechange= ()=> {
        if(xhttp.readyState ===4 && xhttp.status === 200)
        {
            if(xhttp.responseText === "false")
                console.error("something went wrong");
        }
    };
    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhttp.send(`emailAddress=${email}`);
}

