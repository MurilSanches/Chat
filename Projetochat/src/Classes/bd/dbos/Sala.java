package Classes.bd.dbos;

/**
* @author Arthur Kenji Balduino e Murilo Sanches de Paula.
* @RA 18176 e 18187.
* @since 2018.
**/
public class Sala 
{
    private int codigo;
    private String nome;
    private int qntMax;
    
    public Sala(int cod, String n, int limite) throws Exception
    {
        this.setCodigo(cod);
        this.setNome(n);
        this.setLimite(limite);
    }
    
    public int getCodigo()
    {
        return this.codigo;
    }
    
    public void setCodigo(int cod) throws Exception
    {
        if(new Integer(cod) == null || cod <= 0)
            throw new Exception ("SALA: Parametro nulo");
        this.codigo = cod;
    }
    
    public String getNome()
    {
        return this.nome;
    }
    
    public void setNome(String n) throws Exception
    {
        if(n == null || n == "")
            throw new Exception ("SALA: Parametro nulo");
        this.nome = n;
    }
    
    public int getLimite()
    {
        return this.qntMax;
    }
    
    public void setLimite(int max) throws Exception
    {
        if(new Integer(max) == null || max <= 0)
            throw new Exception ("SALA: Parametro nulo");
        this.qntMax = max;
    }

    public int hashCode()
    {
        int ret = 666;
        ret = ret * 11 + new Integer(this.codigo).hashCode();
        ret = ret * 11 + new Integer(this.qntMax).hashCode();
        ret = ret * 11 + this.nome.hashCode();
        return ret;
    }
    
    public String toString()
    {
        String ret = "";
        
        ret = "Nome : " + this.nome + " Codigo Sala : " + this.codigo + " Limite : " + this.qntMax;
        return ret;
    }
    
    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        if(!this.getClass().equals(obj.getClass()))
            return false;
        if(this.equals(obj))
            return true;
        Sala sala = (Sala) obj;
        if(this.codigo != sala.codigo)
            return false;
        if(this.nome != sala.nome)
            return false;
        if(this.qntMax != sala.qntMax)
            return false;        
        return true;
    }
}
