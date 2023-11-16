showAllUsers();

function showAllUsers() {
    let tBody = document.getElementById("tBody");
    tBody.innerHTML = "";
    fetch('http://localhost:8080/admin/users')
        .then(response => response.json())
        .then(users => {
            users.forEach(function (user) {
                let row = tBody.insertRow(0);
                row.id = user.id
                let cell0 = row.insertCell(0);
                cell0.innerHTML = user.id;
                let cell1 = row.insertCell(1);
                cell1.innerHTML = user.login;
                let cell2 = row.insertCell(2);
                cell2.innerHTML = user.age;
                let cell3 = row.insertCell(3);
                cell3.innerHTML = listRoles(user).textContent;

                let cell4 = row.insertCell();
                cell4.innerHTML =
                    '<button type="button" onclick="getModalEdit(' + user.id + ')" class="btn btn-primary btn-sm">Edit</button>';

                let cell5 = row.insertCell();
                cell5.innerHTML =
                    '<button type="button" onclick="getModalDelete(' + user.id + ')" class="btn btn-danger btn-sm">Delete</button>';
            })
        });
    console.log(tBody);
}