package view;

import control.Controle;
import control.Observado;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import observer.Observador;

import utils.SpringUtilities;

public class TelaProdutos extends JFrame implements Observador {

    private Observado observado;

    private static final long serialVersionUID = 1L;

    private List<String> produtos = new ArrayList<>();

    @Override
    public void notificarCriacaoProduto(String descricao, double preco) {
        String produto = descricao + " " + preco;
        if (indexOfProduto == -1) {
            model.add(produto);
        } else {
            model.set(indexOfProduto, produto);
            indexOfProduto = -1;
        }
    }

    private class ProdutosListModel extends AbstractListModel<String> {

        private static final long serialVersionUID = 1L;

        @Override
        public String getElementAt(int index) {
            return produtos.get(index);
        }

        @Override
        public int getSize() {
            return produtos.size();
        }

        public void add(String prod) {
            produtos.add(prod);
            fireIntervalAdded(prod, produtos.size() - 1, produtos.size() - 1);
        }

        public void set(int indexOfProduto, String produto) {
            produtos.set(indexOfProduto, produto);
            fireContentsChanged(produto, indexOfProduto, indexOfProduto);
        }

    }

    private JTextField tfNome;
    private JTextField tfPreco;
    private JButton ok;
    private JList<String> lista;
    private ProdutosListModel model;
    private int indexOfProduto = -1;

    public TelaProdutos() {
        this.observado = new Controle();
        observado.addObserver(this);
        setTitle("MVC 4 55PPR");
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {

        /**
         * ** campos descricao e preco ***
         */
        JPanel jp1 = new JPanel();
        jp1.setLayout(new SpringLayout());

        this.tfNome = new JTextField();
        this.tfPreco = new JTextField();

        jp1.add(new JLabel("Descricao :"));
        jp1.add(tfNome);

        jp1.add(new JLabel("Preco :"));
        jp1.add(tfPreco);

        // apenas para ajeitar a posicao dos componentes na tela
        SpringUtilities.makeCompactGrid(jp1,
                2, 2,
                3, 3, //initX, initY
                3, 3); //xPad, yPad

        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.add(jp1, BorderLayout.CENTER);

        /**
         * ** botao salvar ***
         */
        JPanel jp2 = new JPanel();
        this.ok = new JButton("Salvar");
        jp2.add(ok);

        ok.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                observado.criarProduto(tfNome.getText(), Double.parseDouble(tfPreco.getText()));
                /*if (indexOfProduto == -1) {
					model.add(p);
				} else {
					model.set(indexOfProduto, p);
					indexOfProduto = -1;
				}*/

                tfNome.setText("");
                tfPreco.setText("");

            }
        });

        jp.add(jp2, BorderLayout.SOUTH);

        add(jp, BorderLayout.NORTH);

        /**
         * ** JList ***
         */
        this.model = new ProdutosListModel();
        this.lista = new JList<String>(this.model);
        this.lista.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // duplo clique
                    //indexOfProduto = lista.locationToIndex(e.getPoint());
                    indexOfProduto = lista.getSelectedIndex();
                    String p = produtos.get(indexOfProduto);

                    //tfNome.setText(p.getDescricao());
                    //tfPreco.setText(p.getPreco()+"");
                }
            }

        });
        add(new JScrollPane(this.lista), BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        TelaProdutos tp = new TelaProdutos();
        tp.setVisible(true);

    }

}
