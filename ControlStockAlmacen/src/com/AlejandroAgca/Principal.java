package com.AlejandroAgca;

public class Principal {

	public static boolean fin = true;
	public static void main(String[] args) {
		
		Almacen miAlmacen = new Almacen();
		Retirada retirarPiezas = new Retirada(miAlmacen);		
		Envio enviarPiezas = new Envio(miAlmacen);
		
		retirarPiezas.start();
		enviarPiezas.start();
		
		try {
			retirarPiezas.join();
			enviarPiezas.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class Retirada extends Thread{
	
	Almacen almacen;
	
	public Retirada(Almacen almacen) {
		this.almacen = almacen;
	}
	
	@Override
	public void run() {
		while(Principal.fin) {
		almacen.salidaDePiezas();
		try { 
			sleep(2400);
			} catch (InterruptedException e)  { 
			e.printStackTrace() ; 
			}
		}
	}
}

class Envio extends Thread{
	
	Almacen almacen;
	public Envio(Almacen almacen) {
		this.almacen = almacen;
	}
	
	@Override
	public void run() {
		while(Principal.fin) {
		almacen.llegadaDePiezas();
		try { 
			sleep(800);
			} catch (InterruptedException e)  { 
			e.printStackTrace() ; 
			}
		}
	}
}

class Almacen{
	
	private int piezas;
	private int maximo;
	private int minimo;
	private int piezasQueLlegan;
	private int piezasQueSalen;
	private int dia;
	
	public Almacen() {
		piezas = 8000;
		maximo = 20000;
		minimo = 0;
		dia = 1;
	}
	
	/*public synchronized void salidaDePiezas() {
	piezasQueSalen = (int) Math.floor(Math.random()*(2500-2000+1)+2000);
	System.out.println("Día " + dia++);
	System.out.println("Pedido de " + piezasQueSalen + " piezas");
while (piezas - piezasQueSalen < minimo) {
	System.out.println("No hay piezas suficientes!");
	try {
		wait();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
}
piezas = piezas - piezasQueSalen;
System.out.println("Hay " + piezas + " en el almacén");
notify();
}*/
	
	public synchronized void salidaDePiezas() {
		piezasQueSalen = (int) Math.floor(Math.random()*(2500-2000+1)+2000);
		System.out.println("\nDía " + dia++);
		System.out.println("Pedido de " + piezasQueSalen + " piezas");
		if(piezas - piezasQueSalen < minimo) {
			System.out.println("No hay piezas suficientes!");
			Principal.fin = false;
		}
		else {
			piezas = piezas - piezasQueSalen;
			System.out.println("Hay " + piezas + " en el almacén");
		}
	}
	
	/*public synchronized void llegadaDePiezas() {
	piezasQueLlegan = (int) Math.floor(Math.random()*(1000-400+1)+400);
	System.out.println("Llegan " + piezasQueLlegan + " piezas");
while (piezas + piezasQueLlegan > maximo) {
	System.out.println("El almacén esta lleno!");
	try {
		wait();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
}
piezas = piezas + piezasQueLlegan;
System.out.println("Hay " + piezas + " en el almacén");
notify();
}*/
	
	public synchronized void llegadaDePiezas() {
		piezasQueLlegan = (int) Math.floor(Math.random()*(1000-400+1)+400);
		System.out.println("Llegan " + piezasQueLlegan + " piezas");
		
		if (piezas + piezasQueLlegan > maximo) {
			System.out.println("El almacén esta lleno!");
			Principal.fin = false;
		}
		else {
			piezas = piezas + piezasQueLlegan;
			System.out.println("Hay " + piezas + " en el almacén");
		}
		
	}
	
}