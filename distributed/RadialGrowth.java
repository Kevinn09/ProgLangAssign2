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
import java.net.*;

public class RadialGrowth extends UniversalActor  {
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
		RadialGrowth instance = (RadialGrowth)new RadialGrowth(uan, ual,null).construct();
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

	public static ActorReference getReferenceByName(UAN uan)	{ return new RadialGrowth(false, uan); }
	public static ActorReference getReferenceByName(String uan)	{ return RadialGrowth.getReferenceByName(new UAN(uan)); }
	public static ActorReference getReferenceByLocation(UAL ual)	{ return new RadialGrowth(false, ual); }

	public static ActorReference getReferenceByLocation(String ual)	{ return RadialGrowth.getReferenceByLocation(new UAL(ual)); }
	public RadialGrowth(boolean o, UAN __uan)	{ super(false,__uan); }
	public RadialGrowth(boolean o, UAL __ual)	{ super(false,__ual); }
	public RadialGrowth(UAN __uan,UniversalActor.State sourceActor)	{ this(__uan, null, sourceActor); }
	public RadialGrowth(UAL __ual,UniversalActor.State sourceActor)	{ this(null, __ual, sourceActor); }
	public RadialGrowth(UniversalActor.State sourceActor)		{ this(null, null, sourceActor);  }
	public RadialGrowth()		{  }
	public RadialGrowth(UAN __uan, UAL __ual, Object obj) {
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
			    createRemotely(__uan, __ual, "distributed.RadialGrowth", sourceRef);
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

	public UniversalActor construct (String[] args) {
		Object[] __arguments = { args };
		this.send( new Message(this, this, "construct", __arguments, null, null) );
		return this;
	}

	public UniversalActor construct() {
		Object[] __arguments = { };
		this.send( new Message(this, this, "construct", __arguments, null, null) );
		return this;
	}

	public class State extends UniversalActor .State {
		public RadialGrowth self;
		public void updateSelf(ActorReference actorReference) {
			((RadialGrowth)actorReference).setUAL(getUAL());
			((RadialGrowth)actorReference).setUAN(getUAN());
			self = new RadialGrowth(false,getUAL());
			self.setUAN(getUAN());
			self.setUAL(getUAL());
			self.activateGC();
		}

		public void preAct(String[] arguments) {
			getActorMemory().getInverseList().removeInverseReference("rmsp://me",1);
			{
				Object[] __args={arguments};
				self.send( new Message(self,self, "act", __args, null,null,false) );
			}
		}

		public State() {
			this(null, null);
		}

		public State(UAN __uan, UAL __ual) {
			super(__uan, __ual);
			addClassName( "distributed.RadialGrowth$State" );
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

		LinkedList nodes = new LinkedList();
		void construct(String[] args){
						{
				// act(args)
				{
					Object _arguments[] = { args };
					Message message = new Message( self, self, "act", _arguments, null, null );
					__messages.add( message );
				}
			}
		}
		public void act(String[] args) {
			if (args.length!=2) {{
				{
					// standardError<-println("[error] incorrect amount of arguments. Expecting 2, received "+args.length)
					{
						Object _arguments[] = { "[error] incorrect amount of arguments. Expecting 2, received "+args.length };
						Message message = new Message( self, standardError, "println", _arguments, null, null );
						__messages.add( message );
					}
				}
				return;
			}
}			String filename = args[0];
			String startingUAN = args[1];
			String startID = "";
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter("output.txt", false));
				out.close();
				BufferedReader reader = new BufferedReader(new FileReader(filename));
				int count = 0;
				String line;
				while ((line=reader.readLine())!=null) {
					String tmp = line;
					String[] inputs = tmp.split("\t");
					String host = inputs[1];
					String port = inputs[2];
					Node newNode = ((Node)new Node(new UAN("uan://"+args[1]+"/"+inputs[0]), new UAL("rmsp://"+host+":"+port),this).construct(line));
					nodes.add(newNode);
					if (count==0) {{
						startID = inputs[0];
						count++;
					}
}				}
				reader.close();
			}
			catch (ConnectException ce) {
				{
					// standardError<-println("[error] connection error\n"+ce)
					{
						Object _arguments[] = { "[error] connection error\n"+ce };
						Message message = new Message( self, standardError, "println", _arguments, null, null );
						__messages.add( message );
					}
				}
				return;
			}
			catch (IOException ioe) {
				{
					// standardError<-println("[error] Can't open the file "+filename+"\n"+ioe)
					{
						Object _arguments[] = { "[error] Can't open the file "+filename+"\n"+ioe };
						Message message = new Message( self, standardError, "println", _arguments, null, null );
						__messages.add( message );
					}
				}
				return;
			}
			catch (Exception e) {
				{
					// standardError<-println("[error] distributed RadialGrowth failed\n"+e)
					{
						Object _arguments[] = { "[error] distributed RadialGrowth failed\n"+e };
						Message message = new Message( self, standardError, "println", _arguments, null, null );
						__messages.add( message );
					}
				}
				return;
			}

			for (int i = 0; i<nodes.size()-1; i++){
				{
					// nodes.get(i+1)<-setLeft(nodes.get(i))
					{
						Object _arguments[] = { nodes.get(i) };
						Message message = new Message( self, nodes.get(i+1), "setLeft", _arguments, null, null );
						__messages.add( message );
					}
				}
				{
					// nodes.get(i)<-setRight(nodes.get(i+1))
					{
						Object _arguments[] = { nodes.get(i+1) };
						Message message = new Message( self, nodes.get(i), "setRight", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
			{
				// nodes.getFirst()<-setLeft(nodes.getLast())
				{
					Object _arguments[] = { nodes.getLast() };
					Message message = new Message( self, nodes.getFirst(), "setLeft", _arguments, null, null );
					__messages.add( message );
				}
			}
			{
				// nodes.getLast()<-setRight(nodes.getFirst())
				{
					Object _arguments[] = { nodes.getFirst() };
					Message message = new Message( self, nodes.getLast(), "setRight", _arguments, null, null );
					__messages.add( message );
				}
			}
			for (int i = 0; i<nodes.size(); i++){
				{
					// nodes.get(i)<-setSize(nodes.size())
					{
						Object _arguments[] = { nodes.size() };
						Message message = new Message( self, nodes.get(i), "setSize", _arguments, null, null );
						__messages.add( message );
					}
				}
			}
			startingUAN = args[1]+"/"+startID;
			{
				// beginElection(startingUAN)
				{
					Object _arguments[] = { startingUAN };
					Message message = new Message( self, self, "beginElection", _arguments, null, null );
					__messages.add( message );
				}
			}
		}
		public void beginElection(String startingUAN) {
			Node n = (Node)Node.getReferenceByName(new UAN("uan://"+startingUAN));
			{
				// n<-startElection(0, 0)
				{
					Object _arguments[] = { new Integer(0), new Integer(0) };
					Message message = new Message( self, n, "startElection", _arguments, null, null );
					__messages.add( message );
				}
			}
		}
	}
}