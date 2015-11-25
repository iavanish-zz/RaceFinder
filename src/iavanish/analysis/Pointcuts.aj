package iavanish.analysis;

public aspect Pointcuts {

	pointcut scope():
		((!within(iavanish.analysis.*)) && (!within(iavanish.test0.*)) && 
		(within(iavanish.test1.*)) && (!within(iavanish.test2.*)));
	
	pointcut sharedVariableRead():
		get(static * *);
	
	pointcut sharedVariableWrite():
		set(static * *);
	
}
