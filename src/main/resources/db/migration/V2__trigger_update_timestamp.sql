-- Function para atualizar o campo updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger que dispara antes de qualquer UPDATE
CREATE TRIGGER trg_update_timestamp
    BEFORE UPDATE ON veiculos
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();
