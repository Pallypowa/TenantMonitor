-- Insert data into User table
INSERT INTO users (id, first_name, last_name, email, user_name, password, user_type) VALUES
                                                                  ('a1111111-1111-1111-1111-111111111111', 'Alice', 'Smith', 'alice.smith@example.com', 'LenderLennon', 'hashed_password1', 'OWNER'),
                                                                  ('b2222222-2222-2222-2222-222222222222', 'Bob', 'Johnson', 'bob.johnson@example.com', 'PropertyPeter', 'hashed_password2', 'OWNER'),
                                                                  ('c3333333-3333-3333-3333-333333333333', 'Charlie', 'Chaplin', 'bob.johnson@example.com', '', '', 'TENANT');

-- Insert data into Property table
INSERT INTO property (id, name, zipcode, street, size, storey, house_number, price, owner_id, is_furnished, property_type, tenant_id) VALUES
                                                                                                                                        ('e5555555-5555-5555-5555-555555555555', 'Sunny Apartments', 1051, '123 Main Street', 85, 3, 10, 500000, 'a1111111-1111-1111-1111-111111111111', false, 'APARTMENT', null),
                                                                                                                                        ('f6666666-6666-6666-6666-666666666666', 'Cozy Cottage', 1067, '456 Side Street', 120, 1, 20, 750000, 'b2222222-2222-2222-2222-222222222222', true, 'HOUSE', 'c3333333-3333-3333-3333-333333333333');

-- Insert data into TransactionHistory table
INSERT INTO transaction_history (id, property_id, tenant_id, amount, created_at) VALUES
                                                                                   ('a7777777-7777-7777-7777-777777777777', 'e5555555-5555-5555-5555-555555555555', 'a1111111-1111-1111-1111-111111111111', 500000, '2024-01-01'),
                                                                                   ('b8888888-8888-8888-8888-888888888888', 'f6666666-6666-6666-6666-666666666666', 'b2222222-2222-2222-2222-222222222222', 750000, '2024-06-01');
