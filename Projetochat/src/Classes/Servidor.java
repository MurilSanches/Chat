package Classes;
import java.io.*;
import java.net.*;
import Classes.principais.*;

public class Servidor
{
	public static void main(String[] args)
	{
		try
		{
			Salas salas = new Salas();
			 for(int i = 1; i < 10; i++)
	                if(Classes.bd.daos.SalasBd.cadastrado(i))
	                {
	                    Classes.bd.dbos.Sala sala = Classes.bd.daos.SalasBd.getSala(i);                    
	                    salas.addSala(new Classes.principais.Sala(sala.getNome(), sala.getLimite()));                       
	                }  
	                else 
	                	break;

			ServerSocket pedido = new ServerSocket(23434);
			System.out.println("Servidor rodando . . . ");
			for(;;)
			{
				Socket conexao = pedido.accept();
				CuidadoraDeUsuario cuidadora = new CuidadoraDeUsuario(conexao, salas);
				cuidadora.start();				
			}
		}
		catch(Exception err)
		{
			System.err.println(err.getMessage());
		}
	}
}