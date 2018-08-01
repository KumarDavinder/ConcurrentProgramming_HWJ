package it.uniroma3.hwj.main;

import java.util.concurrent.TimeUnit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import it.uniroma3.hwj.LimitedMemory_hwj2;
import it.uniroma3.hwj.base.Node;
import it.uniroma3.hwj.hwj2.BTA_limitedMemory;
import it.uniroma3.hwj.suppot.BTA_serial;
import it.uniroma3.hwj.suppot.BinaryTree;

public class Hwj2_main_LM {

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

		/*==============LIMITED MEMORY================*/
		BTA_limitedMemory bta_limitedMemory = new BTA_limitedMemory(depthTree);
		for(int i = 0; i < warm_up; i++)
			bta_limitedMemory.computeOnerousSum(root);
		long startTimeLM = System.currentTimeMillis();
		int sumLM = bta_limitedMemory.computeOnerousSum(root);
		double timeLM = System.currentTimeMillis() - startTimeLM;
		
		/*==============TEST================*/
		JUnitCore junit = new JUnitCore();
		System.out.println("====================TEST LIMITED MEMORY===========================");
		Result resultTest = junit.run(LimitedMemory_hwj2.class);
		for (Failure failure : resultTest.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(resultTest.wasSuccessful());

		/*==============================*/
		System.out.println("\n====================LIMITED MEMORY==========================");
		System.out.println("TREE");
		System.out.println("Number nodes "+ nodes);
		System.out.println("Depth: "+ depthTree);
		System.out.println("Warm_up = "+warm_up);
		System.out.println("********************TEMPO DI ESECUZIONE*******************");
		System.out.println("Serial time = "+serialTime+" ms");
		System.out.println("Limited Memory time = "+timeLM+" ms");
		System.out.println("\n*****************SOMME*****************");
		System.out.println("Somma esecuzione Seriale = " + sumSerial);
		System.out.println("Somma Limited Memory = "+sumLM);
		System.out.println("\n*****************SPEEDUP******************");
		System.out.println("Speedup Limited Memory = " +serialTime/timeLM);
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
