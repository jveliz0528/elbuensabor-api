package com.delivery.demo.services.insumo;

import com.delivery.demo.entities.articulos.ArticuloInsumo;
import com.delivery.demo.entities.articulos.HistorialStock;
import com.delivery.demo.repositories.BaseRepository;
import com.delivery.demo.services.base.BaseServiceImpl;
import com.delivery.demo.specifications.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class ArticuloInsumoServiceImpl extends BaseServiceImpl<ArticuloInsumo, Long> implements ArticuloInsumoService {

    public ArticuloInsumoServiceImpl(BaseRepository<ArticuloInsumo, Long> baseRepository) {
        super(baseRepository);
    }

    SearchSpecification<ArticuloInsumo> spec = new SearchSpecification<ArticuloInsumo>();
    Specification<ArticuloInsumo> isNotDeleted = spec.isNotDeleted();

    @Override
    public Map<String, Object> findAll(String filter, int page, int size, String sortBy, String direction) throws Exception {
        try {
            Pageable pageable;
            if (direction.equals("desc")) {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
            } else {
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
            }

            Page<ArticuloInsumo> entityPage;

            if(filter == null || filter.equals("")){
                entityPage = baseRepository.findAll(Specification.where(isNotDeleted),pageable);
            } else {
                Specification<ArticuloInsumo> filterByDenominacion = spec.findByProperty("denominacion", filter);
                Specification<ArticuloInsumo> filterByDescripcion = spec.findByProperty("descripcion", filter);
                Specification<ArticuloInsumo> filterByRubro = spec.findByForeignAttribute("rubro", "denominacion", filter);
                Specification<ArticuloInsumo> filterByRubroPadre = spec.rubroPadre(filter);

                entityPage = baseRepository.findAll(Specification.where(isNotDeleted)
                        .and(Specification.where(filterByDenominacion)
                                .or(filterByDescripcion)
                                .or(filterByRubro)
                                .or(filterByRubroPadre))
                        , pageable);
            }

            List<ArticuloInsumo> entities = entityPage.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("payload", entities);
            response.put("length", entityPage.getTotalElements());

            return response;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<ArticuloInsumo> findAll(String filter) throws Exception {
        try{

            if(filter == null || filter.equals("")){
                return baseRepository.findAll(Specification.where(isNotDeleted));
            } else {
                Specification<ArticuloInsumo> filterByRubro = spec.findByForeignAttribute("rubro", "denominacion", filter);
                Specification<ArticuloInsumo> filterByRubroPadre = spec.rubroPadre(filter);

                return baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByRubro).or(filterByRubroPadre)));
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /*
     * @desc This method saves a supply article and if stockActual is greater than 0 creates a new
     * HistorialStock and add it to the article historialStock
     * @return ArticuloInsumo articuloInsumo or new Exception()
     * */
    @Override
    public ArticuloInsumo save(ArticuloInsumo entity) throws Exception {
        try {

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            entity.setUltimaActualizacion(timestamp);

            if(entity.getStockActual() > 0){
                entity.getHistorialStock().add(new HistorialStock(entity.getStockActual(), timestamp, true));
            }

            entity = baseRepository.save(entity);

            return entity;

        } catch (Exception e) {

            throw new Exception(e.getMessage());

        }
    }

    /*
     * @desc This method add stock to a supply article and creates a new HistorialStock for it
     * @return ArticuloInsumo articuloInsumo or new Exception()
     * */
    @Override
    public ArticuloInsumo addStock(Long articuloId, double cantidad) throws Exception {
        try {
            Optional<ArticuloInsumo> articuloInsumoOptional = baseRepository.findById(articuloId);
            ArticuloInsumo articulo = articuloInsumoOptional.get();

            articulo.setStockActual(articulo.getStockActual() + cantidad);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            articulo.setUltimaActualizacion(timestamp);
            articulo.getHistorialStock().add(new HistorialStock(cantidad, timestamp, true));

            articulo = baseRepository.save(articulo);
            return articulo;

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /*
     * @desc This method removes stock to a supply article and creates a new HistorialStock for it
     * @return ArticuloInsumo articuloInsumo or new Exception()
     * */
    @Override
    public ArticuloInsumo removeStock(Long articuloId, double cantidad) throws Exception {
        try {

            Optional<ArticuloInsumo> articuloInsumoOptional = baseRepository.findById(articuloId);
            ArticuloInsumo articulo = articuloInsumoOptional.get();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            if(articulo.getStockActual() > cantidad){
                articulo.setStockActual(articulo.getStockActual() - cantidad);
                articulo.getHistorialStock().add(new HistorialStock(cantidad, timestamp, false));
                articulo.setUltimaActualizacion(timestamp);

                articulo = baseRepository.save(articulo);
                return articulo;

            } else {
                throw new Exception("Stock insuficiente");
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    /*
     * @desc This method gets all supplies where propery "esInsumo" is false,
     * and filter the data if filter string exists
     * @return List<ArticuloInsumo> bebidas or new Exception()
     * */
    @Override
    public List<ArticuloInsumo> getBebidas(String filter) throws Exception {
        try{

            Specification<ArticuloInsumo> esBebida = spec.esBebida();
            Specification<ArticuloInsumo> esPublico = spec.isNotHidden();

            if(filter == null || filter.equals("")){
                return baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(esBebida)).and(Specification.where(esPublico)));
            } else {
                Specification<ArticuloInsumo> filterByDenominacion = spec.findByProperty("denominacion", filter);
                Specification<ArticuloInsumo> filterByDescripcion = spec.findByProperty("descripcion", filter);
                Specification<ArticuloInsumo> filterByRubro = spec.findByForeignAttribute("rubro", "denominacion", filter);
                Specification<ArticuloInsumo> filterByRubroPadre = spec.rubroPadre(filter);

                return baseRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(esBebida)).and(Specification.where(esPublico))
                        .and(Specification.where(filterByDenominacion)
                                .or(filterByDescripcion)
                                .or(filterByRubro)
                                .or(filterByRubroPadre)
                        ));
            }

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}
