package com.formation.exercice4.controller;
import com.formation.exercice4.model.Produit;
import com.formation.exercice4.service.ProduitService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * Contrôleur REST pour les produits.
 * Chaque méthode correspond à un endpoint de l'API.
 *
 * @RestController = @Controller + @ResponseBody
 * → Toutes les méthodes retournent du JSON (pas des vues HTML).
 */
@RestController
@RequestMapping("/api/v1/produits")
public class ProduitController {
    private final ProduitService produitService;
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }
    // ============================================================
// GET /api/v1/produits — Lister tous les produits
// ============================================================
    @GetMapping
    public ResponseEntity<Page<Produit>> listerTous(
            @PageableDefault(page = 0, size = 5, sort = "prix", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<Produit> produits = produitService.recupererTous(pageable);
        return ResponseEntity.ok(produits);
    }
    // ============================================================
// GET /api/v1/produits/recherche?nom=clavier — Rechercher par nom
// ============================================================
    @GetMapping("/recherche")
    public ResponseEntity<Page<Produit>> rechercher(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Double prixMin,
            @RequestParam(required = false) Double prixMax,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Produit> produitFiltre = produitService.recupererFiltre(nom, prixMin, prixMax, pageable);
        return ResponseEntity.ok(produitFiltre);
    }
    // ============================================================
// GET /api/v1/produits/{id} — Obtenir un produit par son ID
// ============================================================
    @GetMapping("/{id}")
    public ResponseEntity<Produit> obtenirParId(@PathVariable Long id) {
        return produitService.recupererParId(id)
                .map(ResponseEntity::ok) // 200 OK si trouvé
                .orElse(ResponseEntity.notFound().build()); // 404 si non trouvé
    }
    // ============================================================
// POST /api/v1/produits — Créer un nouveau produit
// ============================================================
    @PostMapping
    public ResponseEntity<Produit> creer(@Valid @RequestBody Produit produit) {
        Produit produitCree = produitService.creer(produit);
        return ResponseEntity
                .status(HttpStatus.CREATED) // 201 Created
                .body(produitCree);
    }
    // ============================================================
// PUT /api/v1/produits/{id} — Mettre à jour un produit
// ============================================================
    @PutMapping("/{id}")
    public ResponseEntity<Produit> mettreAJour(@PathVariable Long id,
                                               @Valid @RequestBody Produit produit)
    {
        return produitService.mettreAJour(id, produit)
                .map(ResponseEntity::ok) // 200 OK si mis à jour
                .orElse(ResponseEntity.notFound().build()); // 404 si non trouvé
    }
    // ============================================================
// DELETE /api/v1/produits/{id} — Supprimer un produit
// ============================================================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (produitService.supprimer(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build(); // 404 si non trouvé
    }
}