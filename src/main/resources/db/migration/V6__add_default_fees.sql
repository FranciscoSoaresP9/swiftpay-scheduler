INSERT INTO fees (name, min_amount, max_amount) VALUES
    ('Taxa A', 0, 1000),
    ('Taxa B', 1001, 2000),
    ('Taxa C', 2001, NULL);

INSERT INTO fee_ranges (fee_id, min_days, max_days, fixed_fee, tax_percentage) VALUES
    ((SELECT id FROM fees WHERE name = 'Taxa A'), 0, 0, 3.00, 3.00),
    ((SELECT id FROM fees WHERE name = 'Taxa B'), 1, 10, 0.00, 9.00),
    ((SELECT id FROM fees WHERE name = 'Taxa C'), 11, 20, 0.00, 8.20),
    ((SELECT id FROM fees WHERE name = 'Taxa C'), 21, 30, 0.00, 6.90),
    ((SELECT id FROM fees WHERE name = 'Taxa C'), 31, 40, 0.00, 4.70),
    ((SELECT id FROM fees WHERE name = 'Taxa C'), 41, NULL, 0.00, 1.70);