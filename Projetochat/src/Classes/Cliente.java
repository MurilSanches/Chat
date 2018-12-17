package Classes;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.util.ArrayList;
import Classes.Coisa.*;

public class Cliente //instancia janela
{
	public static void main(String[] args)
	{
		try
		{
			for(;;)
			{			
				Socket conexao = null;				
				for(;;)
				{                
						String ip = JOptionPane.showInputDialog(null,"Digite o ip do servidor a conectar : (Exemplo: 177.220.18.111)");

						if(ip == "" || ip == null)
							continue;

						conexao = new Socket(ip, 23434);
						break;                           
				}      			
				JanelaPrincipal janela = new JanelaPrincipal(conexao);

				ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream());
				
				Coisa recebido = null;
				for(;;)
				{
					recebido = (Coisa)receptor.readObject();

					if(recebido instanceof SalasDisponiveis)
					   janela.carregarSalas(((SalasDisponiveis)recebido).getNomeSala());

						else if(recebido instanceof AvisoDeSalaCheia)
							janela.avisarErro("A sala escolhida está cheia!");

							else if(recebido instanceof AvisoDeSalaInvalida)
								janela.avisarErro("A sala escolhida é inválida!");

								else
									break;
				}
			
				for(;;)
				{
					recebido = (Coisa)receptor.readObject();

					if(recebido instanceof AvisoDeNomeEscolhidoComSucesso)
						break;

					else 
						if (recebido instanceof AvisoDeNomeExistente)
						janela.avisarErro("O nome escolhido já está sendo usado na sala");
				}

				recebido = (Coisa)receptor.readObject();

				if(recebido instanceof UsuariosNaSala)
				{
					janela.mostrarDesignDeChat(((UsuariosNaSala)recebido).getUsuarios());

					for(;;)
					{
						recebido = (Coisa)receptor.readObject();

						if(recebido instanceof Mensagem)
						{
							if(((Mensagem)recebido).getDestinatario().equals(""))
							{								
								janela.mostra(((Mensagem)recebido).getMensagem(), ((Mensagem)recebido).getRemetente());
							}
							else
							{								
								janela.mostraPriv(((Mensagem)recebido).getMensagem(), ((Mensagem)recebido).getRemetente());								
							}
						}

						else if(recebido instanceof AvisoDeEntradaNaSala)
						{
							janela.mostraEntrada(((AvisoDeEntradaNaSala)recebido).getRemetente());
						}

						else if(recebido instanceof AvisoDeSaidaDaSala)
							janela.mostraSaida(((AvisoDeSaidaDaSala)recebido).getRemetente());


						else if(recebido instanceof PedidoParaSairDaSala)
							break;
					}

					janela.fecharTudo();
					receptor.close();
				}
			}

	    }
	    catch(Exception err)
	    {
			System.err.println(err.getMessage());
		}
	}
}