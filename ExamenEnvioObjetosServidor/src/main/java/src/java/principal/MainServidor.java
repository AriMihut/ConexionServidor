package src.java.principal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import pojo.Persona;

public class MainServidor {

	static ArrayList<Persona> personas = new ArrayList<Persona>();
	private static ObjectInputStream flujoEntrada;
	private static Persona persona;
	private static ObjectOutputStream flujoSalida;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		int puerto = 6000;
		ServerSocket servidor = new ServerSocket(puerto);
		System.out.println("Servidor iniciado...");
		Socket cliente = servidor.accept();
		
		flujoEntrada = new ObjectInputStream(cliente.getInputStream()); 
		flujoSalida = new ObjectOutputStream(cliente.getOutputStream());
		
		do {
			
			persona = (Persona) flujoEntrada.readObject();
			personas.add(persona);
			System.out.println("La persona que llega al Servidor es: " + persona.getNombre() + " " + persona.getEdad());
			calcularEdad();
		}while(flujoEntrada != null);
		
		flujoEntrada.close();
		flujoSalida.close();
		cliente.close();
		servidor.close();
	}

	private static void calcularEdad() throws IOException {
		
		for(int i = 0; i < personas.size(); i++) {
			if(personas.get(i).getEdad() >= 18) {
				flujoSalida.writeObject(persona);
				System.out.println("El nombre de la persona que ha llegado es: " + personas.get(i).getNombre() + " y es mayor de edad");
			}else {
				System.out.println("La persona que ha llegado es menor de edad.");
			}
		}
		
	}

}
