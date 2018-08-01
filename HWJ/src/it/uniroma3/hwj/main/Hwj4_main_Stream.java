package it.uniroma3.hwj.main;

import java.util.concurrent.TimeUnit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import it.uniroma3.hwj.Stream_hwj4;
import it.uniroma3.hwj.base.Node;
import it.uniroma3.hwj.hwj4.BTA_stream;
import it.uniroma3.hwj.suppot.BTA_serial;
import it.uniroma3.hwj.suppot.BinaryTree;

public class Hwj4_main_Stream {

	public static void main(String[] args) {
        long lStartTime = System.currentTimeMillis();

		Node root = BinaryTree.genereateTree(10);
		int nodes = BinaryTree.countNodes(root);
		int depthTree = BinaryTree.getDepth(root);

		int warm_up = 5;
		/*==============ESECUZIONE SERIALE================*/
		BTA_serial bta_serial = new BTA_serial();
		for(int i = 0; i < warm_up; i++){
			bta_serial.computeOnerousSum(root);
		}		
		long startSerialTime = System.currentTimeMillis();
		int sumSerial  = bta_serial.computeOnerousSum(root);
		double serialTime = System.currentTimeMillis() - startSerialTime;

		/*============== STREAM ================*/
		BTA_stream bta_stream = new BTA_stream();
		for(int i = 0; i < warm_up; i++)
			bta_stream.computeOnerousSum(root);
		long startTimeStream = System.currentTimeMillis();
		int sumStream = bta_stream.computeOnerousSum(root);
		double timeStream = System.currentTimeMillis() - startTimeStream;
		
		/*==============TEST================*/
		JUnitCore junit = new JUnitCore();
		System.out.println("====================TEST STREAM==========================");
		Result resultTest = junit.run(Stream_hwj4.class);
		for (Failure failure : resultTest.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(resultTest.wasSuccessful());

		/*==============================*/
		System.out.println("\n====================Stream==========================");
		System.out.println("TREE");
		System.out.println("Number nodes "+ nodes);
		System.out.println("Depth: "+ depthTree);
		System.out.println("Warm_up = "+warm_up);
		System.out.println("********************TEMPO DI ESECUZIONE*******************");
		System.out.println("Serial time = "+serialTime+" ms");
		System.out.println("Stream time = "+timeStream+" ms");
		System.out.println("\n*****************SOMME*****************");
		System.out.println("Somma esecuzione Seriale = " + sumSerial);
		System.out.println("Somma Stream = "+sumStream);
		System.out.println("\n*****************SPEEDUP******************");
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
