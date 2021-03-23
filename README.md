# STUN-server---IDATT2104-Nettverksprogrammering

### LAST CONTINIOUS INTEGRATION / DEPLOYMENT

### INTRODUCTION

This is a STUN-server with P2P client application, created by:

Eirik Steira

Stian Mogen

Nicolay Schiøll Johansen

The program is created for a project in the NTNU course IDATT2104. The team was tasked to create a STUN-server with a P2P client application.
We were not supposed to create a complete implementation of a STUN-server, but rather add functionality we saw fit, and also preparing it for further work in the future.
Implementation of the server needed to be inn accordance with [RFC 5389](https://tools.ietf.org/html/rfc5389), which we tried to consider throughout the entire development process.

### IMPLEMENTED FUNCTIONALITY

#### The STUN-server supports the following functionality:
#### Message Header

The message header consists of the following contents with the size in bits in parenthesis:

	 0                   1                   2                   3
       0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 = 32 bits
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |0 0|    STUN Message Type (14) |       Message Length (16)     |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                      Magic Cookie (32)                        | 0x2112A442
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
      |                                                               |
      |                     Transaction ID (96 bits)                  |
      |                                                               |
      +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
The message header is parsed with a MessageHeaderParser, which writes the contents of the message header, 
with the appropriate amount of bits, to our message header.

#### MessageClass and MessageMethod

The Message Type in the Message Header is split into two different sub-types. These are Class and Method. The Message Method we use is simply Binding, while the Class can be either Request or Response. The Class is determined by what type of message is being sent.

#### Attributes

* Attribute Builder

There are several supported attributes for our implementation of the STUN Message. These different attributes are handled with our Attribute Builer class, which seperates and interprets the attributes from a message. 

* MappedAddress and XorMappedAddres

Our implementation of the different Address attributes is done with an abstract class Address, with the sub-classes XorMappedAddress and MappedAddress. Both IPv4 and IPv6 are supported. 

* Attributes added, but not fully implemented

While the STUN server is not fully complete, the foundation for further work is set for implementation of the following attributes:
  - Username
  - Fingerprint
  - MessageIntegrity
  - Error-Code

#### Exception Handling

The format of a STUN Message is critical, therefore checks, as well as exception class for format handling is implemented as a security message. 

#### Multi Threading

The server is set up as to give several users the oppurtunity to connect. Multi threading is therefore implemented, so that each client has an own dedicated thread. 

#### STUN- and Signaling Server Docker Image

The servers are run in Docker Images, as to provide extra security and seperation. 

#### P2P Application with WebRTC

The P2P applications allows users to communicate with minimal latency, with quick client to client data transfer. 

#### Room Creation

User are given the option to create their own unique room, with a shareable link.

#### Heroku Server Hosting

The servers are hosted using Hekoru. This allows users on seperate networks to communicate with each other with the P2P connection.

#### Continous Integration

CI and Deployment is set-up, so that future updates and improvements can be done to this project.


### FUTURE WORK AND IMPROVEMENTS

#### SERVER

* More Attributes

We want to add support for more attributes in the STUN message. Currently we have implemented MappedAddress and XorMappedAddress, while Username is near completion. We decided that prioritizing an AttributeBuilder, which will allow anyone who wishes to continue work on this project in the future, with a great framework for adding more attributes. Attributes such as Fingerprint, Message-Integrity and Error-Code would be of high priority.


* MessageReceiverValidator

While message validation is included, it is not yet completed in its implementation.

### EXTERNAL DEPENDENCIES

For this project some external dependencies where used:

* Docker
* Hekoru 
* Node.js

### INSTALLATION

### BUILD AND RUN THE STUN-SERVER AND CLIENT

### TESTING

### API DOCUMENTATION

### EXTERNAL SOURCES 

This project would not have been possible without the help from developers providing invaluable resources for our group to explore. 

[RFC 5389](https://tools.ietf.org/html/rfc5389#section-18.4) 

[Simple RTCDataChannel sample by Mozilla](https://developer.mozilla.org/en-US/docs/Web/API/WebRTC_API/Simple_RTCDataChannel_sample)

[What are STUN and TURN Servers by WebRTC.ventures](https://www.youtube.com/watch?v=4dLJmZOcWFc&ab_channel=WebRTC.ventures)

[STUN Protocol Explained by Nick Galea](https://developer.mozilla.org/en-US/docs/Web/API/WebRTC_API/Simple_RTCDataChannel_sample)

[Universal Connection Establishment by htwg](https://github.com/htwg/UCE)

[Binary Arithmetic Shifts by MrBrownCS](https://www.youtube.com/watch?v=nm_laES9rKk&ab_channel=MrBrownCS)

[WebRTC Data Channel Tutorial by Coding With Chaim](https://www.youtube.com/watch?v=NBPDYco-alo&ab_channel=CodingWithChaim)

[Creating a React webRTC Video Chat Application by Coding With Chaim](https://www.youtube.com/watch?v=BpN6ZwFjbCY&ab_channel=CodingWithChaim)

[JSTUN by tking](https://github.com/tking/JSTUN)

[Simple STUN client in Java StackOverflow](https://stackoverflow.com/questions/27469398/simple-stun-client-in-java/27584193#27584193)

[P2P-chat-java by NateKong](https://github.com/NateKong/P2P-chat-java/blob/master/STUN.java)

[How to Open Firewall Ports in Windows 10](https://www.tomshardware.com/news/how-to-open-firewall-ports-in-windows-10,36451.html)

[WebRTC samples Trickle ICE](https://webrtc.github.io/samples/src/content/peerconnection/trickle-ice/)

[Deploying Node.js](https://devcenter.heroku.com/articles/deploying-nodejs)





