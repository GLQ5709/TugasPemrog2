/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ryobambang.model;

/**
 *
 * @author Lenobo
 */
public class iddetail_pesanan {
    private int id_detail;
    private int id_pesanan;
    private int id_produk;
    private int jumlah;

    // Constructor kosong
    public iddetail_pesanan() {}

    // Constructor dengan parameter
    public iddetail_pesanan(int id_detail, int id_pesanan, int id_produk, int jumlah) {
        this.id_detail = id_detail;
        this.id_pesanan = id_pesanan;
        this.id_produk = id_produk;
        this.jumlah = jumlah;
    }

    // Getter & Setter
    public int getId_detail() {
        return id_detail;
    }

    public void setId_detail(int id_detail) {
        this.id_detail = id_detail;
    }

    public int getId_pesanan() {
        return id_pesanan;
    }

    public void setId_pesanan(int id_pesanan) {
        this.id_pesanan = id_pesanan;
    }

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}
