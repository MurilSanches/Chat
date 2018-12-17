package Classes.principais;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import Classes.Coisa.*;


/**
 * @author Arthur Kenji Balduino e Murilo Sanches de Paula.
 * @RA 18176 e 18187.
 * @since 2018.
 **/
public class Usuario
{
 	protected String nick;
	protected Socket conexao;
	protected ObjectOutputStream transmissor;
	protected ObjectInputStream receptor;
	protected Sala sala;

	/**
	 * 
	 * @param nick String do nome do Usuario
	 * @param s    Socket 
	 * @param sa   Sala
	 * @param oos
	 * @param ois
	 * @throws Exception caso algum parametro for nulo
	 */
	public Usuario(String nick, Socket s, Sala sl, ObjectOutputStream oos, ObjectInputStream ois) throws Exception 	
	{
		if(nick == null || s == null || sl == null || ois == null || oos == null)
		   throw new Exception("USUARIO : Algum parametro do construtor vazio");

		this.nick        = nick;
		this.conexao     = s;
		this.sala        = sl;
		this.transmissor = oos;
		this.receptor    = ois;
	}
	
	/**
	 *
	 * @param sala sala a ser atribuida para atributo o sala
	 * @throws Exception caso o parametro for nulo
	 */
	public void setSala(Sala sl) throws Exception
	{
		if(sl == null)
			throw new Exception("USUARIO :Parametro ao adicionar a sala nao pode ser nulo");
		this.sala=sl;
	}

	/**
	 *@param nome nome do usuario
	 *@throws Exception se o parametro for nulo
	 **/
	public void setNome(String nome) throws Exception
	{
		if(nome == null || nome.equals(""))
			throw new Exception("USUARIO :Parametro ao mudar o nome do usuario nao pode ser nulo");

		this.nick = nome;
	}

	/**
	 * 
	 * @return retorna o nome do usuario
	 */
	public String getNome()
	{
		return this.nick;
	}
	
	/**
	 * 
 	 * @return retorna a sala na qual o usuario se encontra
	 */
	public Sala getSala()
	{
		return this.sala;
	}

	/**
	 * 
	 * @throws Exception quando o objeto que deseja ser enviado é null
	 */
	public void envia(Coisa x) throws Exception
	{
		if(x == null)
			throw new Exception("Objeto enviado null");

		transmissor.writeObject(x);
		transmissor.flush();
	}


	/**
	 * 
	 * @return retorna  o objeto recebido
	 * @throws Exception caso o objeto lido nao seja uma Coisa
	 */
	public Coisa recebe() throws Exception
	{
		Object obj = this.receptor.readObject();

		if(!(obj instanceof Coisa))
			throw new Exception("Objeto lido não é Coisa");

		return (Coisa)obj;
	}

	public void fechaTudo() throws Exception
	{
		this.transmissor.close();
		this.receptor.close();
		this.conexao.close();
	}


	/**
	 * 
	 * @return retorna uma String
	 */
	public String toString()
	{
		return "Nome: "           + this.nick + 
				", Sala: "        + this.sala.getNome() + 
				", Conexao: "     + this.conexao.toString() + 
				", Receptor: "    + this.receptor.toString() + 
				", Transmissor: " + this.transmissor.toString();
	}

	/**
	 * 
	 * @return verdadeiro ou falso dependendo se as variáveis e os objetos são iguais ou não.
	 */
	public boolean equals (Object obj)
	{
		if (this == obj)
		    return true;

		if (obj == null)
		    return false;

		if (this.getClass() != obj.getClass())
		    return false;

		Usuario u = (Usuario) obj;

		if (this.nick != u.nick)
		    return false;
		
		if(this.sala != u.sala)
			return false;

		if(this.conexao != u.conexao)
		   return false;

		if(this.receptor != u.receptor)
		   return false;

		if(this.transmissor != u.transmissor)
		   return false;

		return true;
	}

	/**
	 * 
	 * @return retorna o inteiro formado como código pelo método hashCode().
	 */
	public int hashCode()
	{
		int ret = 666;

		ret = ret * 11 + this.nick.hashCode();
		ret = ret * 11 + this.sala.hashCode();
		ret = ret * 11 + this.conexao.hashCode();
		ret = ret * 11 + this.receptor.hashCode();
		ret = ret * 11 + this.transmissor.hashCode();

		return ret;
	}
}