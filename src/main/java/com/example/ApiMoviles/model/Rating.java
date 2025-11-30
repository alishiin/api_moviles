package com.example.ApiMoviles.model;

import jakarta.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String comentario;

    @ManyToOne
    private User usuario;

    @ManyToOne
    private Product producto;

    public Rating() {}

    public Long getId() { return id; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public User getUsuario() { return usuario; }
    public void setUsuario(User usuario) { this.usuario = usuario; }
    public Product getProducto() { return producto; }
    public void setProducto(Product producto) { this.producto = producto; }
}
