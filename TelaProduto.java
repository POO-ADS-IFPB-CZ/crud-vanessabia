import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author vanessa
 */
public class TelaProduto extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private ProdutoDao produtoDao;

    public TelaProduto() {
        setTitle("Cadastro de Produtos");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        produtoDao = new ProdutoDao();

        modelo = new DefaultTableModel(new Object[]{"Código", "Descrição", "Preço"}, 0);
        tabela = new JTable(modelo);
        carregarTabela();

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnRemover = new JButton("Remover");

        JPanel botoes = new JPanel();
        botoes.add(btnAdicionar);
        botoes.add(btnAtualizar);
        botoes.add(btnRemover);

        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> abrirFormulario(null));
        btnAtualizar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row >= 0) {
                Produto p = new Produto(
                        Integer.parseInt(tabela.getValueAt(row, 0).toString()),
                        tabela.getValueAt(row, 1).toString(),
                        Double.parseDouble(tabela.getValueAt(row, 2).toString())
                );
                abrirFormulario(p);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto.");
            }
        });

        btnRemover.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row >= 0) {
                int codigo = Integer.parseInt(tabela.getValueAt(row, 0).toString());
                produtoDao.remover(new Produto(codigo, "", 0));
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto.");
            }
        });
    }

    private void carregarTabela() {
        modelo.setRowCount(0);
        for (Produto p : produtoDao.listar()) {
            modelo.addRow(new Object[]{p.getCodigo(), p.getDescricao(), p.getPreco()});
        }
    }

    private void abrirFormulario(Produto produto) {
        JTextField campoCodigo = new JTextField();
        JTextField campoDescricao = new JTextField();
        JTextField campoPreco = new JTextField();

        if (produto != null) {
            campoCodigo.setText(String.valueOf(produto.getCodigo()));
            campoDescricao.setText(produto.getDescricao());
            campoPreco.setText(String.valueOf(produto.getPreco()));
            campoCodigo.setEnabled(false);
        }

        JPanel painel = new JPanel(new GridLayout(0, 1));
        painel.add(new JLabel("Código:"));
        painel.add(campoCodigo);
        painel.add(new JLabel("Descrição:"));
        painel.add(campoDescricao);
        painel.add(new JLabel("Preço:"));
        painel.add(campoPreco);

        int result = JOptionPane.showConfirmDialog(this, painel, "Produto",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
          try {
    int codigo = Integer.parseInt(campoCodigo.getText());
    String descricao = campoDescricao.getText();
    double preco = Double.parseDouble(campoPreco.getText());

    Produto novoProduto = new Produto(codigo, descricao, preco);

    if (produto == null) {
        produtoDao.adicionar(novoProduto); // Pode lançar exceção se já existir
    } else {
        produtoDao.atualizar(novoProduto);
    }

    carregarTabela();
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Dados inválidos.");
     } catch (IllegalArgumentException e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
    }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaProduto().setVisible(true));
    }
}
