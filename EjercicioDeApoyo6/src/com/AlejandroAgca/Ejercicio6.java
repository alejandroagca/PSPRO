package com.AlejandroAgca;

public class Ejercicio6 {

	public static boolean[] barrera;

	public static void main(String[] args) {
		barrera = new boolean[4];
		for (int i = 0; i < barrera.length; i++) {
			barrera[i] = true;
		}
		Barrera miBarrera = new Barrera();
		HolaMundo hilo1 = new HolaMundo(1, miBarrera);
		HolaMundo hilo2 = new HolaMundo(2, miBarrera);
		HolaMundo hilo3 = new HolaMundo(3, miBarrera);
		HolaMundo hilo4 = new HolaMundo(4, miBarrera);

		hilo1.start();
		hilo2.start();
		hilo3.start();
		hilo4.start();

		try {
			hilo1.join();
			hilo2.join();
			hilo3.join();
			hilo4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class HolaMundo extends Thread {
	int numero;
	Barrera miBarrera;

	public HolaMundo(int numero, Barrera miBarrera) {
		this.numero = numero;
		this.miBarrera = miBarrera;
	}

	@Override
	public void run() {
		while (true) {
			try {
				sleep(numero * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Hola desde el hilo numero " + numero);
			miBarrera.esperar();
		}
	}
}

class Barrera {
	private int preparado;
	public Barrera() {
		this.preparado = 0;
	}

	public synchronized void esperar() {
		preparado++;
		if (preparado < 4) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}else {
			preparado = 0;
			notifyAll();
		}
	}
}
