package com.example.ApiMoviles.dto;

public class ProductResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private int precio;
    private int stock;
    private String tallasDisponibles;
    private Long categoryId;
    private String categoryName;
    private boolean hasImagen;

    public ProductResponseDTO() {}

    public ProductResponseDTO(Long id, String nombre, String descripcion, int precio, int stock,
                            String tallasDisponibles, Long categoryId, String categoryName, boolean hasImagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.tallasDisponibles = tallasDisponibles;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.hasImagen = hasImagen;
    }

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

    public String getTallasDisponibles() { return tallasDisponibles; }
    public void setTallasDisponibles(String tallasDisponibles) { this.tallasDisponibles = tallasDisponibles; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public boolean isHasImagen() { return hasImagen; }
    public void setHasImagen(boolean hasImagen) { this.hasImagen = hasImagen; }
}
