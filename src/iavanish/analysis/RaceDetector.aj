package iavanish.analysis;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public aspect RaceDetector {

	Map <String, Lock> locksOnSharedVariables = new HashMap <String, Lock> ();
	
	private final class ThreadLocalExtension extends ThreadLocal {
		public synchronized Lock initialValue() {
			Lock locks = new Lock();
			return locks;
		}
	}

	ThreadLocal locksHeldByThreads = new ThreadLocalExtension();

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
					System.err.println("Race Detected at: " + thisJoinPoint.getSourceLocation());
					//	System.exit(1);
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
					System.err.println("Race Detected at: " + thisJoinPoint.getSourceLocation());
					//	System.exit(1);
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
	
	after(Object lock): lock() && args(lock) && Pointcuts.scope() {
		
		synchronized(this) {
			System.out.println(Thread.currentThread().getName() + " has acquired a lock on " + lock);
		}
		
	}

	after(Object lock): unlock() && args(lock) && Pointcuts.scope() {
	
		synchronized(this) {
			System.out.println(Thread.currentThread().getName() + " has released a lock on " + lock);
			Lock locksHeld = (Lock)locksHeldByThreads.get();
			locksHeld.locks.remove(lock);
		}
		
	}
	
}
