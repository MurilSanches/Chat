package Classes.Coisa;
public class AvisoDeEntradaNaSala implements Coisa
{
	private String remetente;

	public AvisoDeEntradaNaSala(String r) throws Exception
	{
		if(r == null || r == "")
			throw new Exception("Usuario fornecido � null");

		this.remetente = r;
	}

	public String getRemetente()
	{
		return this.remetente;
	}
}