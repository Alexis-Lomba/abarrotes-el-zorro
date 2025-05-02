package unam.fes.aragon.tienda_el_zorro.application.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import unam.fes.aragon.tienda_el_zorro.application.service.VentaService;
import unam.fes.aragon.tienda_el_zorro.domain.dto.VentaDTO;
import unam.fes.aragon.tienda_el_zorro.domain.entity.Venta;
import unam.fes.aragon.tienda_el_zorro.infraestructure.mapper.VentaMapper;
import unam.fes.aragon.tienda_el_zorro.infraestructure.repository.VentaRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class VentaServiceImpl implements VentaService {

    private VentaRepository ventaRepository;
    private VentaMapper ventaMapper;

    @Override
    public List<VentaDTO> findAll() {
        return ventaRepository.findAll().stream()
                .map(ventaMapper::toDto)
                .toList();
    }

    @Override
    public VentaDTO createVenta(VentaDTO ventaDTO) {
        Venta venta = ventaMapper.toEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        return ventaMapper.toDto(venta);
    }
} 