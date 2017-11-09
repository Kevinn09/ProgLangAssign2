package concurrent;

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

public class Node extends UniversalActor  {
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
		RunTime.receivedMessage();
		Node instance = (Node)new Node(uan, ual,null).construct();
		gc.WeakReference instanceRef=new gc.WeakReference(uan,ual);
		{
			Object[] _arguments = { args };

			//preAct() for local actor creation
			//act() for remote actor creation
			if (ual != null && !ual.getLocation().equals(ServiceFactory.getTheater().getLocation())) {instance.send( new Message(instanceRef, instanceRef, "act", _arguments, false) );}
			else {instance.send( new Message(instanceRef, instanceRef, "preAct", _arguments, false) );}
		}
		RunTime.finishedProcessingMessage();
	}

	public static ActorReference getReferenceByName(UAN uan)	{ return new Node(false, uan); }
	public static ActorReference getReferenceByName(String uan)	{ return Node.getReferenceByName(new UAN(uan)); }
	public static ActorReference getReferenceByLocation(UAL ual)	{ return new Node(false, ual); }

	public static ActorReference getReferenceByLocation(String ual)	{ return Node.getReferenceByLocation(new UAL(ual)); }
	public Node(boolean o, UAN __uan)	{ super(false,__uan); }
	public Node(boolean o, UAL __ual)	{ super(false,__ual); }
	public Node(UAN __uan,UniversalActor.State sourceActor)	{ this(__uan, null, sourceActor); }
	public Node(UAL __ual,UniversalActor.State sourceActor)	{ this(null, __ual, sourceActor); }
	public Node(UniversalActor.State sourceActor)		{ this(null, null, sourceActor);  }
	public Node()		{  }
	public Node(UAN __uan, UAL __ual, Object obj) {
		//decide the type of sourceActor
		//if obj is null, the actor must be the startup actor.
		//if obj is an actorReference, this actor is created by a remote actor

		if (obj instanceof UniversalActor.State || obj==null) {
			  UniversalActor.State sourceActor;
			  if (obj!=null) { sourceActor=(UniversalActor.State) obj;}
			  else {sourceActor=null;}

			  //remote creation message sent to a remote system service.
			  if (__ual != null && !__ual.getLocation().equals(ServiceFactory.getTheater().getLocation())) {
			    WeakReference sourceRef;
			    if (sourceActor!=null && sourceActor.getUAL() != null) {sourceRef = new WeakReference(sourceActor.getUAN(),sourceActor.getUAL());}
			    else {sourceRef = null;}
			    if (sourceActor != null) {
			      if (__uan != null) {sourceActor.getActorMemory().getForwardList().putReference(__uan);}
			      else if (__ual!=null) {sourceActor.getActorMemory().getForwardList().putReference(__ual);}

			      //update the source of this actor reference
			      setSource(sourceActor.getUAN(), sourceActor.getUAL());
			      activateGC();
			    }
			    createRemotely(__uan, __ual, "concurrent.Node", sourceRef);
			  }

			  // local creation
			  else {
			    State state = new State(__uan, __ual);

			    //assume the reference is weak
			    muteGC();

			    //the source actor is  the startup actor
			    if (sourceActor == null) {
			      state.getActorMemory().getInverseList().putInverseReference("rmsp://me");
			    }

			    //the souce actor is a normal actor
			    else if (sourceActor instanceof UniversalActor.State) {

			      // this reference is part of garbage collection
			      activateGC();

			      //update the source of this actor reference
			      setSource(sourceActor.getUAN(), sourceActor.getUAL());

			      /* Garbage collection registration:
			       * register 'this reference' in sourceActor's forward list @
			       * register 'this reference' in the forward acquaintance's inverse list
			       */
			      String inverseRefString=null;
			      if (sourceActor.getUAN()!=null) {inverseRefString=sourceActor.getUAN().toString();}
			      else if (sourceActor.getUAL()!=null) {inverseRefString=sourceActor.getUAL().toString();}
			      if (__uan != null) {sourceActor.getActorMemory().getForwardList().putReference(__uan);}
			      else if (__ual != null) {sourceActor.getActorMemory().getForwardList().putReference(__ual);}
			      else {sourceActor.getActorMemory().getForwardList().putReference(state.getUAL());}

			      //put the inverse reference information in the actormemory
			      if (inverseRefString!=null) state.getActorMemory().getInverseList().putInverseReference(inverseRefString);
			    }
			    state.updateSelf(this);
			    ServiceFactory.getNaming().setEntry(state.getUAN(), state.getUAL(), state);
			    if (getUAN() != null) ServiceFactory.getNaming().update(state.getUAN(), state.getUAL());
			  }
		}

		//creation invoked by a remote message
		else if (obj instanceof ActorReference) {
			  ActorReference sourceRef= (ActorReference) obj;
			  State state = new State(__uan, __ual);
			  muteGC();
			  state.getActorMemory().getInverseList().putInverseReference("rmsp://me");
			  if (sourceRef.getUAN() != null) {state.getActorMemory().getInverseList().putInverseReference(sourceRef.getUAN());}
			  else if (sourceRef.getUAL() != null) {state.getActorMemory().getInverseList().putInverseReference(sourceRef.getUAL());}
			  state.updateSelf(this);
			  ServiceFactory.getNaming().setEntry(state.getUAN(), state.getUAL(),state);
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

	public class State extends UniversalActor .State {
		public Node self;
		public void updateSelf(ActorReference actorReference) {
			((Node)actorReference).setUAL(getUAL());
			((Node)actorReference).setUAN(getUAN());
			self = new Node(false,getUAL());
			self.setUAN(getUAN());
			self.setUAL(getUAL());
			self.activateGC();
		}

		public State() {
			this(null, null);
		}

		public State(UAN __uan, UAL __ual) {
			super(__uan, __ual);
			addClassName( "concurrent.Node$State" );
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
		int time1;
		int numPastLeaders;
		int wantLeader;
		int currentLeaderNum;
		boolean canBeLeader;
		boolean tempCanBeLeader;
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
			time1 = 0;
			numPastLeaders = 0;
			canBeLeader = true;
			tempCanBeLeader = true;
			hasBeenLeader = false;
			currentLeader = false;
			hasRevolted = false;
			wantLeader = -1;
			currentLeaderNum = -1;
		}
		public void update(int newTime, int counter, int newLeaders) {
			if (counter<size) {{
				time1 = newTime;
				numPastLeaders = newLeaders;
				counter++;
				{
					// left<-update(newTime, counter, newLeaders)
					{
						Object _arguments[] = { newTime, counter, newLeaders };
						Message message = new Message( self, left, "update", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
}			return;
		}
		public int getTime() {
			{
				// standardOutput<-print("getting time:")
				{
					Object _arguments[] = { "getting time:" };
					Message message = new Message( self, standardOutput, "print", _arguments, null, null );
					__messages.add( message );
				}
			}
			{
				// standardOutput<-println(time1)
				{
					Object _arguments[] = { time1 };
					Message message = new Message( self, standardOutput, "println", _arguments, null, null );
					__messages.add( message );
				}
			}
			return time1;
		}
		public int getNumLeaders() {
			return numPastLeaders;
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
		public int ret() {
			return -1;
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
		public void leaderChosen(boolean chosen, int counter, int i) {
			if (counter<size) {{
				if (chosen==true) {{
					tempCanBeLeader = false;
					currentLeaderNum = i;
				}
}				else {{
					tempCanBeLeader = true;
					currentLeaderNum = -1;
				}
}				counter++;
				{
					// left<-leaderChosen(chosen, counter, i)
					{
						Object _arguments[] = { chosen, counter, i };
						Message message = new Message( self, left, "leaderChosen", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
}			return;
		}
		public int getWant() {
			return this.wantLeader;
		}
		public int getPrior() {
			return this.priority;
		}
		public void setleader() {
			currentLeader = true;
			canBeLeader = false;
			hasBeenLeader = true;
		}
		public int receiveMessage(int senderId, int senderPriority, boolean senderLeaderStatus, int tTL, int pastLeaders, int time, int localTime, boolean direction) {
			if (senderId==id&&senderLeaderStatus&&currentLeader==false) {{
				currentLeader = true;
				canBeLeader = false;
				hasBeenLeader = true;
				wantLeader = id;
				{
					// printStatusMessage("ID="+Integer.toString(senderId)+" became leader at t="+Integer.toString(time))
					{
						Object _arguments[] = { "ID="+Integer.toString(senderId)+" became leader at t="+Integer.toString(time) };
						Message message = new Message( self, self, "printStatusMessage", _arguments, null, null );
						__messages.add( message );
					}
				}
				time++;
				time1 = time;
				{
					Token token_3_0 = new Token();
					// left<-leaderChosen(true, 0, id)
					{
						Object _arguments[] = { true, new Integer(0), id };
						Message message = new Message( self, left, "leaderChosen", _arguments, null, token_3_0 );
						__messages.add( message );
					}
					// left<-reset(0)
					{
						Object _arguments[] = { new Integer(0) };
						Message message = new Message( self, left, "reset", _arguments, token_3_0, null );
						__messages.add( message );
					}
				}
				return id;
			}
}			else {{
				tTL--;
				if (priority>=senderPriority) {{
					senderLeaderStatus = false;
				}
}				else {if (priority==senderPriority) {{
					if (id>senderId) {{
						senderLeaderStatus = false;
					}
}				}
}}				if (hasBeenLeader==true) {{
					senderLeaderStatus = false;
				}
}				if (tTL==0) {{
					{
						// left<-replyMessage(id, senderId, senderLeaderStatus, pastLeaders, time, localTime, direction)
						{
							Object _arguments[] = { id, senderId, senderLeaderStatus, pastLeaders, time, localTime, direction };
							Message message = new Message( self, left, "replyMessage", _arguments, null, null );
							__messages.add( message );
						}
					}
				}
}				else {if (tempCanBeLeader) {{
					{
						// left<-receiveMessage(senderId, senderPriority, senderLeaderStatus, tTL, pastLeaders, time, localTime, direction)
						{
							Object _arguments[] = { senderId, senderPriority, senderLeaderStatus, tTL, pastLeaders, time, localTime, direction };
							Message message = new Message( self, left, "receiveMessage", _arguments, null, null );
							__messages.add( message );
						}
					}
				}
}}			}
}			return -1;
		}
		public int leaderTime(int time, int revolts, int pastLeaders, int localTime, int l) {
			if (localTime>=tolerance&&id!=l&&hasRevolted==false&&tempCanBeLeader==false) {{
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
					// left<-leaderTime(time, revolts, pastLeaders, localTime, l)
					{
						Object _arguments[] = { time, revolts, pastLeaders, localTime, l };
						Message message = new Message( self, left, "leaderTime", _arguments, null, null );
						__messages.add( message );
					}
				}
				{
					// ret()
					{
						Object _arguments[] = {  };
						Message message = new Message( self, self, "ret", _arguments, null, currentMessage.getContinuationToken() );
						__messages.add( message );
					}
					throw new CurrentContinuationException();
				}
			}
}			else {if (revolts>=((size+1)/2)&&currentLeader) {{
				currentLeader = false;
				pastLeaders++;
				localTime++;
				{
					// printStatusMessage("ID="+Integer.toString(id)+" was deposed at t="+Integer.toString(time))
					{
						Object _arguments[] = { "ID="+Integer.toString(id)+" was deposed at t="+Integer.toString(time) };
						Message message = new Message( self, self, "printStatusMessage", _arguments, null, null );
						__messages.add( message );
					}
				}
				time++;
				time1 = time;
				{
					Token token_3_0 = new Token();
					// join block
					token_3_0.setJoinDirector();
					{
						Token token_4_0 = new Token();
						// left<-update(time, 0, pastLeaders)
						{
							Object _arguments[] = { time, new Integer(0), pastLeaders };
							Message message = new Message( self, left, "update", _arguments, null, token_4_0 );
							__messages.add( message );
						}
						// left<-leaderChosen(false, 0, id)
						{
							Object _arguments[] = { false, new Integer(0), id };
							Message message = new Message( self, left, "leaderChosen", _arguments, token_4_0, token_3_0 );
							__messages.add( message );
						}
					}
					addJoinToken(token_3_0);
					// ret()
					{
						Object _arguments[] = {  };
						Message message = new Message( self, self, "ret", _arguments, token_3_0, currentMessage.getContinuationToken() );
						__messages.add( message );
					}
					throw new CurrentContinuationException();
				}
			}
}			else {{
				localTime++;
				time++;
				{
					// left<-leaderTime(time, revolts, pastLeaders, localTime, l)
					{
						Object _arguments[] = { time, revolts, pastLeaders, localTime, l };
						Message message = new Message( self, left, "leaderTime", _arguments, null, null );
						__messages.add( message );
					}
				}
				{
					// ret()
					{
						Object _arguments[] = {  };
						Message message = new Message( self, self, "ret", _arguments, null, currentMessage.getContinuationToken() );
						__messages.add( message );
					}
					throw new CurrentContinuationException();
				}
			}
}}		}
		public int replyMessage(int newId, int senderId, boolean senderLeaderStatus, int pastLeaders, int time, int localTime, boolean direction) {
			if (id==senderId) {{
				if (senderLeaderStatus) {{
					ttl *= 2;
					{
						// left<-receiveMessage(id, priority, canBeLeader, ttl, pastLeaders, time, localTime, direction)
						{
							Object _arguments[] = { id, priority, canBeLeader, ttl, pastLeaders, time, localTime, direction };
							Message message = new Message( self, left, "receiveMessage", _arguments, null, null );
							__messages.add( message );
						}
					}
				}
}			}
}			else {{
				{
					// left<-replyMessage(newId, senderId, senderLeaderStatus, pastLeaders, time, localTime, direction)
					{
						Object _arguments[] = { newId, senderId, senderLeaderStatus, pastLeaders, time, localTime, direction };
						Message message = new Message( self, left, "replyMessage", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
}			return -1;
		}
		public int startElection(int timestamp, int pastLeaders) {
			if (tempCanBeLeader) {{
				{
					Token token_3_0 = new Token();
					// join block
					token_3_0.setJoinDirector();
					{
						// right<-receiveMessage(id, priority, canBeLeader, ttl, pastLeaders, timestamp, 0, true)
						{
							Object _arguments[] = { id, priority, canBeLeader, ttl, pastLeaders, timestamp, new Integer(0), true };
							Message message = new Message( self, right, "receiveMessage", _arguments, null, token_3_0 );
							__messages.add( message );
						}
					}
					{
						// left<-receiveMessage(id, priority, canBeLeader, ttl, pastLeaders, timestamp, 0, false)
						{
							Object _arguments[] = { id, priority, canBeLeader, ttl, pastLeaders, timestamp, new Integer(0), false };
							Message message = new Message( self, left, "receiveMessage", _arguments, null, token_3_0 );
							__messages.add( message );
						}
					}
					addJoinToken(token_3_0);
					// standardOutput<-println(token)
					{
						Object _arguments[] = { token_3_0 };
						Message message = new Message( self, standardOutput, "println", _arguments, token_3_0, null );
						Object[] _propertyInfo = { new Integer(100) };
						message.setProperty( "delay", _propertyInfo );
						__messages.add( message );
					}
				}
			}
}			return id;
		}
	}
}