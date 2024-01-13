document.getElementById('addNewUser').addEventListener('click', createUser)

async function createUser() {
    const inputName = document.getElementById('newName');
    const inputLastName = document.getElementById('newLastName');
    const inputAge = document.getElementById('newAge');
    const inputPassword = document.getElementById('newPassword');

    const name = inputName.value;
    const lastName = inputLastName.value;
    const age = inputAge.value;
    const password = inputPassword.value;
    let listRoles = roleArray(document.getElementById('newRoles')).map(role => ({
        ...role,
        name: `ROLE_${role.name}`,
    }));

    if (name && lastName && age && password && (listRoles.length !== 0)) {
        let res = await fetch("http://localhost:8080/api", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({name, lastName, age, password, roles: listRoles})
        });
        const result = await res.json();
        addUser(result);
    }
    inputName.value = ''
    inputLastName.value = ''
    inputAge.value = ''
    inputPassword.value = ''
}

let roleArray = (options) => {
    let array = []
    for (let i = 0; i < options.length; i++) {
        if (options[i].selected) {
            let role = {
                id: options[i].value,
                name: options[i].text,
            }
            array.push(role)
        }
    }
    return array
}