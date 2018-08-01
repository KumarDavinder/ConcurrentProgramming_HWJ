package it.uniroma3.hwj.main;

import java.util.concurrent.TimeUnit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import it.uniroma3.hwj.AllTests;
import it.uniroma3.hwj.base.Node;
import it.uniroma3.hwj.hwj1.BTA_unlimitedMemory;
import it.uniroma3.hwj.hwj2.BTA_limitedMemory;
import it.uniroma3.hwj.hwj3.BTA_forkJoin;
import it.uniroma3.hwj.hwj4.BTA_stream;
import it.uniroma3.hwj.suppot.BTA_serial;
import it.uniroma3.hwj.suppot.BinaryTree;

public class HWJ_main {

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


		/*==============LIMITED MEMORY================*/
		BTA_limitedMemory bta_limitedMemory = new BTA_limitedMemory(depthTree);
		for(int i = 0; i < warm_up; i++)
			bta_limitedMemory.computeOnerousSum(root);
		long startTimeLM = System.currentTimeMillis();
		int sumLM = bta_limitedMemory.computeOnerousSum(root);
		double timeLM = System.currentTimeMillis() - startTimeLM;

		/*============== FORK JOIN ================*/
		BTA_forkJoin bta_forkJoin = new BTA_forkJoin();
		for(int i = 0; i < warm_up; i++)
			bta_forkJoin.computeOnerousSum(root);
		long startTimeFJ = System.currentTimeMillis();
		int sumFJ = bta_forkJoin.computeOnerousSum(root);
		double timeFJ = System.currentTimeMillis() - startTimeFJ;

		/*============== STREAM ================*/
		BTA_stream bta_stream = new BTA_stream();
		for(int i = 0; i < warm_up; i++)
			bta_stream.computeOnerousSum(root);
		long startTimeStream = System.currentTimeMillis();
		int sumStream = bta_stream.computeOnerousSum(root);
		double timeStream = System.currentTimeMillis() - startTimeStream;

		/*==============TEST================*/
		JUnitCore junit = new JUnitCore();
		System.out.println("====================ALL TESTS==========================");
		Result resultTest = junit.run(AllTests.class);
		for (Failure failure : resultTest.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(resultTest.wasSuccessful());

		/*===========================================================================================*/
		System.out.println("\n====================HWJ==========================");
		System.out.println("TREE");
		System.out.println("Number nodes "+ nodes);
		System.out.println("Depth: "+ depthTree);
		System.out.println("Warm_up = "+warm_up);
		System.out.println("********************TEMPO DI ESECUZIONE*******************");
		System.out.println("Serial time = "+serialTime+" ms");
		System.out.println("Unlimited Memory time = "+timeUM+" ms");
		System.out.println("Limited Memory time = "+timeLM+" ms");
		System.out.println("Fork Join time = "+timeFJ+" ms");
		System.out.println("Stream time = "+timeStream+" ms\n");
		System.out.println("*****************SOMME*****************");
		System.out.println("Somma esecuzione Seriale = " + sumSerial);
		System.out.println("Somma Unlimited Memory = "+sumUM);
		System.out.println("Somma Limited Memory = "+sumLM);
		System.out.println("Somma Fork Join = "+sumFJ);		
		System.out.println("Somma Stream = "+sumStream+"\n");
		System.out.println("*****************SPEEDUP******************");
		System.out.println("Speedup Unlimited Memory = " +serialTime/timeUM);
		System.out.println("Speedup Limited Memory = " +serialTime/timeLM);
		System.out.println("Speedup Fork Join = " +serialTime/timeFJ);
		System.out.println("Speedup Stream = " +serialTime/timeStream+ " non è lo speedUp perchè l'implementazione con gli Stream è seriale");
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
