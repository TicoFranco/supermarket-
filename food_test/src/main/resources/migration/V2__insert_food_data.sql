INSERT INTO food (name, price, type, url)
SELECT 'Refrigerante', 7.50, 'BEBIDA', 'http://localhost:8080/images/bebidas/refrigerante.png'
UNION ALL SELECT 'Agua', 5.00, 'BEBIDA', 'http://localhost:8080/images/bebidas/agua.png'
UNION ALL SELECT 'Suco', 3.50, 'BEBIDA', 'http://localhost:8080/images/bebidas/suco.png'
UNION ALL SELECT 'Leite', 4.00, 'BEBIDA', 'http://localhost:8080/images/bebidas/leite.png'
UNION ALL SELECT 'Vinho', 30.00, 'BEBIDA', 'http://localhost:8080/images/bebidas/vinho.png'
UNION ALL SELECT 'Carne Bovina', 40.00, 'CARNE', 'http://localhost:8080/images/carnes/carne_bovina.png'
UNION ALL SELECT 'Frango', 28.00, 'CARNE', 'http://localhost:8080/images/carnes/frango.png'
UNION ALL SELECT 'Peixe', 35.00, 'CARNE', 'http://localhost:8080/images/carnes/peixe.png'
UNION ALL SELECT 'Macarrão', 20.00, 'MASSA', 'http://localhost:8080/images/massas/macarrao.png'
UNION ALL SELECT 'Lasanha', 32.00, 'MASSA', 'http://localhost:8080/images/massas/lasanha.png'
UNION ALL SELECT 'Pizza', 50.00, 'MASSA', 'http://localhost:8080/images/massas/pizza.png'
UNION ALL SELECT 'Barra de Chocolate', 15.00, 'DOCE', 'http://localhost:8080/images/doces/chocolate.png'
UNION ALL SELECT 'Bolo', 60.00, 'DOCE', 'http://localhost:8080/images/doces/bolo.png'
WHERE NOT EXISTS (SELECT 1 FROM food);

