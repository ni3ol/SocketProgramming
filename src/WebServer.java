import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.io.OutputStream;
/**
 * Simple Web Server.
 * 
 * The server opens a TCP port and listens on that port for HTTP requests.
 * The server accepts a port number as an optional parameter.</br>
 * If no parameter is given then it requests one be randomly assigned when
 * opening the TCP server socket.</br>
 * In all cases, the server prints out the port that it is using.
 * 
 * 
 * @author Stephan Jamieson
 * @version 16/02/2016
 */
public class WebServer {

    private WebServer() {}
    /**
     * Run the web server. The server accepts a port number as an optional parameter.</br>
     * If no parameter is given then it requests one be randomly assigned when opening the TCP server socket.</br>
     * In all cases, the server prints out the port that it is using.
     */
    public static void main(String argv[]) throws Exception {

    	//server socket being created
    	ServerSocket serverSocket = null; 
    	
    	//client socket created to communicate with client. Server listens and accepts connections. 
    	Socket clientSocket = null; 

    	// Get the port number from the command line.
    	int port = 8080;
    	if (argv.length > 0) {
    		try {         
    			port = argv.length>0 ?(new Integer(argv[0])).intValue():0;
    		}
    		catch (ArrayIndexOutOfBoundsException e) {
    			port = 8080;
    			//System.out.println(e);
    		}
    		catch (NumberFormatException e) {
    			port = 8080;
    			//System.out.println(e);
    		}
    	}

    	//create new server socket with port number provided
    	serverSocket = new ServerSocket(port); 
    	System.out.println("Server is listening for request on port " + port);

    	//client socket created. Server client listening for connection
    	clientSocket =serverSocket.accept(); 
    	System.out.println("A connection was estasblished on port " + port + ".");
    	
    	//Create inputstream to get request from client
    	InputStreamReader input = new InputStreamReader(clientSocket.getInputStream());
    	
    	//Create output stream to send response to client
    	OutputStream out = new DataOutputStream(clientSocket.getOutputStream()); 
    	
    	//Make request object with input received from inputstream
    	Request r = Request.parse(input);
    	
    	//Create a response object
    	GetRequestProcessor g = new GetRequestProcessor();
    	Response rep = g.process(r);
    	
    	//Send response to the outputstream
    	Response.send(out, rep);
    	
    	//Close the socket
    	serverSocket.close();             
    }
}
