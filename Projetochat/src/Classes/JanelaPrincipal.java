package Classes;

import Classes.Coisa.*;
import Classes.principais.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class JanelaPrincipal extends JPanel
{
	protected JFrame frmChat;    
	protected JButton btnEnviar;
	protected JTextField txtMensagem;        
	protected JList<String> listaUsuarios;
	protected JTextArea txtConversa;
	protected JComboBox<String> cbxSalas;      
	protected JButton btnSair;
	protected Socket conexao;
	protected ObjectOutputStream transmissor;
	protected Usuario usuario;	
	protected ArrayList<String> nomesSala;
	protected ArrayList<String> nomesUsuarios; 	
	protected DefaultListModel<String> usuarios;
	private String nomeUsuario;

	public JanelaPrincipal(Socket conexao) throws Exception
	{
		if(conexao == null)
			throw new Exception("JANELA : Parametros nulos");        
		this.conexao    = conexao;
		this.transmissor = new ObjectOutputStream(conexao.getOutputStream());
		this.usuarios 	= new DefaultListModel<String>();  
		this.initComponents();  
		this.btnSair.setEnabled(true);
	}

	@SuppressWarnings("unchecked")
	private void initComponents()
	{
		TratadorDeEvento tratador = new TratadorDeEvento();
		
		this.frmChat = new JFrame();
		this.frmChat.setResizable(false);
		this.frmChat.setTitle("Chat");
		this.frmChat.setBounds(50, 50, 857, 599);
		this.frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frmChat.getContentPane().setLayout(null);
		this.frmChat.setVisible(true);

		JScrollPane jScrollPane1 = new JScrollPane();
		jScrollPane1.setBounds(10, 96, 639, 389);
		this.frmChat.getContentPane().add(jScrollPane1);

		this.txtConversa = new JTextArea();
		this.txtConversa.setEditable(false);
		this.txtConversa.setEnabled(false);
		jScrollPane1.setViewportView(txtConversa);

		JScrollPane jScrollPane2 = new JScrollPane();
		jScrollPane2.setBounds(659, 96, 161, 425);
		this.frmChat.getContentPane().add(jScrollPane2);

		this.listaUsuarios = new JList<String>(this.usuarios);
		this.listaUsuarios.setEnabled(false);
		this.listaUsuarios.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) 
				{      
					try {
						String remetente = listaUsuarios.getSelectedValue();					
						String mensagem  = JOptionPane.showInputDialog("Escreva a sua mensagem para" + remetente);
						
						transmissor.writeObject(new Mensagem(remetente, nomeUsuario, mensagem));
						transmissor.flush();
					}
					catch(Exception e)
					{}
				}
			}
		});
		jScrollPane2.setViewportView(listaUsuarios);

		this.txtMensagem = new JTextField();
		this.txtMensagem.setBounds(10, 496, 528, 25);
		this.frmChat.getContentPane().add(this.txtMensagem);
		this.txtMensagem.setColumns(10);
		this.txtMensagem.setEnabled(false);

		this.btnEnviar = new JButton("Enviar");
		this.btnEnviar.setEnabled(false);
		this.btnEnviar.setBounds(544, 496, 105, 25);
		this.btnEnviar.addActionListener(tratador);
		this.frmChat.getContentPane().add(this.btnEnviar);

		JLabel jTitulo = new JLabel();
		jTitulo.setText("Chat");
		jTitulo.setFont(new Font("Tahoma", 0, 24));
		jTitulo.setBounds(380, 0, 100, 100);
		this.frmChat.getContentPane().add(jTitulo);

		this.btnSair = new JButton("Conectar");
		this.btnSair.setBounds(685, 55, 105, 25);
		this.btnSair.setEnabled(false);
		this.frmChat.getContentPane().add(this.btnSair);
		this.btnSair.addActionListener(tratador);

		JLabel jSalas = new JLabel();
		jSalas.setText("Salas :");
		jSalas.setFont(new Font("Tahoma", 0, 24));
		jSalas.setBounds(10, 45, 100, 50);
		this.frmChat.getContentPane().add(jSalas);

		this.cbxSalas = new JComboBox<String>();
		this.cbxSalas.setBounds(90, 55, 100, 30);
		this.frmChat.getContentPane().add(this.cbxSalas);        
	} 

	public void mostrarDesignDeChat(ArrayList<String> usuarios)
	{
		this.frmChat.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.cbxSalas.setEnabled(false);

		this.txtMensagem.setEnabled(true);
		this.btnSair.setText("Sair");
		this.btnEnviar.setEnabled(true);
		this.txtConversa.setEnabled(true);
		this.btnSair.setEnabled(true);
		this.listaUsuarios.setEnabled(true);
		
		for(int i = 0; i < usuarios.size(); i++)
			this.usuarios.addElement(usuarios.get(i));;
	}
	
	public void carregarSalas(ArrayList<String> salas)
	{
		for(int i = 0; i < salas.size(); i++)
			this.cbxSalas.addItem(salas.get(i));
	}

	public void avisarErro(String erro)
	{
		JOptionPane.showMessageDialog(null, erro);
	}

	public void mostra(String texto, String r)
	{
		txtConversa.append(r + ": " + texto + "\n");                         
	}

	public void mostraPriv(String texto, String r)
	{
		txtConversa.append("Mensagem privada de "+ r + ": " + texto + "\n");
						 
	}

	public void mostraEntrada(String r)
	{
		txtConversa.append(r + " entrou na sala\n");

		boolean jaEstaNoComboBox = false;

		for(int i = 0; i < this.usuarios.size();i++)
			if(this.usuarios.get(i).equals(r.trim()))
				jaEstaNoComboBox = true;

		if(jaEstaNoComboBox == false)
		{
			this.usuarios.addElement(r);
		}
	}

	public void mostraSaida(String r)
	{
		txtConversa.append(r + " saiu da sala\n");

	    for(int i=0; i<usuarios.size(); i++)
	    	if(usuarios.get(i).equals(r.trim()))
	    	{
	    		this.usuarios.removeElement(r);
	    	}
	}

	public void fecharTudo() throws Exception
	{
		this.conexao.close();
		this.transmissor.close();
		this.frmChat.dispose();
	}

	private class TratadorDeEvento implements ActionListener
	{

		public void trateClickNoBotaoConectar()
		{
			try
			{
				String s = cbxSalas.getSelectedItem()+"";
				transmissor.writeObject(new EscolhaDeSala(s));
				transmissor.flush();
											
				for(;;)
				{
					s = JOptionPane.showInputDialog("Digite um nome de usuario");
					
					if(s == null || s.trim().equals(""))
					{
						avisarErro("Nome inválido");			
					}
					else 
					{			
						transmissor.writeObject(new EscolhaDeNome(s));
						transmissor.flush();
						nomeUsuario = s;
						break;
					}
				}
			}
			catch(Exception err)
			{}
		}

		public void trateClickNoBotaoEnviar()
		{
			String s = txtMensagem.getText();

			try
			{
				if(s != null || !(s.trim().equals("")))
				{
					mostra(s, "Você");

					transmissor.writeObject(new Mensagem(s, nomeUsuario));
					transmissor.flush();
					txtMensagem.setText("");
				}
			}
			catch(Exception err)
			{}
		}

		public void trateClickNoBotaoSair()
		{
			try
			{
				transmissor.writeObject(new PedidoParaSairDaSala());
				transmissor.flush();
			}
			catch(Exception err)
			{}
		}


		public void actionPerformed (ActionEvent e)
		{
			String comando = e.getActionCommand();

			if(comando == "Conectar")
			{
				trateClickNoBotaoConectar();
			}
			if(comando == "Enviar")
			{
				trateClickNoBotaoEnviar();
			}
			if(comando == "Sair")
			{
				trateClickNoBotaoSair();
			}

		}
	}
}
