package tn.fsegt.foyer.Entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "blocs")
public class Bloc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomBloc;

    @OneToMany(mappedBy = "bloc", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Chambre> chambres;
    @ManyToOne
    private Foyer foyer;

    public void setFoyer(Foyer foyer) { this.foyer = foyer; }
    public Bloc() {}

    public Bloc(String nomBloc) {
        this.nomBloc = nomBloc;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomBloc() { return nomBloc; }
    public void setNomBloc(String nomBloc) { this.nomBloc = nomBloc; }

    public List<Chambre> getChambres() { return chambres; }
    public void setChambres(List<Chambre> chambres) { this.chambres = chambres; }
}
