package com.fiap.cerveja.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fiap.cerveja.domain.Tipo;
import com.fiap.cerveja.dto.CervejaCreateOrUpdateDTO;
import com.fiap.cerveja.dto.CervejaDTO;
import com.fiap.cerveja.dto.CervejaVencimentoDTO;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("cervejas")
public class CervejaController {

    private List<CervejaDTO> cervejaList ;

    public CervejaController(){
        cervejaList = new ArrayList<>();
        CervejaDTO cervejaDTO1 = new CervejaDTO();
        cervejaDTO1.setId(1L);
        cervejaDTO1.setNome("Baden Baden");
        cervejaDTO1.setTipo(Tipo.WEISS);
        cervejaDTO1.setVencimento(LocalDate.of(2022, 6, 30));
        cervejaList.add(cervejaDTO1);

        CervejaDTO cervejaDTO2 = new CervejaDTO();
        cervejaDTO2.setId(2L);
        cervejaDTO2.setNome("Colonia");
        cervejaDTO2.setTipo(Tipo.PILSEN);
        cervejaDTO2.setVencimento(LocalDate.of(2022, 2, 15));
        cervejaList.add(cervejaDTO2);
    }

    @GetMapping
    public List<CervejaDTO> buscarCervejas(
            @RequestParam(value = "tipo", required = false) Tipo tipo
    ){
        return cervejaList.stream()
                .filter(cervejaDTO -> tipo == null || cervejaDTO.getTipo().equals(tipo))
                .collect(Collectors.toList());
    }

    @GetMapping("{idCerveja}")
    public CervejaDTO buscarCervejaPorId(
        @PathVariable(value = "idCerveja") Long id
    ){
        return getCervejaDTO(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CervejaDTO criar(
            @RequestBody CervejaCreateOrUpdateDTO createOrUpdateDTO
            ){
        CervejaDTO cervejaDTO = new CervejaDTO();
        cervejaDTO.setId(cervejaList.size() + 1L);
        cervejaDTO.setNome(createOrUpdateDTO.getNome());
        cervejaDTO.setTipo(createOrUpdateDTO.getTipo());
        cervejaDTO.setVencimento(createOrUpdateDTO.getVencimento());

        cervejaList.add(cervejaDTO);
        return cervejaDTO;
    }

    @PutMapping("{id}")
    public CervejaDTO update(
            @PathVariable Long id,
            @RequestBody CervejaCreateOrUpdateDTO createOrUpdateDTO
    ){
        CervejaDTO cervejaDTO = getCervejaDTO(id);

        cervejaDTO.setNome(createOrUpdateDTO.getNome());
        cervejaDTO.setTipo(createOrUpdateDTO.getTipo());
        cervejaDTO.setVencimento(createOrUpdateDTO.getVencimento());

        return cervejaDTO;
    }

    @PatchMapping("{id}")
    public CervejaDTO updateVencimento(
            @PathVariable Long id,
            @RequestBody CervejaVencimentoDTO cervejaVencimentoDTO
    ){
        CervejaDTO cervejaDTO = getCervejaDTO(id);
        cervejaDTO.setVencimento(cervejaVencimentoDTO.getVencimento());

        return cervejaDTO;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCerveja(
            @PathVariable Long id
    ){
        CervejaDTO cervejaDTO = getCervejaDTO(id);
        cervejaList.remove(cervejaDTO);
    }

    private CervejaDTO getCervejaDTO(@PathVariable Long id) {
        return cervejaList.stream()
                .filter(dto -> dto.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cerveja.nao.encontrada"));
    }
}
