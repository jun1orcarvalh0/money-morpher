INSERT INTO profile (email, username, password, role)
  VALUES ('maria.silva@email.com', 'maria.silva', '$2y$10$iwJxffQ0XotDP9vtGG2waOlnzwvqBCPMDd.6FLp9cYE8y.8NLvg4S', 'ADMIN');
INSERT INTO profile (email, username, password, role)
  VALUES ('joao.santos@email.com', 'joao.santos', '$$2y$10$iwJxffQ0XotDP9vtGG2waOlnzwvqBCPMDd.6FLp9cYE8y.8NLvg4S', 'ADMIN');
INSERT INTO profile (email, username, password, role)
  VALUES ('ana.pereira@email.com', 'ana.pereira', '$$2y$10$iwJxffQ0XotDP9vtGG2waOlnzwvqBCPMDd.6FLp9cYE8y.8NLvg4S', 'CLIENT');
INSERT INTO profile (email, username, password, role)
  VALUES ('pedro.oliveira@email.com', 'pedro.oliveira', '$2y$10$iwJxffQ0XotDP9vtGG2waOlnzwvqBCPMDd.6FLp9cYE8y.8NLvg4S', 'CLIENT');
INSERT INTO profile (email, username, password, role)
  VALUES ('laura.costa@email.com', 'laura.costa', '$2y$10$iwJxffQ0XotDP9vtGG2waOlnzwvqBCPMDd.6FLp9cYE8y.8NLvg4S', 'CLIENT');

INSERT INTO transaction (uuid, description, brlvalue, usdvalue, eurvalue, created_at, transaction_type, profile_id)
VALUES
    ('ca6c3621-fcaf-4e80-8985-07a75bc62502', 'Pix for Junior', 60.00, 12.00, 10.80, '2023-10-27 18:00:00', 'Purchase', (SELECT id FROM profile WHERE username = 'pedro.oliveira')),
    ('e699ef16-7fb7-4135-8159-e4b13f12a5b3', 'Supermarket', 90.00, 18.00, 16.20, '2023-10-27 19:00:00', 'Purchase', (SELECT id FROM profile WHERE username = 'pedro.oliveira'));