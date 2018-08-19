#RoomService
The main server code for the RoomService project.

##Goal
The goal is to create a internet-of-things application that runs on multiple Raspberry Pi's to control certain aspects of the room environment based on what the user is currently doing.

Stage one of the project is to create the core application and create all the little application to work around it. Every part connected will have it's own application created from a template to do exactly what that component has to do (Think a light controller, music controller, etc).

Stage two is to create simple interfaces to control all the little applications. This will include things such as a simple program to run on a computer that reads current activity, and adjust the room atmosphere accordingly.

Stage three is to expand it further depending on how well the previous stages work. Think Amazon Alexa support, Android support.

##Architecture
It uses simple client-server architecture. The client can currently request to either be able to control, listen or both. In the future this will be moved to an authentication system with roles built-in.

More technical explanation will follow.

##To do
- RSA encryption on the connection
- Authentication system
- Simplify adding of packets
- Logging
- Live status

##Updates
18/08/2018 | Updated to version V1.0.2 of the Network Core.
