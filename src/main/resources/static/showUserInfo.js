showUserInfo();

function showUserInfo(user) {
    fetch('http://localhost:8080/info')
        .then(response => response.json())
        .then(user => {
            let tBody = document.getElementById("user_info");
            tBody.innerHTML = "";

            let row = tBody.insertRow(0);

            let cell0 = row.insertCell(0);
            cell0.innerHTML = user.id;
            let cell1 = row.insertCell(1);
            cell1.innerHTML = user.login;
            let cell2 = row.insertCell(2);
            cell2.innerHTML = user.age;
            let cell3 = row.insertCell(3);
            cell3.innerHTML = listRoles(user).textContent;
        });
}

function listRoles(user) {
    let rolesList = document.createElement('ul');
    for (let i = 0; i < user.roles.length; i++) {

        console.log(user.roles[i]);
        let role = document.createElement('li');
        role.textContent = user.roles[i].substring(5,10) + " ";
        rolesList.appendChild(role);
    }

    return rolesList;
}
