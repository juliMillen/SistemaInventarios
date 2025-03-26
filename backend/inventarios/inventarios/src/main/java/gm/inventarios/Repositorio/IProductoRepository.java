package gm.inventarios.Repositorio;

import gm.inventarios.Entidad.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {
}
