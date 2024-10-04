A user is automatically created when a user signsup.
To signup:

send a post to http://.../signup/register,
In body put"
{
    "username": "...",
    "password": "..."
}
"
Will send back a long in string form to serve as an ID.

Adding user info:

send a put to http://.../users/create/{id},
In body put"
{
    "firstName": "...",
    "lastName": "...",
    "phoneNumber": "..."
}
"

To login:
send to http://.../login/submit
In body put a string[] with {username, password}.
Important!! Will return a string that needs to be acknoledged. The string will be three possibilities:
1. "Wrong password"
2. "Wrong username"
3. The long id in form of a string. Parse this long.

To delete a User:

Send a delete to http://.../login/delete/{id}

To update user info do the same as adding user info.

To get a User:

send a Get to http://.../users/{id}

To get all users:

send a Get to http://.../users
returns a list of users

To get login:

Send a Get to http://.../login/{id}


