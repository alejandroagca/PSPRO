package com.AlejandroAgca;

class Alumno extends Thread 
{
	String _nombre;
	Bienvenida _saludo;
	public Alumno(String nombre, Bienvenida saludo) 
	{
		_nombre = nombre;
		_saludo = saludo;
	}
	
	@Override
	public void run() 
	{
		try {
			Thread.sleep(1000);
			_saludo.SaludarAlProfesor(this._nombre);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

class Profesor extends Thread 
{
	String _nombre;
	Bienvenida _saludo;
	public Profesor(String nombre, Bienvenida saludo) 
	{
		_nombre = nombre;
		_saludo = saludo;
	}
	
	@Override
	public void run() 
	{
		try {
			Thread.sleep(2000);
			_saludo.ProfesorSaluda();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}

class Bienvenida {
	private boolean _comienzoClase;
	
	public Bienvenida() 
	{
		_comienzoClase = false;
	}
	
	public synchronized void SaludarAlProfesor(String alumno) 
	{
		System.out.println("El alumno " + alumno + " quiere saludar");
		while(_comienzoClase == false) 
		{
			try {
				wait();
				System.out.println("El alumno " + alumno + " dice ¡Buenos Dias!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	public synchronized void ProfesorSaluda() 
	{
		System.out.println("El profesor dice: ¡Buenos Días!");
		_comienzoClase = true;
		this.notifyAll();
	}
}

public class ComienzoClase {
	public static void main(String[] args) {
		Bienvenida saludo = new Bienvenida();
		int nAlumnos = Integer.parseInt(args[0]);
		for (int i = 0; i < nAlumnos; i++) {
			Alumno alumno = new Alumno("Alumno " + i, saludo);
			alumno.start();
		}
		Profesor profe = new Profesor("Eliseo",saludo);
		profe.start();
	}
}
