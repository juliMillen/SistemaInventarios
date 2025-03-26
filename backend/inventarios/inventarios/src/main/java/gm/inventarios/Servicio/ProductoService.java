package gm.inventarios.Servicio;

import gm.inventarios.Entidad.Producto;
import gm.inventarios.Repositorio.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {
    @Autowired
    private IProductoRepository productoRepository;

    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Integer id) {
       Producto buscado = productoRepository.findById(id).orElse(null);
       return buscado;
    }

    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Producto producto) {
        if (producto != null) {
            productoRepository.delete(producto);
        }else{
            System.out.println("No existe el producto");
        }
    }

    public void eliminarPorId(Integer id) {
        if(productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
        }else{
            System.out.println("No se puede eliminar el producto");
        }
    }
}
