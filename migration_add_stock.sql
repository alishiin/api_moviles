-- Script para agregar la columna stock a la tabla producto si no existe
-- Ejecutar este script en tu base de datos Supabase si la columna no se crea automáticamente

-- Agregar columna stock si no existe
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns
                   WHERE table_name='producto' AND column_name='stock') THEN
        ALTER TABLE producto ADD COLUMN stock INTEGER DEFAULT 0 NOT NULL;
    END IF;
END $$;

-- Agregar columna tallas_disponibles si no existe
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns
                   WHERE table_name='producto' AND column_name='tallas_disponibles') THEN
        ALTER TABLE producto ADD COLUMN tallas_disponibles VARCHAR(500);
    END IF;
END $$;

-- Actualizar productos existentes que tengan stock NULL
UPDATE producto SET stock = 0 WHERE stock IS NULL;

-- Verificar que las columnas se crearon correctamente
SELECT column_name, data_type, is_nullable, column_default
FROM information_schema.columns
WHERE table_name = 'producto'
ORDER BY ordinal_position;
