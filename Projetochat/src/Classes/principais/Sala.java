package Classes.principais;
import java.util.ArrayList;

public class Sala
{
	protected ArrayList<Usuario> listaUsuarios;
	protected String nome;
	protected int limite;
	protected int qtd;

	public Sala(String n, int q)
	{
		this.listaUsuarios = new ArrayList<Usuario>(q);
		this.nome = n;		
		this.limite = q;
		this.qtd = this.listaUsuarios.size();
	}

	public void setNome(String n)
	{
		this.nome = n;
	}

	public void setLimite(int q)
	{
		this.limite = q;
	}

	public String getNome()
	{
		return this.nome;
	}

	public int getLimite()
	{
		return this.limite;
	}

	public int getQtd()
	{
		return this.qtd;
	}

	public boolean isCheia()
	{
		if(this.qtd == this.limite)
			return true;

		return false;
	}

	public Usuario getUsuario(int i)throws Exception
	{
		if(i < 0 || i > qtd - 1)
			throw new Exception("index fora dos limites");

		return listaUsuarios.get(i);
	}

	public Usuario getUsuario(String nome)throws Exception
	{
			if(nome == null || nome == "")
				throw new Exception("Parâmetro null");

			for(int i = 0; i < this.qtd; i++)
				if(this.listaUsuarios.get(i).getNome().trim().equals(nome.trim()))
					return listaUsuarios.get(i);

			throw new Exception("Usuario não esta na sala");
	}

	public void adicionarUsuario(Usuario usuario) throws Exception
	{
		if(!this.isCheia())
		{
			this.listaUsuarios.add(usuario);
			this.qtd++;
		}
		else
			throw new Exception("sala cheia");
	}

	public void removerUsuario(Usuario usuario)throws Exception
	{
		if(!(listaUsuarios.contains(usuario)))
			throw new Exception("usuario não existe nessa sala");

		this.listaUsuarios.remove(usuario);
		this.qtd--;
	}

	public boolean existeNome(String nome)
	{
		for(int i = 0; i < this.qtd; i++)
			if(this.listaUsuarios.get(i).getNome().trim().equals(nome.trim()))
				return true;

		return false;
	}

	public String toString()
	{
		String ret = "Nome da sala: " + this.nome +  " Usuarios :("+this.qtd+"): ";
		for(int i = 0; i < this.qtd; i++)
			ret += this.listaUsuarios.get(i).getNome() + ", ";
		return ret;
	}

	public boolean equals(Object obj)
	{
		if(obj == this)
			return true;

		if(obj == null)
			return false;

		if(obj.getClass() != this.getClass())
			return false;

		Sala sala = (Sala)obj;
		
		if(this.limite != sala.limite)
			return false;
		if(this.listaUsuarios != sala.listaUsuarios)
			return false;
		if(this.nome != sala.nome)
			return false;
		if(this.qtd  != sala.qtd)
			return false;

		return true;
	}

	public int hashCode()
	{
		int ret = 666;

		ret = ret * 11 + new Integer(this.limite).hashCode();
		ret = ret * 11 + new Integer(this.qtd).hashCode();
		ret = ret * 11 + this.nome.hashCode();
		ret = ret * 11 + this.listaUsuarios.hashCode();

		return ret;
	}
}