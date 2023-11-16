showHeader();

function showHeader() {
    fetch('http://localhost:8080/info')
        .then(response => response.json())
        .then(user => {

            document.getElementById("header_email").innerHTML = user.login;

            document.getElementById("header_roles").innerHTML = 'with roles: ' + listRoles(user).textContent;
        });
}