package com.AlejandroAgca;

public class Relevos {
	public static void main(String[] args) {
		Carrera carrera = new Carrera();
		Hilo hilo1 = new Hilo(1, carrera);
		Hilo hilo2 = new Hilo(2, carrera);
		Hilo hilo3 = new Hilo(3, carrera);
		Hilo hilo4 = new Hilo(4, carrera);
		hilo1.start();
		hilo2.start();
		hilo3.start();
		hilo4.start();
		carrera.ComenzarCarrera();
		try {
			hilo1.join();
			hilo2.join();
			hilo3.join();
			hilo4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Todos los relevistas han terminado la carrera");
	}

}

class Hilo extends Thread{
	Carrera carrera;
	int numero;
	public Hilo(int numero, Carrera carrera) {
		this.numero = numero;
		this.carrera = carrera;
	}
	
	@Override
	public void run() {
			carrera.RelevistaComienza(this);
	}
}

class Carrera{
	boolean comienzo;
	public Carrera() {
		comienzo = false;
	}
	
	public synchronized void RelevistaComienza(Hilo hilo) {
		while(!comienzo){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("El hilo " + hilo.numero + " finaliza la carrera");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		notify();
	}
	
	public synchronized void ComenzarCarrera(){
		System.out.println("¡Comienza la carrera!");
		comienzo = true;
		notify();
	}
}