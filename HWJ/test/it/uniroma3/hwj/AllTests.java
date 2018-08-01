package it.uniroma3.hwj;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	UnlimitedMemory_hwj1.class,
	LimitedMemory_hwj2.class,
	ForkJoin_hwj3.class, 
	Stream_hwj4.class 
})
public class AllTests {

}
