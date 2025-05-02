package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.FacturaService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.FacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Factura;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.FacturaMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.FacturaRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class FacturaServiceImpl implements FacturaService {

    private FacturaRepository facturaRepository;
    private FacturaMapper facturaMapper;

    @Override
    public List<FacturaDTO> findAll() {
        return facturaRepository.findAll().stream()
                .map(facturaMapper::toDto)
                .toList();
    }

    @Override
    public FacturaDTO createFactura(FacturaDTO facturaDTO) {
        Factura factura = facturaMapper.toEntity(facturaDTO);
        factura = facturaRepository.save(factura);
        return facturaMapper.toDto(factura);
    }
} 