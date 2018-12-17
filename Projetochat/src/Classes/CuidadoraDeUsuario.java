package Classes;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import Classes.principais.*;
import Classes.Coisa.*;

/**
A classe CuidadoraDeUsuario é uma classe thread que serve de intermédio para todas as ações de um usuário.

@authors Felipe Melchior de Britto, Gabrielle da Silva barbosa e Christovam Alves Lemos.
@since 2018.
*/
public class CuidadoraDeUsuario extends Thread
{
	private Socket conexao;
	private Salas salas;
	private Usuario usuario;

	public CuidadoraDeUsuario(Socket conexao, Salas salas) throws Exception
	{
		if(conexao == null || salas == null)
			throw new Exception("Parâmetro null");

		this.conexao = conexao;
		this.salas = salas;
	}

	public void run()
	{
		try
		{
			ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
			ObjectInputStream  receptor    = new ObjectInputStream(conexao.getInputStream());
			
			Sala sala;

			ArrayList<String> nomeSalas = new ArrayList<String>(salas.getQtdSalas());

			for(int i=0; i<salas.getQtdSalas(); i++)
				nomeSalas.add(salas.getSala(i).getNome());

			transmissor.writeObject(new SalasDisponiveis(nomeSalas));
			transmissor.flush();
			for(;;)
			{
				Object obj = receptor.readObject();

				if(obj instanceof EscolhaDeSala)
				{
					String s = ((EscolhaDeSala)obj).getNomeSala();

					synchronized(salas)
					{
						if(!(salas.existeSala(s)))
						{
							transmissor.writeObject(new AvisoDeSalaInvalida());
							transmissor.flush();
						}
						else
						{
							sala = salas.getSala(s);
							if(sala.isCheia())
							{
								transmissor.writeObject(new AvisoDeSalaCheia());
								transmissor.flush();
						    }
							else
							{
								transmissor.writeObject(new AvisoDeSalaEscolhidaComSucesso());
								transmissor.flush();
								break;
							}
						}
					}

				}
			}
			String nome;
			for(;;)
			{
				Object obj = receptor.readObject();

				if(obj instanceof EscolhaDeNome)
				{
					nome = ((EscolhaDeNome)obj).getNome();
					if(sala.existeNome(nome))
					{
						transmissor.writeObject(new AvisoDeNomeExistente());
						transmissor.flush();
					}
					else
					{
						transmissor.writeObject(new AvisoDeNomeEscolhidoComSucesso());
						transmissor.flush();
						break;
					}
				}
			}

			ArrayList<String> usuarios;

			usuarios = new ArrayList<String>(sala.getLimite());

			for(int i=0; i<sala.getQtd(); i++)			
				usuarios.add(sala.getUsuario(i).getNome());

			transmissor.writeObject(new UsuariosNaSala(usuarios));
			transmissor.flush();
			
			usuario = new Usuario(nome, conexao, sala, transmissor, receptor);

			for(int i = 0; i < sala.getQtd();i++)
			{
				sala.getUsuario(i).envia(new AvisoDeEntradaNaSala(this.usuario.getNome()));
			}
			
			for(int i = 0; i < sala.getQtd(); i++)
			{
				usuario.envia(new AvisoDeEntradaNaSala(sala.getUsuario(i).getNome()));
			}

			sala.adicionarUsuario(usuario);		
			
			Coisa recebido = null;
			do
			{
				recebido = usuario.recebe();

				if(recebido instanceof Mensagem)
				{
					if(((Mensagem)recebido).getDestinatario().equals(""))
					{
						for(int i=0; i<this.usuario.getSala().getQtd(); i++)
							if(this.usuario.getSala().getUsuario(i) != this.usuario)
								this.usuario.getSala().getUsuario(i).envia(recebido);
					}
					else
					{
						String nomeDestinatario = ((Mensagem)recebido).getDestinatario();

						this.usuario.getSala().getUsuario(nomeDestinatario).envia(recebido);
					}

				}
			}
			while(!(recebido instanceof PedidoParaSairDaSala));

			this.usuario.getSala().removerUsuario(usuario);

			for(int i=0; i<this.usuario.getSala().getQtd(); i++)
				this.usuario.getSala().getUsuario(i).envia(new AvisoDeSaidaDaSala(this.usuario.getNome()));
			this.usuario.envia(new PedidoParaSairDaSala());
			this.usuario.fechaTudo();

		}
		catch(Exception err)
		{}
	}
}