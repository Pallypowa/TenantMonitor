-- Insert data into Owner table
INSERT INTO owner (id, first_name, last_name, email, password) VALUES
                                                                   ('a1111111-1111-1111-1111-111111111111', 'Alice', 'Smith', 'alice.smith@example.com', 'hashed_password1'),
                                                                   ('b2222222-2222-2222-2222-222222222222', 'Bob', 'Johnson', 'bob.johnson@example.com', 'hashed_password2');

-- Insert data into Tenant table
INSERT INTO tenant (id, first_name, last_name, email, phone_number, lease_start_date, lease_end_date) VALUES
                                                                                                                    ('c3333333-3333-3333-3333-333333333333', 'Charlie', 'Brown', 'charlie.brown@example.com', '123-456-7890', '2024-01-01', '2024-12-31'),
                                                                                                                    ('d4444444-4444-4444-4444-444444444444', 'Dana', 'White', 'dana.white@example.com', '098-765-4321', '2024-06-01', '2025-05-31');

-- Insert data into Property table
INSERT INTO property (id, name, zipcode, street, size, storey, house_number, price, owner_id, tenant_id, is_free, is_furnished, property_type) VALUES
                                                                                                                                                   ('e5555555-5555-5555-5555-555555555555', 'Sunny Apartments', 1051, '123 Main Street', 85, 3, 10, 500000, 'a1111111-1111-1111-1111-111111111111', 'c3333333-3333-3333-3333-333333333333', false, false, 'APARTMENT'),
                                                                                                                                                   ('f6666666-6666-6666-6666-666666666666', 'Cozy Cottage', 1067, '456 Side Street', 120, 1, 20, 750000, 'b2222222-2222-2222-2222-222222222222', 'd4444444-4444-4444-4444-444444444444', false, true, 'HOUSE');

-- Insert data into TransactionHistory table
INSERT INTO transaction_history (id, property_id, tenant_id, amount, created_at) VALUES
                                                                                    ('a7777777-7777-7777-7777-777777777777', 'e5555555-5555-5555-5555-555555555555', 'c3333333-3333-3333-3333-333333333333', 500000, '2024-01-01'),
                                                                                    ('b8888888-8888-8888-8888-888888888888', 'f6666666-6666-6666-6666-666666666666', 'd4444444-4444-4444-4444-444444444444', 750000, '2024-06-01');