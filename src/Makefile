JFLAGS = -g
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	GetRequestProcessor.java \
	HTTPMethodType.java \
	HTTPStatus.java \
	Message.java \
	Request.java \
	RequestProcessor.java \
	Response.java \
	WebServer.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
