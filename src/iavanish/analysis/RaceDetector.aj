package iavanish.analysis;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public aspect RaceDetector {

	Map <String, Lock> locksOnSharedVariables = new HashMap <String, Lock> ();
	//	<variableName, locks>
	
	//	ThreadLocals are variables that are exclusively assigned to all the threads in the system
	private final class GetThreadLocalObject extends ThreadLocal {
		public synchronized Lock initialValue() {
			Lock locks = new Lock();
			return locks;
		}
	}

	ThreadLocal locksHeldByThreads = new GetThreadLocalObject();
	//	<locks>

	before(): Pointcuts.sharedVariableRead() && Pointcuts.scope() {
		
		synchronized(this) {
			
			String variableName = thisJoinPointStaticPart.getSignature().toLongString();
			Lock locksHeld = (Lock)locksHeldByThreads.get();
			
			if(!locksOnSharedVariables.containsKey(variableName)) {
				locksOnSharedVariables.put(variableName, locksHeld);
			}
			
			else {
			
				Lock locksOnVariable = locksOnSharedVariables.get(variableName);
				Lock temp = new Lock();
				
				for(Object l: locksOnVariable.locks) {
					if(locksHeld.locks.contains(l)) {
						temp.locks.add(l);
						//	finding intersection
					}
				}
				
				if(temp.locks.size() == 0) {
					//	intersection is empty, race possible
					try {
						throw new RaceDetectedException(thisJoinPoint.getSourceLocation());
					}
					catch(RaceDetectedException e) {
						System.err.println("Race Detected at: " + e);
					}
				}
				else {
					locksOnSharedVariables.put(variableName, temp);
					//	safe
				}
				
			}
			
		}
		
	}
	
	before(): Pointcuts.sharedVariableWrite() && Pointcuts.scope() {
		
		synchronized(this) {
			
			String variableName = thisJoinPointStaticPart.getSignature().toLongString();
			Lock locksHeld = (Lock)locksHeldByThreads.get();
			
			if(!locksOnSharedVariables.containsKey(variableName)) {
				locksOnSharedVariables.put(variableName, locksHeld);
			}
			
			else {
			
				Lock locksOnVariable = locksOnSharedVariables.get(variableName);
				Lock temp = new Lock();
				
				for(Object l: locksOnVariable.locks) {
					if(locksHeld.locks.contains(l)) {
						temp.locks.add(l);
						//	finding intersection
					}
				}
				
				if(temp.locks.size() == 0) {
					//	intersection is empty, race possible
					try {
						throw new RaceDetectedException(thisJoinPoint.getSourceLocation());
					}
					catch(RaceDetectedException e) {
						System.err.println("Race Detected at: " + e);
					}
				}
				else {
					locksOnSharedVariables.put(variableName, temp);
					//	safe
				}
				
			}
			
		}
		
	}
	
	before(Object lock): lock() && args(lock) && Pointcuts.scope() {
		
		synchronized(this) {
			
			//	collecting the locks held by a particular thread
			Lock locksHeld = (Lock)locksHeldByThreads.get();
			locksHeld.locks.add(lock);
			
		}
		
	}

	after(Object lock): unlock() && args(lock) && Pointcuts.scope() {
	
		synchronized(this) {
			
			//	filtering out the locks released by a particular thread
			Lock locksHeld = (Lock)locksHeldByThreads.get();
			locksHeld.locks.remove(lock);
			
		}
		
	}
	
}
