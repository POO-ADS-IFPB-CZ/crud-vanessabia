import java.util.List;
/**
 *
 * @author vanessa
 */
public class ProdutoDao {
    private final GenericDao<Produto> dao;
    private List<Produto> produtos;

    public ProdutoDao() {
        dao = new GenericDao<>("produtos.dat");
        produtos = dao.load();
    }

    public List<Produto> listar() {
        return produtos;
    }

    public void adicionar(Produto produto) {
    if (produtos.contains(produto)) {
        throw new IllegalArgumentException("Já existe um produto com esse código.");
    }
    produtos.add(produto);
    dao.save(produtos);
    }  

    public void atualizar(Produto produto) {
        int index = produtos.indexOf(produto);
        if (index != -1) {
            produtos.set(index, produto);
            dao.save(produtos);
        }
    }

    public void remover(Produto produto) {
        produtos.remove(produto);
        dao.save(produtos);
    }
}

