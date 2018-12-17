package Classes.Coisa;
import java.util.ArrayList;
import Classes.principais.*;
import Classes.bd.dbos.*;

/**
 * @author Arthur Kenji Balduino e Murilo Sanches de Paula.
 * @RA 18176 e 18187.
 * @since 2018.
 **/
public class SalaUsuario
{
	/**
		ArrayList<Usuario> lista que mant�m controle dos usuarios da sala.
	*/
	protected ArrayList<Usuario> listaUsuarios;
	/**
		String nome da sala.
	*/
	protected String nome;
	/**
		Int c�digo da sala.
	*/
	protected int cod;
	/**
		Int quantidade m�xima de pessoas que podem ocupar a sala.
	*/
	protected int qtdMax;
	/**
		Int quantas pessoas est�o na sala.
	*/
	protected int qtdOcupado;

	/**
		Construtor de SalaUsuario, listaUsuario come�a vazia pois, quando uma sala � criada, ainda n�o h� usu�rios.

		@param n nome da sala.
		@param q quantidade maxima de pessoas.
		@param c codigo da sala.
		@throws Exception se o nome da sala passado � null ou vazio, n�o se verifica q ou c pois int n pode ser vazio.
	*/
	public SalaUsuario(String n, int q)throws Exception
	{
		if(n == null || n == "")
			throw new Exception("Par�metro para nome de sala null ou vazio");

		this.listaUsuarios = new ArrayList<Usuario>(q);
		this.nome = n;	
		this.qtdMax = q;
		this.qtdOcupado = 0;
	}

	/**
		Construtor da classe que utiliza de um dbo de Sala e pega os atributos dele, novamente
		inicializando listaUsuarios vazia.

		@param sala dbo.
		@throws Exception se o objeto do par�metro for null.
	*/
	public SalaUsuario(Classes.bd.dbos.Sala sala)throws Exception
	{
		if(sala == null)
		   throw new Exception("Objeto de sala vazio");

		this.listaUsuarios = new ArrayList<Usuario>(sala.getLimite());
		this.nome = sala.getNome();
		this.cod = sala.getCodigo();
		this.qtdMax = sala.getLimite();
		this.qtdOcupado = 0;
	}

    /**
		M�todo que retorna nome da sala.

		@return nome da sala.
	*/
	public String getNome()
	{
		return this.nome;
	}

	/**
		M�todo que retorna quantidade m�xima de usuarios na sala.

		@return quantidade m�xima de usu�rios.
	*/
	public int getQtdMax()
	{
		return this.qtdMax;
	}

	/**
		M�todo que retorna o c�digo da sala.

		@return c�digo da sala.
	*/
	public int getCod()
	{
		return this.cod;
	}

	/**
		M�todo que retorna quantos usu�rios h� na sala.

		@return nome da sala.
	*/
	public int getQtdOcupado()
	{
		return this.qtdOcupado;
	}

	/**
		M�todo que retorna se a sala est� cheia.

		@return true ou false se a sala estiver cheia ou n�o.
	*/
	public boolean isCheia()
	{
		if(this.qtdOcupado == this.qtdMax)
			return true;

		return false;
	}

    /**
		M�todo que retorna um usu�rio da sala de acordo com o index passado.

		@param int index para o get da ArrayList.
		@return usuario procurado.
		@throws Exception se o index for uma posi��o inv�lida.
	*/
	public Usuario getUsuario(int index)throws Exception
	{
		if(index<0 || index>qtdOcupado-1)
			throw new Exception("index fora dos limites");

		return listaUsuarios.get(index);
	}

	/**
		M�todo que retorna um usu�rio da sala de acordo com o index passado.

		@param nome do usuario a ser procurado.
		@return usuario procurado.
		@throws Exception se o nome passado for null ou vazio ou se n�o houver nenhum usuario que tenha tal nome na sala.
	*/
	public Usuario getUsuario(String nome)throws Exception
	{
			if(nome == null || nome == "")
				throw new Exception("Par�metro null");

			for(int i=0; i<this.qtdOcupado; i++)
				if(this.listaUsuarios.get(i).getNome().trim().equals(nome.trim()))
					return listaUsuarios.get(i);

			throw new Exception("Usuario n�o consta na sala");
	}

	/**
		M�todo que adiciona um usu�rio � lista de usuarios da sala.

		@param usuario a ser adicionado.
		@throws Exception se o usuario passado for null ou se a sala estiver cheia.
	*/
	public void adicionarUsuario(Usuario usuario) throws Exception
	{
		if(usuario == null)
			throw new Exception("usuario passado � null");

		if(!this.isCheia())
		{
			this.listaUsuarios.add(usuario);
			this.qtdOcupado++;
		}
		else
			throw new Exception("sala cheia");
	}

	/**
		M�todo que remove um usu�rio da sala.

		@param usuario a ser removido.
		@return usuario procurado.
		@throws Exception se o nome passado for null ou vazio ou se n�o houver nenhum usuario que tenha tal nome na sala.
	*/
	public void removerUsuario(Usuario usuario)throws Exception
	{
		if(!(listaUsuarios.contains(usuario)))
			throw new Exception("usuario n�o existe nessa sala");

		this.listaUsuarios.remove(usuario);
		this.qtdOcupado--;
	}

	/**
		M�todo que verifica se existe um usuario na sala com o nome passado por par�metro.

		@param nome do usuario a ser procurado.
		@return true ou false dependendo da exist�ncia do nome passado.
		@throws Exception se o nome passado for null ou vazio.
	*/
	public boolean existeNome(String nome)throws Exception
	{
		if(nome == null || nome == "")
			throw new Exception("nome passado � null ou vazio");

		for(int i=0; i<this.qtdOcupado;i++)
			if(this.listaUsuarios.get(i).getNome().trim().equals(nome.trim()))
				return true;

		return false;
	}

	/**
		M�todo que retorna uma String com informa��es da classe.

		@return String contendo nome da sala, c�digo e quantidade ocupada em rela��o � m�xima.
	*/
	public String toString()
	{
		return "Nome da sala: " + this.nome + " C�digo: " + this.cod + " Lugares ocupados: " + this.getQtdOcupado() + "/" + this.getQtdMax();
	}

	/**
		M�todo que compara dois objetos da classe verificando seus atributos.

		@param Objeto para compara��o.
		@return true ou false de acordo com a igualdade.
	*/
	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(obj == null)
			return false;

		if(obj.getClass() != this.getClass())
			return false;

		SalaUsuario sala = (SalaUsuario)obj;

		if(this.cod != sala.cod)
			return false;

		return true;
	}

	/**
		M�todo que cria um c�digo para um objeto da classe.

		@return int c�digo formado.
	*/
	public int hashCode()
	{
		int ret = 5;

		ret = ret*2 + new Integer(this.cod).hashCode();

		ret = ret*2 + new Integer(this.qtdMax).hashCode();

		ret = ret*2 + this.nome.hashCode();

		ret = ret*2 + this.listaUsuarios.hashCode();

		return ret;
	}
}