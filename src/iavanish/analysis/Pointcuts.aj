package iavanish.analysis;

public aspect Pointcuts {

	pointcut scope():
		((!within(iavanish.analysis.*)) && 
				((!within(iavanish.test1.*)) && (!within(iavanish.test2.*)) && (!within(iavanish.test3.*))) && 
				((within(iavanish.test1_checked.*)) || (within(iavanish.test2_checked.*)) || (within(iavanish.test3_checked.*))));
	
	pointcut sharedVariableRead():
		get(static * *);
	
	pointcut sharedVariableWrite():
		set(static * *);
	
}
