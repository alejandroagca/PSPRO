package com.AlejandroAgca;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunProcessPing {

	public static void main(String[] args) {
		if(args.length <= 0) {
			System.err.println("Se necesita como argumento un proceso a ejecutar");
		}
		ProcessBuilder pb = new ProcessBuilder(args);
		pb.redirectErrorStream(true);
		try {
			Process proceso = pb.start();
			MostrarSalidaProceso(proceso);
			System.exit(0);
		}
		catch(IOException e){
			System.err.println("Error en la E/S");
			System.exit(-1);
		}
		
	}
	private static void MostrarSalidaProceso(Process proceso) {
		int contador = 0;
		try {
			
			InputStreamReader lector = new InputStreamReader(proceso.getInputStream(),"utf-8");
			BufferedReader br = new BufferedReader(lector);
			
			String linea;
			
			while((linea = br.readLine()) != null && (contador <= 5)) 
			{
				System.out.println(linea);
				contador++;
			}
			
			if(proceso != null) 
			{
				proceso.destroy();
			}
			
				proceso.waitFor();
				System.out.println("Salida del proceso hijo:" + proceso.exitValue());
		}
		catch(InterruptedException e){
			System.out.println("No se pudo esperar al fin del proceso hijo");
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
}
