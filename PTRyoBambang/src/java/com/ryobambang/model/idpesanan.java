/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ryobambang.model;

import java.sql.Timestamp;

/**
 *
 * @author Lenobo
 */
public class idpesanan {
    private int id_pesanan;
    private int id_user;
    private Timestamp tanggal;
    private double total;

    // Constructor kosong
    public idpesanan() {}

    // Constructor dengan parameter
    public idpesanan(int id_pesanan, int id_user, Timestamp tanggal, double total) {
        this.id_pesanan = id_pesanan;
        this.id_user = id_user;
        this.tanggal = tanggal;
        this.total = total;
    }

    // Getter & Setter
    public int getId_pesanan() {
        return id_pesanan;
    }

    public void setId_pesanan(int id_pesanan) {
        this.id_pesanan = id_pesanan;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Timestamp getTanggal() {
        return tanggal;
    }

    public void setTanggal(Timestamp tanggal) {
        this.tanggal = tanggal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
