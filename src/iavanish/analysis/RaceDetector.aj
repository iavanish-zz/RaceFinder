package iavanish.analysis;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public aspect RaceDetector {

	Map <String, Lock> locksOnSharedVariables = new HashMap <String, Lock> ();
	
	private final class GetThreadLocalObject extends ThreadLocal {
		public synchronized Lock initialValue() {
			Lock locks = new Lock();
			return locks;
		}
	}

	ThreadLocal locksHeldByThreads = new GetThreadLocalObject();

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
					}
				}
				if(temp.locks.size() == 0) {
					throw new RaceDetectedException(thisJoinPoint.getSourceLocation());
				}
				else {
					locksOnSharedVariables.put(variableName, temp);
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
					}
				}
				if(temp.locks.size() == 0) {
					throw new RaceDetectedException(thisJoinPoint.getSourceLocation());
				}
				else {
					locksOnSharedVariables.put(variableName, temp);
				}
			}
		}
		
	}
	
	before(Object lock): lock() && args(lock) && Pointcuts.scope() {
		
		synchronized(this) {
			Lock locksHeld = (Lock)locksHeldByThreads.get();
			locksHeld.locks.add(lock);
		}
		
	}

	after(Object lock): unlock() && args(lock) && Pointcuts.scope() {
	
		synchronized(this) {
			Lock locksHeld = (Lock)locksHeldByThreads.get();
			locksHeld.locks.remove(lock);
		}
		
	}
	
}
