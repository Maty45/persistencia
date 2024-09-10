package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();

            Factura factura1 = new Factura();
            factura1.setNumero(12);
            factura1.setFecha("12-12-2002");

            Domicilio dom = new Domicilio("España", 134);
            Cliente cliente = new Cliente("Oscar", "Wilde",133342);
            cliente.setDomicilio(dom);
            dom.setCliente(cliente);
            factura1.setCliente(cliente);

            Categoria perecederos = new Categoria("Perecederos");
            Categoria lacteos = new Categoria("lacteos");
            Categoria limpieza = new Categoria("limpieza");

            Articulo art1 = new Articulo(30, "Coca-cola", 100);
            Articulo art2 = new Articulo(40,"Trapo de piso",300);

            art1.getCategorias().add(perecederos);
            art2.getCategorias().add(limpieza);
            perecederos.getArticulos().add(art1);
            limpieza.getArticulos().add(art2);

            DetalleFactura det1 = new DetalleFactura();
            det1.setArticulo(art1);
            det1.setCantidad(2);
            det1.setSubtotal(200);

            art1.getDetallesFact().add(det1);
            factura1.getDetalles().add(det1);
            det1.setFactura(factura1);

            DetalleFactura det2 = new DetalleFactura();
            det2.setArticulo(art2);
            det2.setCantidad(1);
            det2.setSubtotal(300);

            art2.getDetallesFact().add(det2);
            factura1.getDetalles().add(det2);
            det2.setFactura(factura1);

            factura1.setTotal(500);


            em.flush();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        }
        em.close();
        entityManagerFactory.close();
    }
}
