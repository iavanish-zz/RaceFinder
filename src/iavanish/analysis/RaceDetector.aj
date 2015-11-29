package iavanish.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public aspect RaceDetector {

	//	ThreadLocals are variables that are exclusively assigned to all the threads in the system
	private final class GetThreadLocalObject extends ThreadLocal {
		public synchronized Lock initialValue() {
			Lock locks = new Lock();
			return locks;
		}
	}	
	
	//	to ensure that we don't report the same data race multiple times
	Set <String> detectedRaces = new HashSet <String> ();
	
	//	<variableName, locks>
	Map <String, Lock> locksOnSharedVariables = new HashMap <String, Lock> ();
	
	//	<locks>
	ThreadLocal locksHeldByThreads = new GetThreadLocalObject();

	before(): Pointcuts.sharedVariableRead() && Pointcuts.scope() {
		
		synchronized(this) {
			
			String variableName = thisJoinPointStaticPart.getSignature().toLongString();
			
			if(!variableName.equals("public static final java.io.PrintStream java.lang.System.out")) {
			
				Lock locksHeldByThread = (Lock)locksHeldByThreads.get();
			
				if(!locksOnSharedVariables.containsKey(variableName)) {
					//	first read/write
					locksOnSharedVariables.put(variableName, locksHeldByThread);
				}
			
				else {
			
					Lock locksOnSharedVariable = locksOnSharedVariables.get(variableName);
					Lock temp = new Lock();
				
					for(Object l: locksOnSharedVariable.locks) {
						if(locksHeldByThread.locks.contains(l)) {
							//	finding intersection
							temp.locks.add(l);
						}
					}
				
					if(temp.locks.size() == 0) {
				
						//	intersection is empty, race possible
						try {
							throw new RaceDetectedException(thisJoinPoint.getSourceLocation());
						}
					
						catch(RaceDetectedException e) {
					
							if(!detectedRaces.contains(e.toString())) {
								//	check if we have already detected this race
								detectedRaces.add(e.toString());
								System.err.println("Race Detected at: " + e.toString());
							}
							else {
								//	ignore
							}
						
						}
				
					}
				
					else {
				
						//	safe
						locksOnSharedVariables.put(variableName, temp);
				
					}
					
				}
				
			}
			
		}
		
	}
	
	before(): Pointcuts.sharedVariableWrite() && Pointcuts.scope() {
		
		synchronized(this) {
			
			String variableName = thisJoinPointStaticPart.getSignature().toLongString();
			
			if(!variableName.equals("public static final java.io.PrintStream java.lang.System.out")) {
			
				Lock locksHeldByThread = (Lock)locksHeldByThreads.get();
			
				if(!locksOnSharedVariables.containsKey(variableName)) {
					//	first read/write
					locksOnSharedVariables.put(variableName, locksHeldByThread);
				}
			
				else {
			
					Lock locksOnSharedVariable = locksOnSharedVariables.get(variableName);
					Lock temp = new Lock();
				
					for(Object l: locksOnSharedVariable.locks) {
						if(locksHeldByThread.locks.contains(l)) {
							//	finding intersection
							temp.locks.add(l);
						}
					}
				
					if(temp.locks.size() == 0) {
					
						//	intersection is empty, race possible
						try {
							throw new RaceDetectedException(thisJoinPoint.getSourceLocation());
						}
						
						catch(RaceDetectedException e) {
					
							if(!detectedRaces.contains(e.toString())) {
								//	check if we have already detected this race
								detectedRaces.add(e.toString());
								System.err.println("Race Detected at: " + e.toString());
							}
							else {
								//	ignore
							}
						
						}
				
					}
				
					else {
				
						//	safe
						locksOnSharedVariables.put(variableName, temp);
				
					}
				
				}
				
			}
			
		}
		
	}
	
	before(Object lock): lock() && args(lock) && Pointcuts.scope() {
		
		synchronized(this) {
			
			//	collecting the locks held by a particular thread
			Lock locksHeldByThread = (Lock)locksHeldByThreads.get();
			locksHeldByThread.locks.add(lock);
			
		}
		
	}

	after(Object lock): unlock() && args(lock) && Pointcuts.scope() {
	
		synchronized(this) {
			
			//	removing the locks released by a particular thread
			Lock locksHeldByThread = (Lock)locksHeldByThreads.get();
			locksHeldByThread.locks.remove(lock);
			
		}
		
	}
	
}
