function editUser() {

    let form = window.formEditUser.editRoles;
    let new_Roles = [];

    let rolesList = document.createElement('ul');

    for (let i = 0; i < form.length; i++) {
        let option = form.options[i];
        let role = document.createElement('li');
        if (option.selected) {
            new_Roles[i] = option.value

            role.textContent = option.value.substring(5, 10) + " ";
            rolesList.appendChild(role);
        }
    }
    let json = JSON.stringify({
        id: window.formEditUser.editID.value,
        name: window.formEditUser.editLogin.value,
        age: window.formEditUser.editAge.value,
        password: window.formEditUser.editPassword.value,
        roles: new_Roles
    })
    console.log(json)
    let id = window.formEditUser.editID.value;

    fetch('http://localhost:8080/admin/users', {
        method: 'PUT',
        body: JSON.stringify({
            id: window.formEditUser.editID.value,
            login: window.formEditUser.editLogin.value,
            age: window.formEditUser.editAge.value,
            password: window.formEditUser.editPassword.value,
            roles: new_Roles
        }),
        headers: {"Content-type": "application/json; charset=UTF-8"}
    })
        .then(response => {
            $('#' + id).replaceWith('<tr id=' + id + '>' +
                '<td>' + id + '</td>' +
                '<td>' + window.formEditUser.editLogin.value + '</td>' +
                '<td>' + window.formEditUser.editAge.value + '</td>' +
                '<td>' + rolesList.textContent + '</td>' +
                '<td> <button type="button" onclick="getModalEdit(' + id + ')" class="btn btn-primary btn-sm">Edit</button> </td>' +
                '<td> <button type="button" onclick="getModalDelete(' + id + ')" class="btn btn-danger btn-sm">Delete</button> </td>' +
                '</tr>');
        });
}