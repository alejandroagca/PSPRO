package com.AlejandroAgca;

public class Check {
	public static void main(String[] args) {
		Monitor monitor = new Monitor();
		Escritor escritor = new Escritor(monitor);
		Lector lector = new Lector(monitor);
		escritor.start();
		lector.start();
		try {
			escritor.join();
			lector.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


class Escritor extends Thread {

	Monitor monitor;

	public Escritor(Monitor m) {
		monitor = m;
	}

	@Override
	public void run() {
		for (int i = 0; i < 1000; i++) {
			monitor.escribir(0);
		}
	}
}


class Lector extends Thread {
	Monitor monitor;

	public Lector(Monitor m) {
		monitor = m;
	}

	@Override
	public void run() {
		for (int i = 0; i < 1000; i++) {
			monitor.leer();
		}
	}
}

class Monitor {
	private int buffer[];
	private int siguiente;
	private boolean estaLleno;
	private boolean estaVacio;
	
	public Monitor() 
	{
		buffer = new int[1000];
		siguiente = 0;
		estaLleno = false;
		estaVacio = true;
	}

	public synchronized void leer() {
		while (estaVacio == true) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		siguiente--;
		if (siguiente == 0) {
			estaVacio = true;
		}
		
		if(siguiente >= 1) {
			if (buffer[siguiente] == buffer[siguiente-1]) {
				System.out.println("El valor del buffer en la posición " + siguiente + " es igual al anterior");
			}
			else {
				System.out.println("ERROR");
			}
		}
		estaLleno = false;
		notify();
	}

	public synchronized void escribir(int n) {
		while (estaLleno == true) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		buffer[siguiente] = n;
		siguiente++;

		if (siguiente == 1000) {
			estaLleno = true;
		}
		estaVacio = false;
		notify();
	}
}