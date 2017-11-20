package com.AlejandroAgca;


public class Supermercado {
	public static double tiempoMedio;

	public static void main(String[] args) {
		tiempoMedio = 0;
		int resultado = 0;
		int numCajas = Integer.parseInt(args[0]);
		int numClientes = Integer.parseInt(args[1]);
		Caja cajas[] = new Caja[numCajas];
		Cliente clientes [] = new Cliente [numClientes];
		for (int i = 0; i < numCajas; i++) {
			cajas[i] = new Caja(i);
		}
		
		for (int i = 0; i < numClientes; i++) {
			clientes[i] = new Cliente(i, cajas[(int)Math.floor(Math.random() * numCajas)]);
		}
		
		for (int i = 0; i < clientes.length; i++) {
			clientes[i].start();
			resultado += clientes[i].precioCompra;
		}
		
		for (int i = 0; i < clientes.length; i++) {
			try {
				clientes[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("El tiempo medio que han esperado los clientes es: " + tiempoMedio/numClientes);
		 System.out.println("El valor de todas las compras es: " + resultado + " euros");
	}
}

class Cliente extends Thread{
	int numero;
	Caja caja;
	int precioCompra;
	double termina, empieza;
	public Cliente(int numero, Caja caja) {
		this.numero = numero;
		this.caja = caja;
		precioCompra = (int)Math.floor(Math.random() * 10 + 1);
	}
	
	@Override
	public void run() {
		realizarCompra();
		empieza = System.currentTimeMillis();
		caja.asignarCaja(this);
		termina = System.currentTimeMillis() - empieza;
		caja.compra(precioCompra,this);
		caja.dejarCaja(this);
		Supermercado.tiempoMedio += termina;
	}
	
	public void realizarCompra() {
		try {
			Thread.sleep((long)Math.random());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Caja{
	int numeroCaja;
	boolean ocupada;
	
	public Caja(int numeroCaja) {
		this.numeroCaja = numeroCaja;
		this.ocupada = false;
	}
	
	public synchronized void asignarCaja(Cliente cliente) {
		System.out.println("El cliente " + cliente.numero + " se ha puesto en cola para comprar en la caja "+ numeroCaja );
		while(ocupada) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ocupada = true;
	}
	
	public synchronized void dejarCaja(Cliente cliente) {
		ocupada = false;
		System.out.println("El cliente " + cliente.numero + " ha dejado libre la caja " + numeroCaja);
		notify();
	}
	
	public void compra(int precio, Cliente cliente) {
		System.out.println("El cliente " + cliente.numero + " está realizando una compra de " + precio + " euros en la caja " + numeroCaja);
	}
}
