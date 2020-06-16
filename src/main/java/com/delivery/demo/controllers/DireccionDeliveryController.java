package com.delivery.demo.controllers;

import com.delivery.demo.entities.direccion.DireccionDelivery;
import com.delivery.demo.services.direccionDelivery.DireccionDeliveryServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(path = "api/v1/direccion/delivery")
@Transactional
public class DireccionDeliveryController extends BaseController<DireccionDelivery, DireccionDeliveryServiceImpl> {
}
