package distributed;

// Import declarations generated by the SALSA compiler, do not modify.
import java.io.IOException;
import java.util.Vector;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;

import salsa.language.Actor;
import salsa.language.ActorReference;
import salsa.language.Message;
import salsa.language.RunTime;
import salsa.language.ServiceFactory;
import gc.WeakReference;
import salsa.language.Token;
import salsa.language.exceptions.*;
import salsa.language.exceptions.CurrentContinuationException;

import salsa.language.UniversalActor;

import salsa.naming.UAN;
import salsa.naming.UAL;
import salsa.naming.MalformedUALException;
import salsa.naming.MalformedUANException;

import salsa.resources.SystemService;

import salsa.resources.ActorService;

// End SALSA compiler generated import delcarations.

import java.io.*;
import java.util.*;

public class Node extends UniversalActor  implements ActorService {
	public static void main(String args[]) {
		UAN uan = null;
		UAL ual = null;
		if (System.getProperty("uan") != null) {
			uan = new UAN( System.getProperty("uan") );
			ServiceFactory.getTheater();
			RunTime.receivedUniversalActor();
		}
		if (System.getProperty("ual") != null) {
			ual = new UAL( System.getProperty("ual") );

			if (uan == null) {
				System.err.println("Actor Creation Error:");
				System.err.println("	uan: " + uan);
				System.err.println("	ual: " + ual);
				System.err.println("	Identifier: " + System.getProperty("identifier"));
				System.err.println("	Cannot specify an actor to have a ual at runtime without a uan.");
				System.err.println("	To give an actor a specific ual at runtime, use the identifier system property.");
				System.exit(0);
			}
			RunTime.receivedUniversalActor();
		}
		if (System.getProperty("identifier") != null) {
			if (ual != null) {
				System.err.println("Actor Creation Error:");
				System.err.println("	uan: " + uan);
				System.err.println("	ual: " + ual);
				System.err.println("	Identifier: " + System.getProperty("identifier"));
				System.err.println("	Cannot specify an identifier and a ual with system properties when creating an actor.");
				System.exit(0);
			}
			ual = new UAL( ServiceFactory.getTheater().getLocation() + System.getProperty("identifier"));
		}
		Node instance = (Node)new Node(uan, ual,null).construct();
		{
			Object[] _arguments = { args };
			instance.send( new Message(instance, instance, "act", _arguments, null, null) );
		}
	}

	public static ActorReference getReferenceByName(UAN uan)	{ return new Node(false, uan); }
	public static ActorReference getReferenceByName(String uan)	{ return Node.getReferenceByName(new UAN(uan)); }
	public static ActorReference getReferenceByLocation(UAL ual)	{ return new Node(false, ual); }

	public static ActorReference getReferenceByLocation(String ual)	{ return Node.getReferenceByLocation(new UAL(ual)); }
	public Node(boolean o, UAN __uan)	{ super(false,__uan); }
	public Node(boolean o, UAL __ual)	{ super(false,__ual); }

	public Node(UAN __uan,UniversalActor.State sourceActor)	{ this(__uan, null,null); }
	public Node(UAL __ual,UniversalActor.State sourceActor)	{ this(null, __ual,null); }
	public Node(UniversalActor.State sourceActor)		{ this(null, null,null);  }
	public Node()		{  }
	public Node(UAN __uan, UAL __ual,Object sourceActor) {
		if (__ual != null && !__ual.getLocation().equals(ServiceFactory.getTheater().getLocation())) {
			createRemotely(__uan, __ual, "distributed.Node");
		} else {
			State state = new State(__uan, __ual);
			state.updateSelf(this);
			ServiceFactory.getNaming().setEntry(state.getUAN(), state.getUAL(), state);
			if (getUAN() != null) ServiceFactory.getNaming().update(state.getUAN(), state.getUAL());
		}
	}

	public UniversalActor construct (String fileLine) {
		Object[] __arguments = { fileLine };
		this.send( new Message(this, this, "construct", __arguments, null, null) );
		return this;
	}

	public UniversalActor construct() {
		Object[] __arguments = { };
		this.send( new Message(this, this, "construct", __arguments, null, null) );
		return this;
	}

	public class State extends UniversalActor .State implements salsa.resources.ActorServiceState {
		public Node self;
		public void updateSelf(ActorReference actorReference) {
			((Node)actorReference).setUAL(getUAL());
			((Node)actorReference).setUAN(getUAN());
			self = new Node(false,getUAL());
			self.setUAN(getUAN());
			self.setUAL(getUAL());
			self.muteGC();
		}

		public State() {
			this(null, null);
		}

		public State(UAN __uan, UAL __ual) {
			super(__uan, __ual);
			addClassName( "distributed.Node$State" );
			addMethodsForClasses();
		}

		public void construct() {}

		public void process(Message message) {
			Method[] matches = getMatches(message.getMethodName());
			Object returnValue = null;
			Exception exception = null;

			if (matches != null) {
				if (!message.getMethodName().equals("die")) {activateArgsGC(message);}
				for (int i = 0; i < matches.length; i++) {
					try {
						if (matches[i].getParameterTypes().length != message.getArguments().length) continue;
						returnValue = matches[i].invoke(this, message.getArguments());
					} catch (Exception e) {
						if (e.getCause() instanceof CurrentContinuationException) {
							sendGeneratedMessages();
							return;
						} else if (e instanceof InvocationTargetException) {
							sendGeneratedMessages();
							exception = (Exception)e.getCause();
							break;
						} else {
							continue;
						}
					}
					sendGeneratedMessages();
					currentMessage.resolveContinuations(returnValue);
					return;
				}
			}

			System.err.println("Message processing exception:");
			if (message.getSource() != null) {
				System.err.println("\tSent by: " + message.getSource().toString());
			} else System.err.println("\tSent by: unknown");
			System.err.println("\tReceived by actor: " + toString());
			System.err.println("\tMessage: " + message.toString());
			if (exception == null) {
				if (matches == null) {
					System.err.println("\tNo methods with the same name found.");
					return;
				}
				System.err.println("\tDid not match any of the following: ");
				for (int i = 0; i < matches.length; i++) {
					System.err.print("\t\tMethod: " + matches[i].getName() + "( ");
					Class[] parTypes = matches[i].getParameterTypes();
					for (int j = 0; j < parTypes.length; j++) {
						System.err.print(parTypes[j].getName() + " ");
					}
					System.err.println(")");
				}
			} else {
				System.err.println("\tThrew exception: " + exception);
				exception.printStackTrace();
			}
		}

		int id;
		int priority;
		int tolerance;
		int ttl;
		int size;
		String port;
		String host;
		boolean canBeLeader;
		boolean hasBeenLeader;
		boolean currentLeader;
		boolean hasRevolted;
		Node left;
		Node right;
		void construct(String fileLine){
			String[] inputs = fileLine.split("\t");
			id = Integer.parseInt(inputs[0]);
			priority = Integer.parseInt(inputs[3]);
			tolerance = Integer.parseInt(inputs[4]);
			ttl = 1;
			size = 0;
			port = inputs[2];
			host = inputs[1];
			canBeLeader = true;
			hasBeenLeader = false;
			currentLeader = false;
			hasRevolted = false;
		}
		public void setLeft(Node toLeft) {
			left = toLeft;
			return;
		}
		public void setRight(Node toRight) {
			right = toRight;
			return;
		}
		public void setSize(int s) {
			size = s;
			return;
		}
		public void printStatusMessage(String mess) {
			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("output.txt", true)));
				out.println(mess);
				out.close();
			}
			catch (IOException ioe) {
				{
					// standardError<-println("[error] Can't open the file for writing.")
					{
						Object _arguments[] = { "[error] Can't open the file for writing." };
						Message message = new Message( self, standardError, "println", _arguments, null, null );
						__messages.add( message );
					}
				}
			}

		}
		public void reset(int counter) {
			if (counter<size) {{
				hasRevolted = false;
				ttl = 1;
				counter++;
				{
					// left<-reset(counter)
					{
						Object _arguments[] = { counter };
						Message message = new Message( self, left, "reset", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
}			return;
		}
		public void receiveMessage(int senderId, int senderPriority, boolean senderLeaderStatus, int tTL, int pastLeaders, int time, int localTime) {
			if (senderId==id&&canBeLeader) {{
				currentLeader = true;
				canBeLeader = false;
				{
					// printStatusMessage("ID="+Integer.toString(senderId)+" became leader at t="+Integer.toString(time))
					{
						Object _arguments[] = { "ID="+Integer.toString(senderId)+" became leader at t="+Integer.toString(time) };
						Message message = new Message( self, self, "printStatusMessage", _arguments, null, null );
						__messages.add( message );
					}
				}
				time++;
				{
					Token token_3_0 = new Token();
					// left<-reset(0)
					{
						Object _arguments[] = { new Integer(0) };
						Message message = new Message( self, left, "reset", _arguments, null, token_3_0 );
						__messages.add( message );
					}
					// left<-leaderTime(time, 0, pastLeaders, 0)
					{
						Object _arguments[] = { time, new Integer(0), pastLeaders, new Integer(0) };
						Message message = new Message( self, left, "leaderTime", _arguments, token_3_0, null );
						__messages.add( message );
					}
				}
			}
}			else {{
				tTL--;
				if (priority>=senderPriority&&canBeLeader) {{
					senderLeaderStatus = false;
				}
}				else {if (priority==senderPriority&&canBeLeader) {{
					if (id>senderId) {{
						senderLeaderStatus = false;
					}
}				}
}}				if (tTL==0) {{
					{
						// left<-replyMessage(id, senderId, senderLeaderStatus, pastLeaders, time, localTime)
						{
							Object _arguments[] = { id, senderId, senderLeaderStatus, pastLeaders, time, localTime };
							Message message = new Message( self, left, "replyMessage", _arguments, null, null );
							__messages.add( message );
						}
					}
				}
}				else {{
					{
						// left<-receiveMessage(senderId, senderPriority, senderLeaderStatus, tTL, pastLeaders, time, localTime)
						{
							Object _arguments[] = { senderId, senderPriority, senderLeaderStatus, tTL, pastLeaders, time, localTime };
							Message message = new Message( self, left, "receiveMessage", _arguments, null, null );
							__messages.add( message );
						}
					}
				}
}			}
}			return;
		}
		public void leaderTime(int time, int revolts, int pastLeaders, int localTime) {
			if (localTime>=tolerance&&currentLeader==false&&hasRevolted==false) {{
				hasRevolted = true;
				revolts++;
				{
					// printStatusMessage("ID="+Integer.toString(id)+" revolted at t="+Integer.toString(time))
					{
						Object _arguments[] = { "ID="+Integer.toString(id)+" revolted at t="+Integer.toString(time) };
						Message message = new Message( self, self, "printStatusMessage", _arguments, null, null );
						__messages.add( message );
					}
				}
				time++;
				localTime++;
				{
					// left<-leaderTime(time, revolts, pastLeaders, localTime)
					{
						Object _arguments[] = { time, revolts, pastLeaders, localTime };
						Message message = new Message( self, left, "leaderTime", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
}			else {if (revolts>=((size+1)/2)&&currentLeader==true) {{
				currentLeader = false;
				pastLeaders++;
				time++;
				localTime++;
				{
					// printStatusMessage("ID="+Integer.toString(id)+" was deposed at t="+Integer.toString(time-1))
					{
						Object _arguments[] = { "ID="+Integer.toString(id)+" was deposed at t="+Integer.toString(time-1) };
						Message message = new Message( self, self, "printStatusMessage", _arguments, null, null );
						__messages.add( message );
					}
				}
				{
					// left<-startElection(time, pastLeaders)
					{
						Object _arguments[] = { time, pastLeaders };
						Message message = new Message( self, left, "startElection", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
}			else {{
				localTime++;
				time++;
				{
					// left<-leaderTime(time, revolts, pastLeaders, localTime)
					{
						Object _arguments[] = { time, revolts, pastLeaders, localTime };
						Message message = new Message( self, left, "leaderTime", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
}}			return;
		}
		public void replyMessage(int newId, int senderId, boolean senderLeaderStatus, int pastLeaders, int time, int localTime) {
			if (id==senderId) {{
				if (senderLeaderStatus==true) {{
					ttl *= 2;
					{
						// left<-receiveMessage(id, priority, canBeLeader, ttl, pastLeaders, time, localTime)
						{
							Object _arguments[] = { id, priority, canBeLeader, ttl, pastLeaders, time, localTime };
							Message message = new Message( self, left, "receiveMessage", _arguments, null, null );
							__messages.add( message );
						}
					}
				}
}				else {{
					{
						// left<-startElection(time, pastLeaders)
						{
							Object _arguments[] = { time, pastLeaders };
							Message message = new Message( self, left, "startElection", _arguments, null, null );
							__messages.add( message );
						}
					}
				}
}			}
}			else {{
				{
					// left<-replyMessage(newId, senderId, senderLeaderStatus, pastLeaders, time, localTime)
					{
						Object _arguments[] = { newId, senderId, senderLeaderStatus, pastLeaders, time, localTime };
						Message message = new Message( self, left, "replyMessage", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
}			return;
		}
		public void startElection(int timestamp, int pastLeaders) {
			if (pastLeaders==size) {{
				{
					// printStatusMessage("End of simulation")
					{
						Object _arguments[] = { "End of simulation" };
						Message message = new Message( self, self, "printStatusMessage", _arguments, null, null );
						__messages.add( message );
					}
				}
				return;
			}
}			if (hasBeenLeader) {{
				{
					// left<-startElection(timestamp, pastLeaders)
					{
						Object _arguments[] = { timestamp, pastLeaders };
						Message message = new Message( self, left, "startElection", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
}			else {{
				{
					// left<-receiveMessage(id, priority, canBeLeader, ttl, pastLeaders, timestamp, 0)
					{
						Object _arguments[] = { id, priority, canBeLeader, ttl, pastLeaders, timestamp, new Integer(0) };
						Message message = new Message( self, left, "receiveMessage", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
}			return;
		}
	}
}