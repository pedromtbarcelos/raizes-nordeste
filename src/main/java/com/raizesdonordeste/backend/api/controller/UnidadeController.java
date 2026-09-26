import com.raizesdonordeste.backend.domain.entity.Estoque;
import com.raizesdonordeste.backend.infrastructure.repository.EstoqueRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/unidades")
@RequiredArgsConstructor
@Tag(name = "Unidades & Cardápio", description = "Endpoints para consulta de unidades e seus cardápios")
public class UnidadeController {

    private final EstoqueRepository estoqueRepository;

    @GetMapping("/{id}/cardapio")
    @Operation(summary = "Consulta o cardápio (produtos disponíveis no estoque) de uma unidade específica")
    public ResponseEntity> obterCardapioPorUnidade(@PathVariable("id") Long idUnidade) {
        List estoques = estoqueRepository.findAll().stream()
                .filter(e -> e.getUnidade().getId().equals(idUnidade) && e.getQuantidadeSaldo() > 0)
                .collect(Collectors.toList());

        List cardapio = estoques.stream()
                .map(Estoque::getProduto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(cardapio);
    }
}
