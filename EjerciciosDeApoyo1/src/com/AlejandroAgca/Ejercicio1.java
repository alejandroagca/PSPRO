package com.AlejandroAgca;

public class Ejercicio1 {

	public static void main(String[] args) {
		
		HolaMundoThread[] holamundosthread = new HolaMundoThread[6];
		HolaMundoRunnable [] holamundosrunnable = new HolaMundoRunnable[6];
		for (int i = 0; i < holamundosrunnable.length; i++) {
			holamundosrunnable[i] = new HolaMundoRunnable(i);
		}
		for (int i = 0; i < holamundosrunnable.length; i++) {
			holamundosrunnable[i].espera();
		}
		
		for (int i = 0; i < 6; i++) {
			holamundosthread[i] = new HolaMundoThread(i);
		}
		
		for (int i = 0; i < holamundosthread.length; i++) {
			holamundosthread[i].start();
		}
		
		for (int i = 0; i < holamundosthread.length; i++) {
			try {
				holamundosthread[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

class HolaMundoThread extends Thread{
	int numero;
	public HolaMundoThread(int numero) {
		this.numero = numero;
	}
	
	@Override
	public void run() {
		try {
			sleep(numero * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Hola desde el hilo número " + numero);
	}
}

class HolaMundoRunnable implements Runnable{
	int numero;
	Thread hilo;
	public HolaMundoRunnable(int numero) {
		this.numero = numero;
		hilo = new Thread(this);
		hilo.start();
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(numero*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Hola desde el hilo número " + numero);
	}
	
	public void espera() {
		try {
			hilo.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
