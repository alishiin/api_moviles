package com.example.ApiMoviles.model;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    // Precio como entero (sin puntos ni comas)
    @Column(name = "precio", nullable = false)
    private int precio;

    @Column(name = "stock", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] imagen;

    // Tallas disponibles como string separado por comas
    @Column(name = "tallas_disponibles", length = 500)
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
