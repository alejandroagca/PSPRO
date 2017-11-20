package com.AlejandroAgca;

public class Orden {

	public static void main(String[] args) {
		Control control = new Control();
		Hilo primero = new Hilo(1, control);
		Hilo segundo = new Hilo(2, control);
		primero.start();
		segundo.start();
		try {
			primero.join();
			segundo.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class Hilo extends Thread{
	int numero;
	private Control control;
	public Hilo(int numero, Control control) {
		this.numero = numero;
		this.control = control;
	}
	
	@Override
	public void run() {
		control.saludo(this);
	}
	
}

class Control {
	boolean segundo;
	
	public Control(){
		segundo = false;
	}
	
	public synchronized void saludo(Hilo hilo){
		
		if (hilo.numero == 2) {
			segundo = true;
		}
		while (!segundo) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Hola, soy el hilo número " + hilo.numero);
		notify();
	}
}