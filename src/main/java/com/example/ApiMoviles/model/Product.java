package com.example.ApiMoviles.model;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    // Precio como entero (sin puntos ni comas)
    private int precio;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imagen;

    // Tallas disponibles como string separado por comas
    private String tallasDisponibles;

    public Product() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public byte[] getImagen() { return imagen; }
    public void setImagen(byte[] imagen) { this.imagen = imagen; }

    public String getTallasDisponibles() { return tallasDisponibles; }
    public void setTallasDisponibles(String tallasDisponibles) { this.tallasDisponibles = tallasDisponibles; }
}
