package Classes.bd.daos;

import java.sql.*;
import Classes.bd.*;
import Classes.bd.core.*;
import Classes.bd.dbos.*;

public class SalasBd 
{
    public static boolean cadastrado (int codigo) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * FROM Salas WHERE CodSala = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, codigo);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();

            retorno = resultado.first();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar Sala");
        }

        return retorno;
    }

    public static void incluir (Sala sala) throws Exception
    {
        if (sala==null)
            throw new Exception ("Sala nao fornecida");

        try
        {
            String sql;

            sql = "INSERT INTO Salas(CodSala, Nome, QntMaximaUsuarios) VALUES (?,?,?)";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt    (1, sala.getCodigo ());
            BDSQLServer.COMANDO.setString (2, sala.getNome ());
            BDSQLServer.COMANDO.setInt  (3, sala.getLimite());

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao inserir sala");
        }
    }

    public static void excluir (int codigo) throws Exception
    {
        if (!cadastrado (codigo))
            throw new Exception ("Nao cadastrada");

        try
        {
            String sql;

            sql = "DELETE FROM Sala WHERE CodSala=?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, codigo);

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao excluir sala");
        }
    }

    public static void alterar (Sala sala) throws Exception
    {
        if (sala==null)
            throw new Exception ("Livro nao fornecido");

        if (!cadastrado (sala.getCodigo()))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "UPDATE Salas SET Nome=? SET QntMaximaUsuarios=? WHERE CodSala = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setString (1, sala.getNome ());
            BDSQLServer.COMANDO.setInt  (2, sala.getLimite ());
            BDSQLServer.COMANDO.setInt    (3, sala.getCodigo ());

            BDSQLServer.COMANDO.executeUpdate ();
            BDSQLServer.COMANDO.commit        ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao atualizar dados de sala");
        }
    }

    public static Sala getSala (int codigo) throws Exception
    {
        Sala sala = null;

        try
        {
            String sql;

            sql = "SELECT * FROM Salas WHERE CodSala = ?";

            BDSQLServer.COMANDO.prepareStatement (sql);

            BDSQLServer.COMANDO.setInt (1, codigo);

            MeuResultSet resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
            
            if (!resultado.first())
                throw new Exception ("Nao cadastrado");

            sala = new Sala (resultado.getInt("CodSala"),
                             resultado.getString("Nome"),
                             resultado.getInt ("QntMaximaUsuarios"));
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar sala");
        }

        return sala;
    }

    public static MeuResultSet getSalas () throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * FROM Salas";

            BDSQLServer.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)BDSQLServer.COMANDO.executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar salas");
        }

        return resultado;
    }
}
