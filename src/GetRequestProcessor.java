import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * A GetRequestProcessor contains the logic necessary for handling HTTP GET requests.
 * 
 * @author Stephan Jamieson
 * @version 16/02/2016
 */

public class GetRequestProcessor extends RequestProcessor {

	/**
	 * Create a new GetRequestProcessor</br>
	 * Calling <code>getRequestMethod()</code> on this object returns <code>HTTPMethodType.GET</code>.
	 */
	public GetRequestProcessor() {
		super(HTTPMethodType.GET);
	}

	/**
	 * Process a given HTTP GET Request message, returning the result in an HTTP Response message.</br>
	 */
	public Response process(final Request request) throws Exception {

		assert(this.canProcess(request.getMethodType()));
	
		//Create response object 
		Response rep = new Response(request.getHTTPVersion());

		//Retrieve requested file parse as a file 
		Path path = Paths.get("");
        String fs = File.separator;
		String file = path.toAbsolutePath().toString() + fs + "DATA" + request.getURI();
		File f = new File(file);

		//If the file exists, we create a response object as successful
		if (f.exists()) 
		{
			//Set status of response as successful
			rep.setStatus(HTTPStatus.OK); 
			
			//Create a byte array of the file input, and set it to the body of the response message
			byte[] byteFile = new byte[(int)f.length()];
			FileInputStream fis = new FileInputStream(f);
			BufferedInputStream b = new BufferedInputStream(fis);
			b.read(byteFile, 0, byteFile.length);
			b.close();
			rep.setBody(byteFile);
				   		     
			//Set header fields
		    DateFormat date = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
			rep.setHeaderField("Date", date.format(new Date()));
			rep.setHeaderField("Connection", request.getHeaderFieldValue("Connection"));
			rep.setHeaderField("Server", "VJCNIC001 Servers");
			rep.setHeaderField("Accept-Ranges", "byte");
			rep.setHeaderField("Content-Type", "text/html");
			rep.setHeaderField("Content-Length", "" + f.length());
			rep.setHeaderField("Last-Modified", "" + f.lastModified());
			rep.setHeaderField("Keep-Alive", "");
		}
		
		//If file request was unsuccessful, create a 404 Not Found Response Message
		else 
		{
			//Set status of response
			rep.setStatus(HTTPStatus.NOT_FOUND);

			//Set message body to display message in browser  
			String browserMsg = "File requested not found.";
			rep.setBody(browserMsg.getBytes());

			//Set header fields of unsuccessful response 
			SimpleDateFormat date = new SimpleDateFormat("EE, F MM y H:m:s zzz");
			rep.setHeaderField("Date", date.toString());
			rep.setHeaderField("Connection", request.getHeaderFieldValue("Connection"));
			rep.setHeaderField("Server", "VJCNIC001 Servers");
			rep.setHeaderField("Accept-Ranges", "byte");
			rep.setHeaderField("Content-Type", "text/html; charset=UTF-8");
			rep.setHeaderField("Character-Encoding", "gzip");
			int browserMsgLength = browserMsg.length();
			rep.setHeaderField("Content-Length", Integer.toString(browserMsgLength));
			rep.setHeaderField("Keep-Alive", "timeout=2, max=100");
			rep.setHeaderField("Vary", "accept-language,accept-charset,Accept-Encoding");
		}      
		return rep;
	}
}