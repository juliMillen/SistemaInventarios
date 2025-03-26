package gm.inventarios.Controlador;

import gm.inventarios.Entidad.Producto;
import gm.inventarios.Excepciones.RecursoNoEncontradoException;
import gm.inventarios.Servicio.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("inventario-app") //localhost:8080/inventario-app
@CrossOrigin(origins = "http://localhost:4200") //puerto por default de angular
public class ProductoController {

    private static final Logger logger= LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @GetMapping("/productos")
    public List<Producto> listarProductos() {
        List<Producto> listaProductos = productoService.obtenerProductos();
        logger.info("Productos encontrados");
        listaProductos.forEach(producto -> logger.info(producto.toString()));
        return listaProductos;
    }

    @GetMapping("producto/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Integer id) {
        Producto buscado = productoService.buscarPorId(id);
        if (buscado != null) {
            return ResponseEntity.ok(buscado);
        }else{
            throw new RecursoNoEncontradoException("No se encontro el id: "+ id);
        }
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        Producto aEditar = productoService.buscarPorId(id);
        if (aEditar != null) {
            aEditar.setDescripcion(producto.getDescripcion());
            aEditar.setPrecio(producto.getPrecio());
            aEditar.setExistencia(producto.getExistencia());
            //guardo el nuevo producto editado
            this.productoService.guardarProducto(aEditar);
        }else{
            throw new RecursoNoEncontradoException("No se encontro el id: "+ id);
        }
        return ResponseEntity.ok(aEditar);
    }

    @PostMapping("/productos")
    public Producto agregarProducto(@RequestBody Producto producto) {
        logger.info("Producto por agregar: " + producto);
        return this.productoService.guardarProducto(producto);
    }


    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Map<String,Boolean>> eliminarProducto(@PathVariable int id) {
        Producto aEliminar = productoService.buscarPorId(id);
        if (aEliminar != null) {
            this.productoService.eliminarPorId(aEliminar.getIdProducto());
        }else{
            throw new RecursoNoEncontradoException("No se encontro el id: "+ id);
        }
        Map<String,Boolean> respuesta = new HashMap<>();
        respuesta.put("eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
