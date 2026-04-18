package com.hebergement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chambres")
public class Chambre {

    public enum TypeChambre {
        SIMPLE, DOUBLE, TRIPLE;

        public int getCapacite() {
            return switch (this) {
                case SIMPLE -> 1;
                case DOUBLE -> 2;
                case TRIPLE -> 3;
            };
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroChambre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeChambre typeChambre;

    @Column(nullable = false)
    private int placesOccupees = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bloc_id")
    private Bloc bloc;

    public Chambre() {}

    public Chambre(String numeroChambre, TypeChambre typeChambre) {
        this.numeroChambre = numeroChambre;
        this.typeChambre = typeChambre;
    }

    // Logique métier : capacité et disponibilité

    public int getCapacite() {
        return typeChambre.getCapacite();
    }

    public int getPlacesDisponibles() {
        return Math.max(0, getCapacite() - placesOccupees);
    }

    public boolean isPleine() {
        return placesOccupees >= getCapacite();
    }

    public boolean isDisponible() {
        return !isPleine();
    }

    // Getters / Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroChambre() { return numeroChambre; }
    public void setNumeroChambre(String numeroChambre) { this.numeroChambre = numeroChambre; }

    public TypeChambre getTypeChambre() { return typeChambre; }
    public void setTypeChambre(TypeChambre typeChambre) { this.typeChambre = typeChambre; }

    public int getPlacesOccupees() { return placesOccupees; }
    public void setPlacesOccupees(int placesOccupees) { this.placesOccupees = placesOccupees; }

    public Bloc getBloc() { return bloc; }
    public void setBloc(Bloc bloc) { this.bloc = bloc; }
}
