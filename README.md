Welcome to the SocketWhisper. SocketWhisper is software written for fun and experimentation to find out the 
capabilities of Raspberry Pi and Java programming language. The SocketWhisper uses a Raspberry pi to act as 
a server in a server-client relationship model. Ideally, clients should be personal PCs, although there are 
plans to utilize small Android phones that can act as both server and client in the future. The idea of a 
Raspberry Pi as server is this: it knows things by utilizing an embedded Sqlite database that tells jokes 
and random historical facts. It does things such as request weather from nearly any place in the world and 
tells you more about the weather than you thought you'd ever need to know. Raspberry Pi as a server also has 
functions to request freely available photos from NASA, telling stories and showing photos of the known and 
captivating universe. 

****
##Raspberry Pi as server

Let's go through the server/client model and quickly understand how SocketWhisper is implemented.
1. #####<u>Raspberry Pi - Server</u> : 
   The Raspberry Pi as server utilizes the java.net package which comes fully
   loaded with classes for implementing low-level network applications. If you're 
   like me and never implemented or read-up on network applications before this, 
   here's the quick run-down on servers. Simply put; a server is a piece of computer
   hardware or software that provides functionality for programs or devices, called
   clients. In the case of SocketWhisper, our server is powered by software written 
   in Java running on a Raspberry Pi. We'll get to how this is accomplished soon, 
   after we run through the role of a client. Think about servers running games like
   Minecraft, now scale down. A lot. The Raspberry Pi as a server has a major 
   function: to listen for clients, on a dedicated port, requesting to use its services 
   and resources. Thanks to the creators and many contributors of Java, we have classes
   that we can tell to do these things. In Java, we can create a ServerSocket object and
   use its listen() function to block until it receives a request from a client. The 
   ServerSocket class also provides methods to timeout a server if it hasn't received
   any request in some amount of time, and much more. Java takes care of a-lot once 
   the ServerSocket has received a request from a client; fully vetting a client before
   it is granted access to the Raspberry Pi resources. Once the connection is created
   communication can begin, almost. Next we'll go over the role of client. Last remarks, 
   Java does include security measures such as availability to implement secure network 
   communication via encryption. Other security measures include SocketPermission class
   which can be found in java.security package, as well as SecurityManager found in 
   java.lang package and much more. As SocketWhisper is continually being built, the 
   goal is to implement secure features. 
   
2. #####<u>Client - Node</u> : 
   The ClientNode, much like the Raspberry Pi Server, implements many of the same low-level 
   methods from java.net. A client is, essentially, anything such as another computer or 
   program that requests access to resources. In these beginning stages you can consider a 
   PC loaded with SocketWhisper to be client. Here's what you should know: A client node 
   utilizes a Socket supplied by java.net. This Socket must know two things to communicate 
   and grab resources from a server. First, the Host Name of server (it's ip address). 
   Second, which port the server is listening on. Once these building blocks are in place, the 
   last thing to do is request something from the server. Currently, clients connected to 
   Raspberry Pi server will be supplied a prompt which allows a client to choose from a list
   of actions to take. Some options are: random jokes, weather requests, NASA photos and an 
   echo playback where server repeats client. As SocketWhisper grows, so will client and server
   interaction. 
   
3. #####<u>Server-Client Model</u> : 
   
   
