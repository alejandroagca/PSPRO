package com.AlejandroAgca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Ejercicio3 {
	public static int [] datos; 
	public static void main(String[] args) {
		datos = new int[3];
		Hilo[] hilos = new Hilo[args.length];
		long inicio,fin;
		inicio = System.currentTimeMillis();
		for (int i = 0; i < hilos.length; i++) {
			hilos[i] = new Hilo(args[i]);
		}
		
		for (int i = 0; i < hilos.length; i++) {
			hilos[i].start();
		}
		
		for (int i = 0; i < hilos.length; i++) {
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		fin = System.currentTimeMillis() - inicio;
		System.out.println(fin);
		inicio = System.currentTimeMillis();
		String [] comandos = new String[args.length + 1];
		comandos[0] = "wc";
		for (int i = 0; i < args.length; i++) {
			comandos[i+1] = args[i];
		}
		
		ProcessBuilder proceso = new ProcessBuilder(comandos);
		try {
			Process unProceso = proceso.start();
			InputStream is = unProceso.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			String linea;
			
			while((linea = br.readLine())!=null) {
				System.out.println(linea);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		fin = System.currentTimeMillis() - inicio;
		System.out.println(fin);
	}
}

class Hilo extends Thread{
	String argumento;
	public Hilo(String argumento) {
		this.argumento = argumento;
	}
	
	@Override
	public void run() {
		ProcessBuilder proceso = new ProcessBuilder("wc", argumento);
		Process unProceso;
		try {
			unProceso = proceso.start();
			InputStream is = unProceso.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);			
			String linea;
			while((linea = br.readLine())!=null) {
				System.out.println(linea);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	}
}