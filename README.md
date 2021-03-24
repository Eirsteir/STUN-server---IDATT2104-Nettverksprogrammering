# STUN-server---IDATT2104-Nettverksprogrammering

### LAST CONTINIOUS INTEGRATION / DEPLOYMENT

- [STUN server Maven CI](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/actions/workflows/maven.yml)
- [STUN server CD](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/runs/2183152695)
- [Peer to peer client React CI/CD](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/actions/workflows/node-client.yml)

### INTRODUCTION

This is a STUN-server with P2P client application, created by:

- Eirik Steira

- Stian Mogen

- Nicolay Schiøll Johansen

The program is created for a project in the NTNU course IDATT2104. The team was tasked to create a STUN-server with a P2P client application.
We were not supposed to create a complete implementation of a STUN-server, but rather add functionality we saw fit, and also preparing it for further work in the future.
Implementation of the server needed to be inn accordance with [RFC 5389](https://tools.ietf.org/html/rfc5389), which we tried to consider throughout the entire development process.

### IMPLEMENTED FUNCTIONALITY

#### The STUN-server supports the following functionality:

### Receiving a STUN Message

The STUN server is able to receive STUN message on UDP ports 3478 and 3479. The checking of the message defined in [RFC 5389](https://tools.ietf.org/html/rfc5389) section 7.3
are not implemented, but are still there to show how we would have it done.

#### Processing a Request
 
The incoming request message is parsed in its entirety except for the attributes, which is saved for future work and improvements. This is done in [MessageParser](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/main/stun-server/src/main/java/com/nettverksprog/stun/message/MessageParser.java)

The header is parsed and the corresponding message class and method are checked. If this request is a Binding Request, the server forms a Binding Response. This is the currently 
the only message type the server is able to handle. 

#### Forming and Sending a Success Response

When the message is parsed and we are certain it is a Binding Request, the server forms a Binding Success Response with the attributes [MAPPED-ADDRESS](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/main/stun-server/src/main/java/com/nettverksprog/stun/attribute/MappedAddress.java) and [XOR-MAPPED-ADDRESS](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/main/stun-server/src/main/java/com/nettverksprog/stun/attribute/XorMappedAddress.java). 
The message response method is the same as the request, ie Binding and the message class is Success Response. For UDP, the content of these attributes are the source transport
address to the response.

The response is sent over the same transport protocol the request was received on, ie. UDP.

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

The message header is parsed with a [MessageHeaderParser](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/main/stun-server/src/main/java/com/nettverksprog/stun/header/MessageHeaderParser.java), which writes the contents of the message header, 
with the appropriate amount of bits, to our message header. It checks that the magic cookie has the correct value and that the message method and class are known to the server.


#### MessageClass and MessageMethod

The Message Type in the Message Header is split into two different sub-types. These are [Class](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/b0eace5ebc6196fe267e99451de225f0f58a70c2/stun-server/src/main/java/com/nettverksprog/stun/header/MessageClass.java) and [Method](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/b0eace5ebc6196fe267e99451de225f0f58a70c2/stun-server/src/main/java/com/nettverksprog/stun/header/MessageClass.java). The Message Method we use is simply Binding, while the Class can be either Request or Response. The Class is determined by what type of message is being sent.

#### Attributes

* Attribute Builder

There are several supported attributes for our implementation of the STUN Message. These different attributes are handled with our [Attribute Builer](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/b0eace5ebc6196fe267e99451de225f0f58a70c2/stun-server/src/main/java/com/nettverksprog/stun/attribute/AttributeBuilder.java) class, which seperates and interprets the attributes from a message. 

* MappedAddress and XorMappedAddres

Our implementation of the different Address attributes is done with an abstract class [Address](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/b0eace5ebc6196fe267e99451de225f0f58a70c2/stun-server/src/main/java/com/nettverksprog/stun/attribute/Address.java), with the sub-classes [XorMappedAddress]((https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/main/stun-server/src/main/java/com/nettverksprog/stun/attribute/MappedAddress.java)) and [MappedAddress](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/main/stun-server/src/main/java/com/nettverksprog/stun/attribute/XorMappedAddress.java). Both IPv4 and IPv6 are supported. 

These attributes are identical, except for the encoding of the source port and address.

* Attributes added, but not fully implemented

While the STUN server is not fully complete, the foundation for further work is set for implementation of the following attributes:
  - [Username](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/b0eace5ebc6196fe267e99451de225f0f58a70c2/stun-server/src/main/java/com/nettverksprog/stun/attribute/Username.java)
  - [Fingerprint](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/b0eace5ebc6196fe267e99451de225f0f58a70c2/stun-server/src/main/java/com/nettverksprog/stun/attribute/Fingerprint.java)
  - [MessageIntegrity](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/b0eace5ebc6196fe267e99451de225f0f58a70c2/stun-server/src/main/java/com/nettverksprog/stun/attribute/MessageIntegrity.java)
  - [Error-Code](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/blob/b0eace5ebc6196fe267e99451de225f0f58a70c2/stun-server/src/main/java/com/nettverksprog/stun/attribute/ErrorCode.java)

#### Exception Handling

The format of a STUN Message is critical, therefore checks, as well as exception class for format handling is implemented as a security measures. 

#### Multi-Threading

The server starts two threads upon startup, each for the respective port. 

#### STUN- and Signaling Server Docker Image

The servers are run locally with Docker Containers, as to provide extra security and seperation. Each service has its dedicated Docker Image. 

#### P2P Application with WebRTC

The P2P applications allows users to communicate with minimal latency, with quick peer to peer data transfer. 

#### Room Creation and Join Room Functionality

User are given the option to create their own unique room, with a shareable ID. These rooms can easily be joined with the join room functionality. 

### Azure Virtual Machine - STUN Server

The STUN server is hosted in the cloud on a Virtual Machine provided by Microsoft Azure. It is avalable for STUN messages at [stun:nettverksprogg.eastus2.cloudapp.azure.com:3478](https://nettverksprogg.eastus2.cloudapp.azure.com). 

#### Heroku Server Hosting - Signaling Server

The signaling server is hosted using Hekoru at https://signaling-server-nettprogg.herokuapp.com/. This allows users on seperate networks to communicate with each other with the P2P connection.

### Github Pages - P2P client

The Peer to Peer client is being hosted through Github Pages at https://eirsteir.github.io/STUN-server---IDATT2104-Nettverksprogrammering/.

#### Continous Integration

CI is set-up and runs on each commit or pull request to the main branch, so that future updates and improvements can be done to this project ensures the quality of the application.

#### Continous Deployment

Continous Deployment is integrated for both the P2P Client and the Signaling server. We attempted to setup CD for the STUN server to the Azure VM as well, but this proved to be 
problematic and we did not manage to finish this flow in time for the project deadline. 

### FUTURE WORK AND IMPROVEMENTS

#### SERVER

* More Attributes

We want to add support for more attributes in the STUN message. Currently we have implemented MappedAddress and XorMappedAddress, while Username is near completion. We decided that prioritizing an AttributeBuilder, which will allow anyone who wishes to continue work on this project in the future, with a great framework for adding more attributes. Attributes such as Fingerprint, Message-Integrity and Error-Code would be of high priority. We decided that the two former attributes where not quite relevant for our use-case and therefore was not 
prioritized, but should have been implemented if our time slot allowed for it.


* MessageReceiverValidator

While message validation upon receiving a STUN message is included, it is not yet completed in its implementation. Still, most of the checks required by [RFC 5389](https://tools.ietf.org/html/rfc5389) are present, but not functional (they are bypassed). 


* Parsing Request Attributes

Only the message header is parsed properly, but the attributes of the request should also be parsed to be able to properly form a response. 
This entails checking for unknown attributes and responding to authentication mechanisms. 

* Request Retransmissions 

Upon retransmission of a request, the server should reprosess the request and recompute the response only if the requests are idempotent. This is true for a Binding Request, which 
is the only request the server currently handles and this condition holds. If it received an authenticated request it should be stateful by remembering all transaction IDs and
the corresponding responses over the last 40 seconds, which is a future improvement of the server. 

* Processing an Indication

The processing of an indication is not supported by the server and should be implemented in the future to provide a more complete STUN server.

* Forming an Error Response

This is where the ERROR-CODE attribute comes into play, which should contain the error code resulting from the prosessing of the request. This might include adding other attributes,
such as an UNKNOWN-ATTRIBUTES attribute and certain attributes due to authentication error.

* Authentication and Message-Integrity Mechanisms

Both the long-term and short-term credential mechanisms described in [RFC 5389](https://tools.ietf.org/html/rfc5389) are optinal and we chose to rather prioritize other funcionality.
These require additional special processing. 


### EXTERNAL DEPENDENCIES

For this project some external dependencies where used:

- Lombok Library for generating boilerplate code for Java
- Spring Boot: Framework for creating back end programs. 
- React: JavaScript framework for creating responsive GUI. 
- socket.io: Library for real time Server-Web communication
- express: Node.js framework for web applications.

### TECHNOLOGIES 
- Docker: Containerizing framework.
- Hekoru: PaaS for hosting a variety of services.
- Node.js: JavaScript runtime
- Azure: Cloud computing platform and infrastructure

### INSTALLATION

To intall and run the project, follow the below steps.

```
// Clone and enter the repository 
git clone git@github.com:Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering.git
cd 'STUN-server---IDATT2104-Nettverksprogrammering'

// Run the project with GNU-MAKE
make start
```
This fires up the docker containers with docker-compose and runs the applications. 

### BUILD AND RUN THE STUN-SERVER AND CLIENT

If you want to run the server and client on your own computee, follow these instructions:

* Make sure Docker is running and you have docker-compose installed.
* Open the Command Prompt in the repository root. 
* Build and run the two servers in a docker image with the command "docker-compose up" or if you have GNU-make installed, run `make start`
* A new browser window should open at http://localhost:3000, from here you can create a new room and start chatting! 

### TESTING

The automated tests can be run by entering `make test` in the repository root in a command prompt. This runs the three containers which each of them running their own test suites. 

### API DOCUMENTATION

The API Documentation for the STUN server is not currently hosted anywhere, but JavaDoc is included in the [API code](https://github.com/Eirsteir/STUN-server---IDATT2104-Nettverksprogrammering/tree/b0eace5ebc6196fe267e99451de225f0f58a70c2/stun-server/src/main/java/com/nettverksprog/stun). 

### EXTERNAL SOURCES 

This project would not have been possible without the help from developers providing invaluable resources for our group to explore. 

[RFC 5389](https://tools.ietf.org/html/rfc5389#section-18.4) 

[Simple RTCDataChannel sample by Mozilla](https://developer.mozilla.org/en-US/docs/Web/API/WebRTC_API/Simple_RTCDataChannel_sample)

[What are STUN and TURN Servers by WebRTC.ventures](https://www.youtube.com/watch?v=4dLJmZOcWFc&ab_channel=WebRTC.ventures)

[STUN Protocol Explained by Nick Galea](https://developer.mozilla.org/en-US/docs/Web/API/WebRTC_API/Simple_RTCDataChannel_sample)

[Datakom for Dataingeniører, NAT](https://sites.google.com/view/tdat2004-datakom/nettverkslaget/nat?authuser=0)

[Universal Connection Establishment by htwg](https://github.com/htwg/UCE)

[Binary Arithmetic Shifts by MrBrownCS](https://www.youtube.com/watch?v=nm_laES9rKk&ab_channel=MrBrownCS)

[WebRTC Data Channel Tutorial by Coding With Chaim](https://www.youtube.com/watch?v=NBPDYco-alo&ab_channel=CodingWithChaim)

[Creating a React webRTC Video Chat Application by Coding With Chaim](https://www.youtube.com/watch?v=BpN6ZwFjbCY&ab_channel=CodingWithChaim)

[JSTUN by tking](https://github.com/tking/JSTUN)

[Simple STUN client in Java StackOverflow](https://stackoverflow.com/questions/27469398/simple-stun-client-in-java/27584193#27584193)

[P2P-chat-java by NateKong](https://github.com/NateKong/P2P-chat-java/blob/master/STUN.java)

[WebRTC samples Trickle ICE](https://webrtc.github.io/samples/src/content/peerconnection/trickle-ice/)

[Deploying Node.js](https://devcenter.heroku.com/articles/deploying-nodejs)

['Servers for WebRTC: It is not all Peer to Peer' by Kranky Geek](https://www.youtube.com/watch?v=Y1mx7cx6ckI&t=1711s)

[Deploy React App with React Router to Github Pages for Free](https://medium.com/@arijit_chowdhury/deploy-react-app-with-react-router-to-github-pages-for-free-569377f483f)

[Setting up a CI/CD workflow on GitHub Actions for a React App (with GitHub Pages and Codecov)](https://dev.to/dyarleniber/setting-up-a-ci-cd-workflow-on-github-actions-for-a-react-app-with-github-pages-and-codecov-4hnp)

[How To Create A Video Chat App With WebRTC](https://www.youtube.com/watch?v=DvlyzDZDEq4&t=1313s)

[Fun ways to animate CSS gradients](https://www.youtube.com/watch?v=f3mwKLXpOLk)
