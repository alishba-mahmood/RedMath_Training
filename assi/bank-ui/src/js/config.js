fetch('http://localhost:1080/api/accounts', {
    method: 'GET', // or 'POST', 'PUT', 'DELETE', etc.
    headers: {
        'Content-Type': 'application/json', // Specify the content type if needed
    },
    // Other options like body for POST requests
})
.then(response => response.json()) // Parse the response as JSON
.then(data => {
    // Handle the data received from the backend
    console.log(data);
})
.catch(error => {
    // Handle any errors that occurred during the request
    console.error('Error:', error);
});


fetch('http://localhost:1080/api/accounts/*, {
    method: 'DELETE'
    headers: {

    },
    // Other options like body for POST requests
})
.then(response => response.json()) // Parse the response as JSON
.then(data => {
    // Handle the data received from the backend
    console.log(data);
})
.catch(error => {
    // Handle any errors that occurred during the request
    console.error('Error:', error);
});