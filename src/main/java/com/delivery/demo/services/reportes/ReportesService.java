package com.delivery.demo.services.reportes;

import com.delivery.demo.dtos.GraficosDTO;
import com.delivery.demo.entities.comprobantes.Orden;

import java.sql.Date;
import java.util.List;

public interface ReportesService {
    public List<GraficosDTO> getOutOfStock() throws Exception;
    public List<Orden> getOrdenesPorPeriodo(String clienteUid, Date fechaInicio, Date fechaFin) throws Exception;
    public List<GraficosDTO> getIngresosPorPeriodo(Date fechaInicio, Date fechaFin) throws Exception;
}
