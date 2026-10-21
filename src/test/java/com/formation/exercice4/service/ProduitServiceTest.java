package com.formation.exercice4.service;

import com.formation.exercice4.model.Produit;
import com.formation.exercice4.repository.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {
    @Mock // faux repository, piloté par le test
    private ProduitRepository produitRepository;
    @Mock
    private NotificationService notificationService;
    @InjectMocks // vrai service, construit avec le mock
    private ProduitService produitService;

    @Test
    void recupererParId_retourneLeProduit_quandIlExiste() {
        Produit produit = new Produit();
        produit.setId(1L);
        produit.setNom("Clavier");
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        Optional<Produit> resultat = produitService.recupererParId(1L);
        assertThat(resultat).isPresent();
        assertThat(resultat.get().getNom()).isEqualTo("Clavier");
    }

    @Test
    void supprimer_retourneFalse_etNeSupprimeRien_siLeProduitNExistePas() {
        when(produitRepository.existsById(99L)).thenReturn(false);
        boolean resultat = produitService.supprimer(99L);
        assertThat(resultat).isFalse();
        verify(produitRepository, never()).deleteById(any());
    }

    @Test
    void creerWithoutId() {
        Produit produit = new Produit();
        produit.setId(123L);
        produit.setNom("Casque Audio");
        when(produitRepository.save(any(Produit.class))).thenReturn(produit);
        ArgumentCaptor<Produit> captor = ArgumentCaptor.forClass(Produit.class);
        produitService.creer(produit);
        verify(produitRepository).save(captor.capture());
        Produit produitApresCreation = captor.getValue();
        assertThat(produitApresCreation.getId()).isNull();
        assertThat(produitApresCreation.getNom()).isEqualTo("Casque audio");
    }

    @Test
    void creer_appelleNotificationServiceUneFois() {
        Produit produit = new Produit();
        produit.setNom("Souris");
        when(produitRepository.save(any(Produit.class))).thenReturn(produit);
        produitService.creer(produit);
        verify(notificationService, times(1)).notifier(anyString(), anyString());
    }
}
