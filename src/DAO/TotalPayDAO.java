package DAO;


import java.sql.PreparedStatement;
import java.sql.SQLException;

import Aplicacao.TotalPay;
import Aplicacao.Transacao;
import conexao.Conexao;

public class TotalPayDAO {
    public void TotalTipoPay(TotalPay TotalPay) {

        String sql = "SELECT tipoPag, COUNT(tipoPag), SUM(valor) FROM projeto.transacao group by tipoPag;";

        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, TotalPay.getTipoPag());
            ps.setDouble(2, TotalPay.getCount());
            ps.setString(3, TotalPay.getSum());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
