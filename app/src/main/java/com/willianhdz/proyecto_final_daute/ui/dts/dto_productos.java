package com.example.app_crud_mysql.ui.dts;

public class dto_productos {
    int id_producto;
    String nom_producto;
    String des_producto;
    double stock;
    double precio;
    String unidad_de_medida;
    int estado_producto;
    int categoria;
    String Fecha;

    public dto_productos() {
    }

    public dto_productos(int id_producto, String nom_producto, String des_producto, double stock, double precio, String unidad_de_medida, int estado_producto, int categoria, String fecha) {
        this.id_producto = id_producto;
        this.nom_producto = nom_producto;
        this.des_producto = des_producto;
        this.stock = stock;
        this.precio = precio;
        this.unidad_de_medida = unidad_de_medida;
        this.estado_producto = estado_producto;
        this.categoria = categoria;
        Fecha = fecha;
    }


    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNom_producto() {
        return nom_producto;
    }

    public void setNom_producto(String nom_producto) {
        this.nom_producto = nom_producto;
    }

    public String getDes_producto() {
        return des_producto;
    }

    public void setDes_producto(String des_producto) {
        this.des_producto = des_producto;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUnidad_de_medida() {
        return unidad_de_medida;
    }

    public void setUnidad_de_medida(String unidad_de_medida) {
        this.unidad_de_medida = unidad_de_medida;
    }

    public int getEstado_producto() {
        return estado_producto;
    }

    public void setEstado_producto(int estado_producto) {
        this.estado_producto = estado_producto;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}