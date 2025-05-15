package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.DetalleFacturaService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.DetalleFacturaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.DetalleFactura;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.mainclass.DetalleFacturaMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.DetalleFacturaRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DetalleFacturaServiceImpl implements DetalleFacturaService {

    private DetalleFacturaRepository detalleFacturaRepository;
    private DetalleFacturaMapper detalleFacturaMapper;

    @Override
    public List<DetalleFacturaDTO> findAll() {
        return detalleFacturaRepository.findAll().stream()
                .map(detalleFacturaMapper::toDto)
                .toList();
    }

    @Override
    public DetalleFacturaDTO createDetalleFactura(DetalleFacturaDTO detalleFacturaDTO) {
        DetalleFactura detalleFactura = detalleFacturaMapper.toEntity(detalleFacturaDTO);
        detalleFactura = detalleFacturaRepository.save(detalleFactura);
        return detalleFacturaMapper.toDto(detalleFactura);
    }
} 