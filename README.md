# Real-Time Chat API
A powerful and efficient real-time chat API built using websockets, allowing for instant and seamless communication between users.

## Features

- Real-time communication using websockets
- Support for creating customized chat rooms
- Easy to integrate with any front-end framework

# Run Locally
 Clone the project
```bash
$ git clone https://github.com/Frankester/chat-realtime.git
```
Go to the project directory
```bash
$ cd Chat-Realtime
```
Start with docker compose
```bash
$ docker-compose up
```
The API should now be up and running at http://localhost:8080

# API Reference

### Create a User account
- URL: `/auth/sign-up`
- Method: `POST`
- Auth required: `NO`

Example of body request:
```json
{
  "username": "UserAwesome",
  "password": "yourpass1234",
  "email": "your-email@gmail.com"
}
````

### Login
- URL: `/auth/login`
- Method: `POST`
- Auth required: `NO`

Example of body request:
```json
{
  "username": "UserAwesome",
  "password": "yourpass1234"
}
```
Example of response: 
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmcmFua2VzdGVyIiwiaWF0IjoxNjc1OTg3MzI5LCJleHAiOjE2NzYwNzM3Mjl9.pnPAO75dbVqagvsycNvHP40ocllFc-7hDQhejuE7RAs"
}
```

### Create a Room
- URL: `/rooms`
- Method: `POST`
- Auth required: `YES`
- Auth Method: ``Bearer``

Example of body request:
```json
{
  "nombre": "awesomeRoom"
}
```
__Don't forget to set the Authorization header with the ``Bearer JWT``__

Example of response:
```json
{
  "id": 1,
  "usuarios": [
    {
      "username": "UserAwesome",
      "email": "your-email@gmail.com"
    }
  ],
  "mensajes": [],
  "nombre": "awesomeRoom",
  "administrador": {
    "username": "UserAwesome",
    "email": "your-email@gmail.com"
  }
}
```

### Delete a Room
- URL: `/rooms/{idRoom}`
- Method: `DELETE`
- Auth required: `YES`
- Auth Method: ``Bearer``


### Find a Room
Find a Room by Room's Name
- URL: `/rooms?findByNombre={roomName}`
- Method: `GET`
- Auth required: `YES`
- Auth Method: ``Bearer``

Example of response (at ```/rooms?findByNombre=awesomeRoom``` )
```json
[
  {
    "id": 1,
    "usuarios": [
      {
        "username": "UserAwesome",
        "email": "your-email@gmail.com"
      }
    ],
    "mensajes": [],
    "nombre": "awesomeRoom",
    "administrador": {
      "username": "UserAwesome",
      "email": "your-email@gmail.com"
    }
  }
]
```

## Using WebSockets
- Connect to: ``ws://localhost:8080/wsrooms/{server-id}/{session-id}/websocket``
  - ``server-id`` and `session-id` are created by [SockJsClient](https://github.com/sockjs/sockjs-client)
  - Otherwise, you can invent them like: ``server-id: 487`` and `session-id: fdsafdsa` for testing purposes.
- [STOMP](https://stomp.github.io/stomp-specification-1.2.html#Abstract) is used for the messaging protocol.
  - example to subscribe a user: ```["SUBSCRIBE\nid:sub-0\ndestination:/user/UserAwesome/queue/rooms\n\n\u0000"]```
  - you can use [STOMP.js](https://github.com/stomp-js/stompjs) library for generate your messages. 

Supported messages content:
###Join the Room:
```json
  {
    "sender":  "UserAwesome",
    "type": "JOIN"
  }   
 ```

### Leave the Room:
```json
  {
    "sender":  "UserAwesome",
    "type": "LEAVE"
  }   
```

### Send a message:
```json
  {
    "content": "Hello!!",
    "sender":  "UserAwesome",
    "type": "CHAT"
  }   
```

# Built With

**Database:** Mysql

**Spring Boot Starters:**  Data REST, Security, Data JPA, WebSockets

**Authentication**: [JsonWebToken](https://github.com/jwtk/jjwt)

