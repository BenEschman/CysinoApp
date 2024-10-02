Adding user:

send to http://.../users/create,
In body put"
{
    "firstName": "...",
    "lastName": "...",
    "phoneNumber": "..."
}
"
When creating user, connect it to login.
send a request to http://.../login/setUser/{username} with the request body of the user you are connecting


To create login:

send to http://.../signup/register,
In body put"
{
    "username": "...",
    "password": "..."
}
"


