package com.bitirmeproje.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "secenekler")
public class Secenekler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "secenek_id")
    private int secenekId;

    @Column(name = "secenek_metni")
    private String secenekMetni;

    @ManyToOne
    @JsonBackReference
    @JoinColumn (name = "anket_id")
    private Anketler anketId;

    @OneToMany(mappedBy = "secenekId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cevaplar> cevaplar;

    public int getSecenekId() {
        return secenekId;
    }

    public void setSecenekId(int secenekId) {
        this.secenekId = secenekId;
    }

    public Anketler getAnketId() {
        return anketId;
    }

    public void setAnketId(Anketler anketId) {
        this.anketId = anketId;
    }

    public String getSecenekMetni() {
        return secenekMetni;
    }

    public void setSecenekMetni(String secenekMetni) {
        this.secenekMetni = secenekMetni;
    }

    public List<Cevaplar> getCevaplar() {
        return cevaplar;
    }

    public void setCevaplar(List<Cevaplar> cevaplar) {
        this.cevaplar = cevaplar;
    }
}
