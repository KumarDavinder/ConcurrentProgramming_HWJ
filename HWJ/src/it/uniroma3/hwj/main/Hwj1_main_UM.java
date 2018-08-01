package it.uniroma3.hwj.main;

import java.util.concurrent.TimeUnit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import it.uniroma3.hwj.UnlimitedMemory_hwj1;
import it.uniroma3.hwj.base.Node;
import it.uniroma3.hwj.hwj1.BTA_unlimitedMemory;
import it.uniroma3.hwj.suppot.BTA_serial;
import it.uniroma3.hwj.suppot.BinaryTree;

public class Hwj1_main_UM {

	public static void main(String[] args) {
        long lStartTime = System.currentTimeMillis();

		Node root = BinaryTree.genereateTree(16);
		int nodes = BinaryTree.countNodes(root);
		int depthTree = BinaryTree.getDepth(root);

		int warm_up = 3;
		/*==============ESECUZIONE SERIALE================*/
		BTA_serial bta_serial = new BTA_serial();
		for(int i = 0; i < warm_up; i++){
			bta_serial.computeOnerousSum(root);
		}		
		long startSerialTime = System.currentTimeMillis();
		int sumSerial  = bta_serial.computeOnerousSum(root);
		double serialTime = System.currentTimeMillis() - startSerialTime;

		/*==============UNLIMITED MEMORY================*/
		BTA_unlimitedMemory bta_unlimitedMemory = new BTA_unlimitedMemory();
		for(int i = 0; i < warm_up; i++){
			bta_unlimitedMemory.computeOnerousSum(root);
		}
		long startTimeUM = System.currentTimeMillis();
		int sumUM = bta_unlimitedMemory.computeOnerousSum(root);
		double timeUM = System.currentTimeMillis() - startTimeUM;

		/*==============TEST================*/
		JUnitCore junit = new JUnitCore();
		System.out.println("====================TEST UNLIMITED MEMORY==========================");
		Result resultTest = junit.run(UnlimitedMemory_hwj1.class);
		for (Failure failure : resultTest.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(resultTest.wasSuccessful());

		/*==============================*/
		System.out.println("\n====================UNLIMITED MEMORY==========================");
		System.out.println("TREE");
		System.out.println("Number nodes "+ nodes);
		System.out.println("Depth: "+ depthTree);
		System.out.println("Warm_up = "+warm_up);
		System.out.println("********************TEMPO DI ESECUZIONE*******************");
		System.out.println("Serial time = "+serialTime+" ms");
		System.out.println("Unlimited Memory time = "+timeUM+" ms");
		System.out.println("\n*****************SOMME*****************");
		System.out.println("Somma esecuzione Seriale = " + sumSerial);
		System.out.println("Somma Unlimited Memory = "+sumUM);
		System.out.println("\n*****************SPEEDUP******************");
		System.out.println("Speedup Unlimited Memory = " +serialTime/timeUM);
		System.out.println("\n********************TEMPO IMPIEGATO*******************");
		long lEndTime = System.currentTimeMillis();
		long millis = lEndTime - lStartTime;
		String time = String.format("%d min, %d sec", 
				TimeUnit.MILLISECONDS.toMinutes(millis),
				TimeUnit.MILLISECONDS.toSeconds(millis) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
				);
		System.out.println("tempo impiegato = " + time);
	}
}
