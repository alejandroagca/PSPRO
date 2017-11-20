package com.AlejandroAgca;

public class Palillo {
	int numero;
	boolean enUso;
	
	public Palillo(int x) 
	{
		numero = x;
		enUso = false;
	}
	
	public synchronized void coger() {
		while (enUso) {
			System.out.println("El palillo " + numero + " está en uso. Espere.");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		enUso = true;
		System.out.println("El palillo " + numero + " está ocupado");
		//notify();
	}
	
	public synchronized void soltar() {
		enUso = false;
		System.out.println("El palillo " + numero + " está libre");
		notify();
	}
}
