Networks Assignment 
CSC3002F
Nicol Vojacek
VJCNIC001
15 April 2016
===========================================================================

Running my Socket Programming code from Terminal: 

-> cd to the src folder 
-> make
-> java WebServer <port number> 
(leaving port number as null will default to 8080. Please use a number between 1023 and 60000) 
-> Go to browser and open Web Developer Tools- Web Console - Networks (or equivalent) to see request and response messages being send to the browser 
-> Enter localhost:<port number>//response.txt as a url
-> File will either be sent successfully if response.txt requested, or send 404 Not Found if wrong file name entered. 
-> make clean (in terminal)


