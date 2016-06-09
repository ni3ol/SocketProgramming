import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
/**
* Represents an HTTP Request type of message.
* A Request message has a start line comprising HTTP method type, universal 
* resource identifier (URI), and HTTP version.</br>
* It may contain contain header fields, and may have a body.
* 
* 
* 
*/
public class Request extends Message {

    private HTTPMethodType method;
    private String uri;
    private String HTTP_version;
    private byte[] body;
    
    public Request() { super(); }
    
    /**
     * Create a Request message with a request-line composed of the given method type, URI and HTTP version.
     */
    public Request(final HTTPMethodType method, final String uri, final String HTTP_version) {
        super();
        this.method=method;
        this.uri=uri;
        this.HTTP_version=HTTP_version;
    }
        
    /**
     * Determine whether this request has a message body.
     */
    public boolean hasMessageBody() { return body!=null; }
    
    /**
     * Obtain the message body.</br>
     * Requires that <code>this.hasMessageBody()</code>.
     */
    public byte[] getBody() { return body; }
    
    /**
     * Obtain the request method type.
     */
    public HTTPMethodType getMethodType() { return this.method; }
    
    /**
     * Obtain the requested URI.
     */
    public String getURI() { return this.uri; }
    
    /**
     * Obtain the message http version.
     */
    public String getHTTPVersion() { return this.HTTP_version; }
    
    /**
     * Obtain the message request line i.e. <code>this.getMethodType()+" "+this.getURI()+" "+this.getHTTPVersion()</code>
     */
    public String getStartLine() {   
        return this.getMethodType()+" "+this.getURI()+" "+this.getHTTPVersion(); 
    }
        
    /**
     * Read an HTTP request from the given input stream and return it as a Request object.
     */
    public static Request parse(final InputStreamReader input) throws IOException 
    
    {	
    	//Read first line of inputstream as start line for request object
        BufferedReader output = new BufferedReader(input);
        String line = output.readLine();      
    
        //Create a request object using the startline of the request object
        String[] header = line.split(" ");
        HTTPMethodType h;
        h = HTTPMethodType.valueOf(header[0]);
        Request request = new Request(h, header[1], header[2]);
        line = output.readLine();
       
        //Read all following header lines into request object    
        while(!line.isEmpty()) 
        {
        	String[] s = line.split(": ", 2);
        	request.setHeaderField(s[0], s[1]);
        	line = output.readLine();
        }
            
        return request;       
    }
}