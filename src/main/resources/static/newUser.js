function newUser() {
    let form = window.formNewUser.newRoles;
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

    fetch('http://localhost:8080/admin/users', {
        method: 'POST',
        body: JSON.stringify({
            login: window.formNewUser.newLogin.value,
            password: window.formNewUser.newPassword.value,
            age: window.formNewUser.newAge.value,
            roles: new_Roles
        }),
        headers: {"Content-type": "application/json; charset=UTF-8"}
    })
        .then(response => response.json())
        .then(user => {
            $('#tBody tr:last').after('<tr id=' + user.id + '>' +
                '<td>' + user.id + '</td>' +
                '<td>' + window.formNewUser.newLogin.value + '</td>' +
                '<td>' + window.formNewUser.newAge.value + '</td>' +
                '<td>' + rolesList.textContent + '</td>' +
                '<td> <button type="button" onclick="getModalEdit(' + user.id + ')" class="btn btn-primary btn-sm">Edit</button> </td>' +
                '<td> <button type="button" onclick="getModalDelete(' + user.id + ')" class="btn btn-danger btn-sm">Delete</button> </td>' +
                '</tr>');

            window.formNewUser.newLogin.value = "";
            window.formNewUser.newAge.value = "";
            window.formNewUser.newPassword.value = "";
            window.formNewUser.newRoles.value = "";

            $('.nav-tabs a[href="#users_table"]').tab('show');
        });
}