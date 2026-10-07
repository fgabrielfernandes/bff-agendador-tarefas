package com.javanauta.bff_agendadortarefas.controller;


import com.javanauta.bff_agendadortarefas.business.TarefasService;
import com.javanauta.bff_agendadortarefas.business.dto.in.TarefasDTORequest;
import com.javanauta.bff_agendadortarefas.business.dto.out.TarefasDTOResponse;
import com.javanauta.bff_agendadortarefas.business.enums.StatusNotificacaoEnum;
import com.javanauta.bff_agendadortarefas.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Cadastra tarefas de usuários.")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    @Operation(summary = "Salvar Tarefas", description = "Cria uma nova tarefa")
    @ApiResponse(responseCode= "200", description = "Tarefa salva com sucesso!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<TarefasDTOResponse> gravarTarefas(@RequestBody TarefasDTORequest dto,
                                                            @RequestHeader(value = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefasService.gravarTarefas(token, dto));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Busca Tarefas por Periodo", description = "Buscar tarefas por periodo.")
    @ApiResponse(responseCode= "200", description = "Tarefas encontradas com sucesso!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<List<TarefasDTOResponse>> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader(name = "Authorization", required= false) String token) {
        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal, token));
    }

    @GetMapping
    @Operation(summary = "Busca Lista Tarefas por EMAIL de Usuario", description = "Buscar tarefas por usuario.")
    @ApiResponse(responseCode= "200", description = "Tarefas encontradas com sucesso!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<List<TarefasDTOResponse>> buscaTarefasPorEmail(@RequestHeader(name = "Authorization", required= false) String token) {
        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorEmail(token));
    }

    @DeleteMapping
    @Operation(summary = "Deleta tarefas por id", description = "Deleta tarefas cadastradas por ID")
    @ApiResponse(responseCode= "200", description = "Tarefas deletadas!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id,
                                                  @RequestHeader(name = "Authorization", required= false) String token) {
        tarefasService.deletaTarefaPorId(id, token);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    @Operation(summary = "Altera Status Tarefas", description = "Altera status das tarefas.")
    @ApiResponse(responseCode= "200", description = "Status alterados com sucesso!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<TarefasDTOResponse> alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum status,
                                                                      @RequestParam("id") String id,
                                                                      @RequestHeader(name = "Authorization", required= false) String token) {
        return ResponseEntity.ok(tarefasService.alteraStatus(status, id, token));
    }

    @PutMapping
    @Operation(summary = "Altera dados da Tarefa", description = "Altera dados da tarefa.")
    @ApiResponse(responseCode= "200", description = "Dados alterados com sucesso!")
    @ApiResponse(responseCode = "500", description = "Erro de servidor!")
    public ResponseEntity<TarefasDTOResponse> updateTarefas(@RequestBody TarefasDTORequest dto,
                                                            @RequestParam("id") String id,
                                                            @RequestHeader(name = "Authorization", required= false) String token) {
        return ResponseEntity.ok(tarefasService.updateTarefas(dto, id, token));
    }
}
