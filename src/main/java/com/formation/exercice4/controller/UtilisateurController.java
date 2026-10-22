package com.formation.exercice4.controller;

import com.formation.exercice4.dto.UtilisateurRequest;
import com.formation.exercice4.dto.UtilisateurResponse;
import com.formation.exercice4.mapper.UtilisateurMapper;
import com.formation.exercice4.model.Utilisateur;
import com.formation.exercice4.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final UtilisateurMapper utilisateurMapper;

    public UtilisateurController(UtilisateurService utilisateurService, UtilisateurMapper utilisateurMapper) {
        this.utilisateurService = utilisateurService;
        this.utilisateurMapper = utilisateurMapper;
    }

    @GetMapping("/utilisateurs")
    public ResponseEntity<Page<UtilisateurResponse>> listerTous(@PageableDefault(size = 5, sort = "nom") Pageable pageable) {
        Page<UtilisateurResponse> page = utilisateurService.recupererTous(pageable)
                .map(utilisateurMapper::versResponse);

        return ResponseEntity.ok(page);
    }

    @GetMapping("/utilisateurs/{id}")
    public ResponseEntity<UtilisateurResponse> obtenirParId(@PathVariable Long id) {
        return utilisateurService.recupererParId(id)
                .map(utilisateurMapper::versResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/utilisateurs")
    public ResponseEntity<UtilisateurResponse> creer(@Valid @RequestBody UtilisateurRequest utilisateurRequest) {
        Utilisateur utilisateurEntite = utilisateurMapper.versEntite(utilisateurRequest);
        Utilisateur utilisateurSauvegarde = utilisateurService.creer(utilisateurEntite);
        UtilisateurResponse utilisateurResponse = utilisateurMapper.versResponse(utilisateurSauvegarde);
        return ResponseEntity.status(HttpStatus.CREATED).body(utilisateurResponse);
    }

    @PutMapping("/utilisateurs/{id}")
    public ResponseEntity<UtilisateurResponse> modifier(@PathVariable Long id, @Valid @RequestBody UtilisateurRequest utilisateurRequest) {
        Utilisateur utilisateurEntite = utilisateurMapper.versEntite(utilisateurRequest);
        return utilisateurService.mettreAJour(id, utilisateurEntite)
                .map(utilisateurMapper::versResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/utilisateurs/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        boolean estSupprime = utilisateurService.supprimer(id);

        if (estSupprime) {
            return ResponseEntity.noContent().build(); // 204 No Content (Succès)
        } else {
            return ResponseEntity.notFound().build();  // 404 Not Found (ID inexistant)
        }
    }
}