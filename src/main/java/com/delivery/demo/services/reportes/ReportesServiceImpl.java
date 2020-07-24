package com.delivery.demo.services.reportes;

import com.delivery.demo.dtos.GraficosDTO;
import com.delivery.demo.entities.articulos.ArticuloInsumo;
import com.delivery.demo.entities.comprobantes.Factura;
import com.delivery.demo.entities.comprobantes.Orden;
import com.delivery.demo.repositories.articulos.ArticuloInsumoRepository;
import com.delivery.demo.repositories.articulos.ArticuloManufacturadoRepository;
import com.delivery.demo.repositories.comprobantes.FacturaRepository;
import com.delivery.demo.repositories.comprobantes.OrdenRepository;
import com.delivery.demo.specifications.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportesServiceImpl implements ReportesService {

    @Autowired
    ArticuloInsumoRepository insumoRepository;

    @Autowired
    OrdenRepository ordenRepository;

    @Autowired
    FacturaRepository facturaRepository;

    @Autowired
    ArticuloManufacturadoRepository manufacturadoRepository;

    @Override
    public List<GraficosDTO> getOutOfStock() throws Exception {
        try{

            SearchSpecification<ArticuloInsumo> spec = new SearchSpecification<ArticuloInsumo>();
            Specification<ArticuloInsumo> isNotDeleted = spec.isNotDeleted();

            Specification<ArticuloInsumo> filterByOutOfStock = spec.findByOutOfStock();
            List<ArticuloInsumo> insumos = insumoRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(filterByOutOfStock)));

            List<GraficosDTO> graficosDTOS = new ArrayList<>();
            GraficosDTO aux;

            for (ArticuloInsumo articulo: insumos) {
                aux = new GraficosDTO();
                aux.setName(articulo.getDenominacion());
                aux.setValue(articulo.getStockActual());
                graficosDTOS.add(aux);
            }

            return graficosDTOS;

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Orden> getOrdenesPorPeriodo(String clienteUid, Date fechaInicio, Date fechaFin) throws Exception {
        try{

            SearchSpecification<Orden> spec = new SearchSpecification<Orden>();
            Specification<Orden> isNotDeleted = spec.isNotDeleted();

            Specification<Orden> filterByCliente = spec.findByForeignAttribute("cliente", "uid", clienteUid);
            Specification<Orden> betweenDates = spec.findBetweenDates(fechaInicio, fechaFin);
            List<Orden> ordenes = ordenRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(betweenDates).and(filterByCliente)));

            return ordenes;

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<GraficosDTO> getIngresosPorPeriodo(Date fechaInicio, Date fechaFin) throws Exception {
        try{

            SearchSpecification<Factura> spec = new SearchSpecification<Factura>();
            Specification<Factura> isNotDeleted = spec.isNotDeleted();

            Specification<Factura> betweenDates = spec.findBetweenDates(fechaInicio, fechaFin);
            Specification<Factura> filterByEstado = spec.findByEstado("pagado");
            List<Factura> facturas = facturaRepository.findAll(Specification.where(isNotDeleted).and(Specification.where(betweenDates).and(filterByEstado)));

            double ingresos = 0;
            double cantidad = 0;

            for (Factura factura: facturas) {
                ingresos+= factura.getOrden().getTotal();
                cantidad ++;
            }

            List<GraficosDTO> graficosDTOS = new ArrayList<>();

            graficosDTOS.add(new GraficosDTO("CANTIDAD", cantidad));
            graficosDTOS.add(new GraficosDTO("INGRESOS", ingresos));

            return graficosDTOS;

        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<GraficosDTO> getInsumoMasVendido(Date fechaInicio, Date fechaFin) throws Exception {
        try {
            List<Object[]> objects = insumoRepository.getMasVendidos(fechaInicio, fechaFin);
            List<GraficosDTO> insumosMasVendidos = new ArrayList<>();
            for (Object[] object: objects) {
                insumosMasVendidos.add(new GraficosDTO(object[0].toString(), (double) object[1]));
            }
            return insumosMasVendidos;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<GraficosDTO> getManufacturadoMasVendido(Date fechaInicio, Date fechaFin) throws Exception {
        try {
            List<Object[]> objects = manufacturadoRepository.getMasVendidos(fechaInicio, fechaFin);
            List<GraficosDTO> manufacturadosMasVendidos = new ArrayList<>();
            for (Object[] object: objects) {
                manufacturadosMasVendidos.add(new GraficosDTO(object[0].toString(), (double) object[1]));
            }
            return manufacturadosMasVendidos;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
