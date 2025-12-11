package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.util;


public class ValidadorCedulaEc {


    public static boolean esValida(String cedula) {

        // Validación de longitud y formato
        if (cedula == null || cedula.length() != 10 || !cedula.matches("\\d{10}")) {
            return false;
        }

        try {
            // Validación del tercer dígito (debe ser menor a 6 para personas naturales)
            int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
            if (tercerDigito >= 6) return false;

            // Inicialización de coeficientes y variables
            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            int verificador = Integer.parseInt(cedula.substring(9, 10)); // Último dígito
            int suma = 0;
            int digito;

            // Aplicación del Módulo 10 a los primeros 9 dígitos
            for (int i = 0; i < 9; i++) {
                digito = Integer.parseInt(cedula.substring(i, i + 1)) * coeficientes[i];
                // Si el resultado es >= 10, se restan 9 (igual que sumar sus dígitos)
                suma += (digito > 9) ? digito - 9 : digito;
            }


            int digitoVerificadorCalculado = (suma % 10 == 0) ? 0 : 10 - (suma % 10);


            return digitoVerificadorCalculado == verificador;

        } catch (NumberFormatException e) {

            return false;
        }
    }
}