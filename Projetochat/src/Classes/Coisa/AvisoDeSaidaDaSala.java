package Classes.Coisa;
public class AvisoDeSaidaDaSala implements Coisa
{
	private String remetente;

	public AvisoDeSaidaDaSala(String r) throws Exception
	{
		if(r == null || r == "")
			throw new Exception("Usuario fornecido é null");

		this.remetente = r;
	}

	public String getRemetente()
	{
		return this.remetente;
	}
}