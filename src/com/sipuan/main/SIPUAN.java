/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sipuan.main;

import com.sipuan.views.LoginView;

/**
 *
 * @author dixit
 */
public class SIPUAN {
    public static void main(String[] args) {
        // Menjalankan LoginView pertama kali ketika aplikasi dimulai
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginView(); // Membuka LoginView saat aplikasi dijalankan
            }
        });
    }
}
